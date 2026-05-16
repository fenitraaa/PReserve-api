package com.eni.preserve.repository;

import com.eni.preserve.entity.Reserver;
import com.eni.preserve.entity.Voiture;
import com.eni.preserve.enums.TypePaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserverRepository extends JpaRepository<Reserver, String> {

    List<Reserver> findByVoiture(Voiture voiture);

    List<Reserver> findByPayment(TypePaiement payment);

    List<Reserver> findByVoitureAndPayment(Voiture voiture, TypePaiement payment);

    @Query("SELECT COUNT(r) FROM Reserver r WHERE r.voiture = :voiture AND r.payment = :payment")
    long countByVoitureAndPayment(@Param("voiture") Voiture voiture, @Param("payment") TypePaiement payment);

    @Query("SELECT SUM(r.montantAvance) FROM Reserver r")
    Long getTotalRecette();

    @Query("SELECT SUM(r.voiture.frais) FROM Reserver r WHERE r.payment = 'TOUT_PAYE'")
    Long getTotalToutPaye();
}