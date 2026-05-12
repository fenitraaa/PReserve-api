package com.eni.preserve.controller;

import com.eni.preserve.entity.Place;
import com.eni.preserve.entity.PlaceId;
import com.eni.preserve.entity.Voiture;
import com.eni.preserve.enums.TypeVoiture;
import com.eni.preserve.service.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PlaceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PlaceService placeService;

    @InjectMocks
    private PlaceController placeController;

    private Voiture voiture;
    private Place place;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(placeController).build();

        voiture = new Voiture();
        voiture.setIdvoit(1L);
        voiture.setDesign("Toyota");
        voiture.setType(TypeVoiture.SIMPLE);
        voiture.setNbrplace(5);
        voiture.setFrais(10000);

        PlaceId placeId = new PlaceId(1L, 1);
        place = new Place();
        place.setId(placeId);
        place.setVoiture(voiture);
        place.setOccupation(false);
    }

    @Test
    void testCreate() throws Exception {
        when(placeService.create(1L, 1)).thenReturn(place);

        mockMvc.perform(post("/api/places/1/1"))
                .andExpect(status().isOk());

        verify(placeService).create(1L, 1);
    }

    @Test
    void testCreateVoitureIntrouvable() {
        when(placeService.create(99L, 1)).thenThrow(new RuntimeException("Voiture introuvable"));

        assertThrows(() -> mockMvc.perform(post("/api/places/99/1")));
    }

    @Test
    void testFindAll() throws Exception {
        when(placeService.findAll()).thenReturn(List.of(place));

        mockMvc.perform(get("/api/places"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAllEmpty() throws Exception {
        when(placeService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/places"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByVoiture() throws Exception {
        when(placeService.findByVoiture(1L)).thenReturn(List.of(place));

        mockMvc.perform(get("/api/places/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByVoitureIntrouvable() {
        when(placeService.findByVoiture(99L)).thenThrow(new RuntimeException("Voiture introuvable"));

        assertThrows(() -> mockMvc.perform(get("/api/places/99")));
    }

    @Test
    void testFindPlacesLibres() throws Exception {
        when(placeService.findPlacesLibres()).thenReturn(List.of(place));

        mockMvc.perform(get("/api/places/libres"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindPlacesOccupees() throws Exception {
        place.setOccupation(true);
        when(placeService.findPlacesOccupees()).thenReturn(List.of(place));

        mockMvc.perform(get("/api/places/occupees"))
                .andExpect(status().isOk());
    }

    @Test
    void testOccuper() throws Exception {
        place.setOccupation(true);
        when(placeService.occuperPlace(1L, 1)).thenReturn(place);

        mockMvc.perform(put("/api/places/1/1/occuper"))
                .andExpect(status().isOk());

        verify(placeService).occuperPlace(1L, 1);
    }

    @Test
    void testOccuperPlaceIntrouvable() {
        when(placeService.occuperPlace(1L, 99)).thenThrow(new RuntimeException("Place introuvable"));

        assertThrows(() -> mockMvc.perform(put("/api/places/1/99/occuper")));
    }

    @Test
    void testOccuperPlaceDejaOccupee() {
        when(placeService.occuperPlace(1L, 1)).thenThrow(new RuntimeException("Place déjà occupée"));

        assertThrows(() -> mockMvc.perform(put("/api/places/1/1/occuper")));
    }

    @Test
    void testLiberer() throws Exception {
        when(placeService.libererPlace(1L, 1)).thenReturn(place);

        mockMvc.perform(put("/api/places/1/1/liberer"))
                .andExpect(status().isOk());

        verify(placeService).libererPlace(1L, 1);
    }

    @Test
    void testLibererPlaceIntrouvable() {
        when(placeService.libererPlace(1L, 99)).thenThrow(new RuntimeException("Place introuvable"));

        assertThrows(() -> mockMvc.perform(put("/api/places/1/99/liberer")));
    }

    @Test
    void testLibererPlaceDejaLibre() {
        when(placeService.libererPlace(1L, 1)).thenThrow(new RuntimeException("Place déjà libre"));

        assertThrows(() -> mockMvc.perform(put("/api/places/1/1/liberer")));
    }

    private void assertThrows(ThrowingRunnable action) {
        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, action::run);
    }

    @FunctionalInterface
    interface ThrowingRunnable {
        void run() throws Exception;
    }
}