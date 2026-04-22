package com.eni.preserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eni.preserve.entity.Client;
import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findByNomContainingOrNumtelContaining(String nom, String numtel);
}
