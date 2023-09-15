package com.mindhub.ap.homebanking.services;

import com.mindhub.ap.homebanking.dtos.TransactionDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    List<TransactionDTO> filterByDate(LocalDateTime fromDate, LocalDateTime toDate);
}
