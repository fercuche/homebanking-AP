package com.mindhub.ap.homebanking.services;

import com.mindhub.ap.homebanking.dtos.LoanDTO;
import com.mindhub.ap.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.ap.homebanking.models.Loan;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LoanService {

    List<LoanDTO> getAllLoans();
    Loan getLoanById(Long id);
    boolean toAccountExists(String number);
    boolean currentClientMatchAccount(String number, Authentication authentication);
    void requestLoan(LoanApplicationDTO dto, Authentication authentication);


}
