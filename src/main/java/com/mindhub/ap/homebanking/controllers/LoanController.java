package com.mindhub.ap.homebanking.controllers;

import com.mindhub.ap.homebanking.dtos.LoanDTO;
import com.mindhub.ap.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.ap.homebanking.models.Loan;
import com.mindhub.ap.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){

        return loanService.getAllLoans();
    }

    @PostMapping("/loans")
    public ResponseEntity<Object> requestLoan(@RequestBody LoanApplicationDTO dto, Authentication authentication){

        String validationError = null;
        if (dto.getLoanId() == null) {
            validationError = "Loan ID is required";
        } else if (dto.getAmount() == null) {
            validationError = "Amount is required";
        } else if (dto.getPayments() == null) {
            validationError = "Payments field is required";
        } else if (dto.getToAccountNumber().isEmpty()) {
            validationError = "Destination account is required";
        } else if (dto.getAmount() <= 0) {
            validationError = "Amount must be greater than zero";
        } else if (dto.getPayments() <= 0){
            validationError = "Payments must be greater than zero";
        }
        if (validationError != null) {
            return new ResponseEntity<>(validationError, HttpStatus.FORBIDDEN);
        }

        Loan loan = loanService.getLoanById(dto.getLoanId());
        if (loan == null) {
            return new ResponseEntity<>("Loan type doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (dto.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("Amount can't be bigger than Loan Max amount", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(dto.getPayments())) {
            return new ResponseEntity<>("Payments requested aren't available in selected Loan type", HttpStatus.FORBIDDEN);
        }

        if (!loanService.toAccountExists(dto.getToAccountNumber())) {
            return new ResponseEntity<>("Destination account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (!loanService.currentClientMatchAccount(dto.getToAccountNumber(), authentication)) {
            return new ResponseEntity<>("Account doesn't belong to the current client", HttpStatus.FORBIDDEN);
        }

        loanService.requestLoan(dto, authentication);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
