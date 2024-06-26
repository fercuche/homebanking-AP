package com.mindhub.ap.homebanking.repository;

import com.mindhub.ap.homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {

    boolean existsByNumber(String number);
    Card getCardById(Long id);
}