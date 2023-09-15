package com.mindhub.ap.homebanking.controllers;

import com.mindhub.ap.homebanking.dtos.AccountDTO;
import com.mindhub.ap.homebanking.models.Account;
import com.mindhub.ap.homebanking.models.AccountType;
import com.mindhub.ap.homebanking.models.Client;
import com.mindhub.ap.homebanking.repository.AccountRepository;
import com.mindhub.ap.homebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream()
                .map(AccountDTO::new).collect(toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.getAccountById(id)
                .map(AccountDTO::new)
                .orElse(null);
    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam AccountType accountType, Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());

        if (accountType == null){
            return new ResponseEntity<>("The client already have 3 accounts", HttpStatus.FORBIDDEN);
        }
        if (client.getAccounts().size() == 3) {
            return new ResponseEntity<>("The client already have 3 accounts", HttpStatus.FORBIDDEN);
        } else {
            Random random = new Random();
            String randomAccountNumber;
            do {
                randomAccountNumber = String.valueOf(random.nextInt(99999999));
            } while (accountRepository.existsByNumber(randomAccountNumber));

            Account account = new Account("VIN-" + randomAccountNumber, accountType, LocalDate.now(), 0.0D, client);
            client.addAccount(account);
            accountRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
