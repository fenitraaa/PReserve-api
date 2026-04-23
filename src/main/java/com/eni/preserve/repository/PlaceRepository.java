package com.eni.preserve.repository;

import com.eni.preserve.entity.Place;
import com.eni.preserve.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PlaceRepository extends JpaRepository<Place, Voiture> {

    List<Place> findByVoiture(Voiture voiture);

    List<Place> findByOccupation(boolean occupation);

    List<Place> findByVoitureAndOccupation(Voiture voiture, boolean occupation);
    Optional<Place> findByVoitureAndPlace(Voiture voiture, int place);
}