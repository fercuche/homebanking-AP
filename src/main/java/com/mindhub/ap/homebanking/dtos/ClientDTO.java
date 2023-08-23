package com.mindhub.ap.homebanking.dtos;

import com.mindhub.ap.homebanking.models.Client;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<AccountDTO> accounts;
    private Set<ClientLoanDTO> loans;   
    private Set<CardDTO> cards;

    public ClientDTO(Client client) {

        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.password = client.getPassword();
        this.accounts = client.getAccounts().stream()
                .map(account -> new AccountDTO(account)).collect(toSet());
        this.loans = client.getClientLoans().stream()
                .map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(toSet());
        this.cards = client.getCards().stream()
                .map(card -> new CardDTO(card)).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

}
