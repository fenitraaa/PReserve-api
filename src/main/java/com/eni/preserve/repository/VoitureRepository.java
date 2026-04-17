package com.eni.preserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eni.preserve.entity.Voiture;

public interface VoitureRepository extends JpaRepository <Voiture, Long> {
    
}
