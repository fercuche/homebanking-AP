package com.mindhub.ap.homebanking.dtos;

import com.mindhub.ap.homebanking.models.Transaction;
import com.mindhub.ap.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private final Long id;
    private final String description;
    private final LocalDateTime date;
    private final Double amount;
    private final TransactionType type;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
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
}
