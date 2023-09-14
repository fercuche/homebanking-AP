package com.mindhub.ap.homebanking.dtos;

import com.mindhub.ap.homebanking.models.Transaction;
import com.mindhub.ap.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private String description;
    private LocalDateTime date;
    private Double amount;
    private TransactionType type;
    private Double balance;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.balance = transaction.getBalance();

    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Double getBalance() {
        return balance;
    }
}
