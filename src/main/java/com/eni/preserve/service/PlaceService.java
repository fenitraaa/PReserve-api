package com.eni.preserve.service;

import com.eni.preserve.entity.Place;
import com.eni.preserve.entity.PlaceId;
import com.eni.preserve.entity.Voiture;
import com.eni.preserve.repository.PlaceRepository;
import com.eni.preserve.repository.VoitureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final VoitureRepository voitureRepository;

    public Place create(Long idvoit, int numeroPlace) {
        Voiture voiture = voitureRepository.findById(idvoit)
                .orElseThrow(() -> new RuntimeException("Voiture introuvable"));

        PlaceId placeId = new PlaceId(idvoit, numeroPlace);
        Place place = new Place();
        place.setVoiture(voiture);
        place.setId(placeId);
        place.setOccupation(false);

        return placeRepository.save(place);
    }

    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    public List<Place> findByVoiture(Long idvoit) {
        Voiture voiture = voitureRepository.findById(idvoit)
                .orElseThrow(() -> new RuntimeException("Voiture introuvable"));
        return placeRepository.findByVoiture(voiture);
    }

    public List<Place> findPlacesLibres() {
        return placeRepository.findByOccupation(false);
    }

    public List<Place> findPlacesOccupees() {
        return placeRepository.findByOccupation(true);
    }

    public List<Place> findByVoitureAndOccupation(Long idvoit, boolean occupation) {
        Voiture voiture = voitureRepository.findById(idvoit)
                .orElseThrow(() -> new RuntimeException("Voiture introuvable"));
        return placeRepository.findByVoitureAndOccupation(voiture, occupation);
    }

    public Place occuperPlace(Long idvoit, int numeroPlace) {
        Voiture voiture = voitureRepository.findById(idvoit)
                .orElseThrow(() -> new RuntimeException("Voiture introuvable"));

        Place place = placeRepository.findByVoitureAndIdPlace(voiture, numeroPlace)
                .orElseThrow(() -> new RuntimeException("Place introuvable"));

        if (place.isOccupation()) {
            throw new RuntimeException("Place déjà occupée");
        }

        place.setOccupation(true);
        return placeRepository.save(place);
    }

    public Place libererPlace(Long idvoit, int numeroPlace) {
        Voiture voiture = voitureRepository.findById(idvoit)
                .orElseThrow(() -> new RuntimeException("Voiture introuvable"));

        Place place = placeRepository.findByVoitureAndIdPlace(voiture, numeroPlace)
                .orElseThrow(() -> new RuntimeException("Place introuvable"));

        if (!place.isOccupation()) {
            throw new RuntimeException("Place déjà libre");
        }

        place.setOccupation(false);
        return placeRepository.save(place);
    }
}