package com.mindhub.ap.homebanking.dtos;

import com.mindhub.ap.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<AccountDTO> accounts;

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClientDTO(){
    }

    public ClientDTO(Client client) {

        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }


}
