package com.eni.preserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eni.preserve.entity.Client;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, String> {
    @Query("SELECT c FROM Client c WHERE LOWER(c.nom) LIKE LOWER(CONCAT('%', :q, '%')) OR c.numtel LIKE CONCAT('%', :q, '%')")
List<Client> findByNomContainingOrNumtelContaining(@Param("q") String nom, @Param("q") String numtel);
}
