package com.mindhub.ap.homebanking.repository;

import com.mindhub.ap.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAll();
    Optional<Account> getAccountById(Long id);
    Account findByNumber(String number);
    boolean existsByNumber(String number);
}
