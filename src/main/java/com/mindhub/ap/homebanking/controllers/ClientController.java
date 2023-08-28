package com.mindhub.ap.homebanking.controllers;

import com.mindhub.ap.homebanking.dtos.ClientDTO;
import com.mindhub.ap.homebanking.models.Account;
import com.mindhub.ap.homebanking.models.Client;
import com.mindhub.ap.homebanking.repository.AccountRepository;
import com.mindhub.ap.homebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping("/clients")
    public ResponseEntity<List<ClientDTO>> getClients(){
        List<ClientDTO> clientDTOs = clientRepository.findAll().stream()
                .map(ClientDTO::new).collect(toList());
        return ResponseEntity.ok().body(clientDTOs);

    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.getClientById(id)
                                .map(ClientDTO::new)
                                .orElse(null);

    }

    @PostMapping("/clients")
    public ResponseEntity<Object> registerClient(@RequestParam String firstName, @RequestParam String lastName,
                                           @RequestParam String email, @RequestParam String password) {
        String missingParam = null;
        if (firstName.isEmpty()) {
            missingParam = "First Name";
        } else if (lastName.isEmpty()) {
            missingParam = "Last Name";
        } else if (email.isEmpty()) {
            missingParam = "Email";
        } else if (password.isEmpty()) {
            missingParam = "Password";
        }
        if (missingParam != null) {
            return new ResponseEntity<>(missingParam + " is required", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) != null){
            return new ResponseEntity<>("email already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);


        Random random = new Random();
        String randomAccountNumber = String.valueOf(random.nextInt(99999999));
        Account account = new Account("VIN-" + randomAccountNumber, LocalDate.now(),0.0D, client);
        client.addAccount(account);
        accountRepository.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current")
    public ResponseEntity<ClientDTO> getCurrent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientRepository.findByEmail(authentication.getName());
        ClientDTO clientDTO = new ClientDTO(client);
        return ResponseEntity.ok().body(clientDTO);
    }

}