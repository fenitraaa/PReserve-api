package com.eni.preserve.controller;

import com.eni.preserve.entity.Place;
import com.eni.preserve.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/places")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<Place>> findAll() {
        return ResponseEntity.ok(placeService.findAll());
    }

    @GetMapping("/{idvoit}")
    public ResponseEntity<List<Place>> findByVoiture(@PathVariable String idvoit) {
        return ResponseEntity.ok(placeService.findByVoiture(idvoit));
    }

    @GetMapping("/libres")
    public ResponseEntity<List<Place>> findPlacesLibres() {
        return ResponseEntity.ok(placeService.findPlacesLibres());
    }

    @GetMapping("/occupees")
    public ResponseEntity<List<Place>> findPlacesOccupees() {
        return ResponseEntity.ok(placeService.findPlacesOccupees());
    }

    @PutMapping("/{idvoit}/{numeroPlace}/occuper")
    public ResponseEntity<Place> occuper(
            @PathVariable String idvoit,
            @PathVariable int numeroPlace) {
        return ResponseEntity.ok(placeService.occuperPlace(idvoit, numeroPlace));
    }

    @PutMapping("/{idvoit}/{numeroPlace}/liberer")
    public ResponseEntity<Place> liberer(
            @PathVariable String idvoit,
            @PathVariable int numeroPlace) {
        return ResponseEntity.ok(placeService.libererPlace(idvoit, numeroPlace));
    }
}