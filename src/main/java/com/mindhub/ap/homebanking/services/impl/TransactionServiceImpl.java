package com.mindhub.ap.homebanking.services.impl;

import com.mindhub.ap.homebanking.dtos.TransactionDTO;
import com.mindhub.ap.homebanking.repository.TransactionRepository;
import com.mindhub.ap.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<TransactionDTO> filterByDate(LocalDateTime fromDate, LocalDateTime toDate) {
        return transactionRepository.findByDateBetween(fromDate, toDate)
                .stream().map(TransactionDTO::new).collect(toList());
    }
}
