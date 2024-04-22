package com.mindhub.ap.homebanking.controllers;

import com.mindhub.ap.homebanking.dtos.TransactionDTO;
import com.mindhub.ap.homebanking.models.Account;
import com.mindhub.ap.homebanking.models.Client;
import com.mindhub.ap.homebanking.models.Transaction;
import com.mindhub.ap.homebanking.models.TransactionType;
import com.mindhub.ap.homebanking.repository.AccountRepository;
import com.mindhub.ap.homebanking.repository.ClientRepository;
import com.mindhub.ap.homebanking.repository.TransactionRepository;
import com.mindhub.ap.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> makeTransaction(@RequestParam Double amount,@RequestParam String description,
                                                  @RequestParam String toAccountNumber,
                                                  @RequestParam String fromAccountNumber){
        String missingParam = null;
        if (amount == null) {
            missingParam = "Amount";
        } else if (description.isEmpty()) {
            missingParam = "Description";
        } else if (toAccountNumber.isEmpty()) {
            missingParam = "Destination Account Number";
        } else if (fromAccountNumber.isEmpty()) {
            missingParam = "Source Account Number";
        }
        if (missingParam != null) {
            return new ResponseEntity<>(missingParam + " is required", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("Source account can't be the same as Destination account", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.existsByNumber(fromAccountNumber)){
            return new ResponseEntity<>("Source account doesn't exists", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.existsByNumber(toAccountNumber)){
            return new ResponseEntity<>("Destiny account doesn't exists", HttpStatus.FORBIDDEN);
        }
        Account sourceAccount = accountRepository.findByNumber(fromAccountNumber);
        Account destinationAccount = accountRepository.findByNumber(toAccountNumber);
        if (sourceAccount.getBalance() <= amount){
            return new ResponseEntity<>("The amount of the balance account isn't sufficient", HttpStatus.FORBIDDEN);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByEmail(authentication.getName());

        if (!sourceAccount.getClient().equals(currentClient)){
            return new ResponseEntity<>("Source account doesn't belong to authenticated client", HttpStatus.FORBIDDEN);
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        Transaction creditTransaction = new Transaction(description + " " + sourceAccount.getNumber(),
                LocalDateTime.now(), amount, TransactionType.CREDIT, destinationAccount);
        Transaction debitTransaction = new Transaction(description + " " + destinationAccount.getNumber(),
                LocalDateTime.now(), -amount, TransactionType.DEBIT, sourceAccount);

        sourceAccount.addTransaction(debitTransaction);
        destinationAccount.addTransaction(creditTransaction);

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        transactionRepository.save(creditTransaction);
        transactionRepository.save(debitTransaction);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/transactions")
    public ResponseEntity<Object> filterTransactionsByDate(String fromDate, String toDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime from = LocalDate.parse(fromDate, formatter).atStartOfDay();
        LocalDateTime to = LocalDate.parse(toDate, formatter).atStartOfDay();
        List<TransactionDTO> filteredTransactions = transactionService.filterByDate(from, to);
        return new ResponseEntity<>(filteredTransactions, HttpStatus.OK);
    }
}
