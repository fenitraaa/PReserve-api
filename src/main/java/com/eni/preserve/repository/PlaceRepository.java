package com.eni.preserve.repository;

import com.eni.preserve.entity.Place;
import com.eni.preserve.entity.PlaceId;
import com.eni.preserve.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, PlaceId> {

    List<Place> findByVoiture(Voiture voiture);

    List<Place> findByOccupation(boolean occupation);

    List<Place> findByVoitureAndOccupation(Voiture voiture, boolean occupation);

    Optional<Place> findByVoitureAndIdPlace(Voiture voiture, int place);
}