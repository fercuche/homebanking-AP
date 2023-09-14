package com.mindhub.ap.homebanking.dtos;

import com.mindhub.ap.homebanking.models.Account;
import com.mindhub.ap.homebanking.models.AccountType;

import java.time.LocalDate;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AccountDTO {

    private Long id;
    private String number;
    private AccountType accountType;
    private LocalDate creationDate;
    private Double balance;
    private Set<TransactionDTO> transactions;

    public AccountDTO(Account account) {

        this.id = account.getId();
        this.number = account.getNumber();
        this.accountType = account.getAccountType();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream()
                .map(transaction -> new TransactionDTO(transaction)).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
