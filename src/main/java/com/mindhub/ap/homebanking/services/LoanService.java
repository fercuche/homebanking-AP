package com.mindhub.ap.homebanking.services;

import com.mindhub.ap.homebanking.dtos.LoanDTO;

import java.util.List;

public interface LoanService {

    List<LoanDTO> getAllLoans();

}
