package com.mindhub.ap.homebanking.services.impl;

import com.mindhub.ap.homebanking.dtos.AccountDTO;
import com.mindhub.ap.homebanking.dtos.LoanDTO;
import com.mindhub.ap.homebanking.repository.LoanRepository;
import com.mindhub.ap.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(LoanDTO::new).collect(toList());
    }
}
