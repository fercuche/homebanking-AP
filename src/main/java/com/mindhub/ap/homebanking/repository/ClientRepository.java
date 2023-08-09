package com.mindhub.ap.homebanking.repository;

import com.mindhub.ap.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAll();
    Optional<Client> getClientById(Long id);

}