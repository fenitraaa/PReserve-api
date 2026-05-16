package com.eni.preserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eni.preserve.entity.Client;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, String> {
    List<Client> findByNomContainingOrNumtelContaining(String nom, String numtel);
}
