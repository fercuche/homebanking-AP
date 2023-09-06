package com.mindhub.ap.homebanking.repository;

import com.mindhub.ap.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAll();
    boolean existsById(Long id);
    Loan getLoanById(Long id);

}