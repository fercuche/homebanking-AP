package com.mindhub.ap.homebanking.dtos;

import com.mindhub.ap.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class LoanDTO {
    private Long id;
    private String name;
    private Double maxAmount;
    private List<Integer> payments;

    public LoanDTO(Long id, String name, Double maxAmount, List<Integer> payments) {
        this.id = id;
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public LoanDTO(Loan loan){
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

}
