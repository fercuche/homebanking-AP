package com.mindhub.ap.homebanking.services.impl;

import com.mindhub.ap.homebanking.dtos.LoanDTO;
import com.mindhub.ap.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.ap.homebanking.models.*;
import com.mindhub.ap.homebanking.repository.*;
import com.mindhub.ap.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(LoanDTO::new).collect(toList());
    }

    @Override
    public Loan getLoanById(Long id){
        return loanRepository.getLoanById(id);
    }

    @Override
    public boolean toAccountExists(String number){
        return accountRepository.existsByNumber(number);
    }

    @Override
    public boolean currentClientMatchAccount(String number, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().contains(accountRepository.findByNumber(number));
    }

    @Transactional
    @Override
    public void requestLoan(LoanApplicationDTO dto, Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());

        ClientLoan clientLoan = new ClientLoan(dto.getAmount()*1.2,dto.getPayments());
        client.addLoan(clientLoan);
        loanRepository.getLoanById(dto.getLoanId()).addLoan(clientLoan);
        clientLoanRepository.save(clientLoan);

        Account account = accountRepository.findByNumber(dto.getToAccountNumber());

        Transaction transaction = new Transaction( loanRepository.getLoanById(
                dto.getLoanId()).getName()+" loan approved", LocalDateTime.now(), dto.getAmount(),
                TransactionType.CREDIT, account);

        transactionRepository.save(transaction);

        account.setBalance(account.getBalance() + transaction.getAmount());
        accountRepository.save(account);
    }
}
