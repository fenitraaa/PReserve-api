package com.eni.preserve.service;

import com.eni.preserve.entity.Place;
import com.eni.preserve.entity.PlaceId;
import com.eni.preserve.entity.Voiture;
import com.eni.preserve.enums.TypeVoiture;
import com.eni.preserve.repository.PlaceRepository;
import com.eni.preserve.repository.VoitureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private VoitureRepository voitureRepository;

    @InjectMocks
    private PlaceService placeService;

    private Voiture voiture;
    private Place place;
    private PlaceId placeId;

    @BeforeEach
    void init() {
        voiture = new Voiture();
        voiture.setIdvoit("V001");
        voiture.setDesign("Toyota");
        voiture.setType(TypeVoiture.SIMPLE);
        voiture.setNbrplace(5);
        voiture.setFrais(10000);

        placeId = new PlaceId("V001", 1);

        place = new Place();
        place.setId(placeId);
        place.setVoiture(voiture);
        place.setOccupation(false);
    }

    @Test
    void testFindAll() {
        when(placeRepository.findAll()).thenReturn(List.of(place));

        List<Place> result = placeService.findAll();

        assertThat(result).hasSize(1);
    }

    @Test
    void testFindAllEmpty() {
        when(placeRepository.findAll()).thenReturn(List.of());

        List<Place> result = placeService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void testFindByVoiture() {
        when(voitureRepository.findById("V001")).thenReturn(Optional.of(voiture));
        when(placeRepository.findByVoiture(voiture)).thenReturn(List.of(place));

        List<Place> result = placeService.findByVoiture("V001");

        assertThat(result).hasSize(1);
    }

    @Test
    void testFindByVoitureIntrouvable() {
        when(voitureRepository.findById("V999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> placeService.findByVoiture("V999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Voiture introuvable");
    }

    @Test
    void testFindPlacesLibres() {
        when(placeRepository.findByOccupation(false)).thenReturn(List.of(place));

        List<Place> result = placeService.findPlacesLibres();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).isOccupation()).isFalse();
    }

    @Test
    void testFindPlacesOccupees() {
        place.setOccupation(true);
        when(placeRepository.findByOccupation(true)).thenReturn(List.of(place));

        List<Place> result = placeService.findPlacesOccupees();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).isOccupation()).isTrue();
    }

    @Test
    void testFindByVoitureAndOccupation() {
        when(voitureRepository.findById("V001")).thenReturn(Optional.of(voiture));
        when(placeRepository.findByVoitureAndOccupation(voiture, false)).thenReturn(List.of(place));

        List<Place> result = placeService.findByVoitureAndOccupation("V001", false);

        assertThat(result).hasSize(1);
    }

    @Test
    void testOccuperPlace() {
        when(voitureRepository.findById("V001")).thenReturn(Optional.of(voiture));
        when(placeRepository.findByVoitureAndIdPlace(voiture, 1)).thenReturn(Optional.of(place));
        when(placeRepository.save(place)).thenReturn(place);

        Place result = placeService.occuperPlace("V001", 1);

        assertThat(result.isOccupation()).isTrue();
        verify(placeRepository).save(place);
    }

    @Test
    void testOccuperPlaceDejaOccupee() {
        place.setOccupation(true);
        when(voitureRepository.findById("V001")).thenReturn(Optional.of(voiture));
        when(placeRepository.findByVoitureAndIdPlace(voiture, 1)).thenReturn(Optional.of(place));

        assertThatThrownBy(() -> placeService.occuperPlace("V001", 1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Place déjà occupée");

        verify(placeRepository, never()).save(any());
    }

    @Test
    void testOccuperPlaceIntrouvable() {
        when(voitureRepository.findById("V001")).thenReturn(Optional.of(voiture));
        when(placeRepository.findByVoitureAndIdPlace(voiture, 99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> placeService.occuperPlace("V001", 99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Place introuvable");
    }

    @Test
    void testLibererPlace() {
        place.setOccupation(true);
        when(voitureRepository.findById("V001")).thenReturn(Optional.of(voiture));
        when(placeRepository.findByVoitureAndIdPlace(voiture, 1)).thenReturn(Optional.of(place));
        when(placeRepository.save(place)).thenReturn(place);

        Place result = placeService.libererPlace("V001", 1);

        assertThat(result.isOccupation()).isFalse();
        verify(placeRepository).save(place);
    }

    @Test
    void testLibererPlaceDejaLibre() {
        when(voitureRepository.findById("V001")).thenReturn(Optional.of(voiture));
        when(placeRepository.findByVoitureAndIdPlace(voiture, 1)).thenReturn(Optional.of(place));

        assertThatThrownBy(() -> placeService.libererPlace("V001", 1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Place déjà libre");

        verify(placeRepository, never()).save(any());
    }

    @Test
    void testLibererPlaceIntrouvable() {
        when(voitureRepository.findById("V001")).thenReturn(Optional.of(voiture));
        when(placeRepository.findByVoitureAndIdPlace(voiture, 99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> placeService.libererPlace("V001", 99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Place introuvable");
    }
}