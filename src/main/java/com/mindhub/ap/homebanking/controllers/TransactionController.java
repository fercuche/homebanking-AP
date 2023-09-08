package com.mindhub.ap.homebanking.controllers;

import com.mindhub.ap.homebanking.models.Account;
import com.mindhub.ap.homebanking.models.Client;
import com.mindhub.ap.homebanking.models.Transaction;
import com.mindhub.ap.homebanking.models.TransactionType;
import com.mindhub.ap.homebanking.repository.AccountRepository;
import com.mindhub.ap.homebanking.repository.ClientRepository;
import com.mindhub.ap.homebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

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
                                                  @RequestParam String fromAccountNumber, Authentication authentication){
        String missingParam = null;
        if (amount == null) {
            missingParam = "Amount is required";
        } else if (amount <= 0){
            missingParam = "Amount must be higher than zero";
        } else if (description.isBlank()) {
            missingParam = "Description is required";
        } else if (toAccountNumber.isBlank()) {
            missingParam = "Destination Account Number is required";
        } else if (fromAccountNumber.isBlank()) {
            missingParam = "Source Account Number is required";
        }
        if (missingParam != null) {
            return new ResponseEntity<>(missingParam, HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("Source account can't be the same as Destination account", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.existsByNumber(fromAccountNumber)){
            return new ResponseEntity<>("Source account doesn't exists", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.existsByNumber(toAccountNumber)){
            return new ResponseEntity<>("Destination account doesn't exists", HttpStatus.FORBIDDEN);
        }
        Account sourceAccount = accountRepository.findByNumber(fromAccountNumber);
        Account destinationAccount = accountRepository.findByNumber(toAccountNumber);
        if (sourceAccount.getBalance() < amount){
            return new ResponseEntity<>("The amount of the balance account isn't sufficient", HttpStatus.FORBIDDEN);
        }

        Client currentClient = clientRepository.findByEmail(authentication.getName());

        if (!currentClient.getAccounts().contains(sourceAccount)){
            return new ResponseEntity<>("Source account doesn't belong to authenticated client", HttpStatus.FORBIDDEN);
        }

        Transaction creditTransaction = new Transaction(description + " " + sourceAccount.getNumber(),
                LocalDateTime.now(), amount, TransactionType.CREDIT, destinationAccount);
        Transaction debitTransaction = new Transaction(description + " " + destinationAccount.getNumber(),
                LocalDateTime.now(), -amount, TransactionType.DEBIT, sourceAccount);

        sourceAccount.addTransaction(debitTransaction);
        destinationAccount.addTransaction(creditTransaction);

        transactionRepository.save(creditTransaction);
        transactionRepository.save(debitTransaction);

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
