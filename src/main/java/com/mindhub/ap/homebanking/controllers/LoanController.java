package com.mindhub.ap.homebanking.controllers;

import com.mindhub.ap.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/loans")
    public ResponseEntity<Object> getLoans(){

        return ResponseEntity.ok().body(loanService.getAllLoans());

    }
}
