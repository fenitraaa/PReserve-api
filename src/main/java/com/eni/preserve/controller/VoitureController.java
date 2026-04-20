package com.eni.preserve.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eni.preserve.dto.VoitureDTO;
import com.eni.preserve.service.VoitureService;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/voitures")
public class VoitureController {
    private final VoitureService voitureService;

    @PostMapping
    public ResponseEntity<VoitureDTO> create(@RequestBody VoitureDTO dto) {
        VoitureDTO created = voitureService.create(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<VoitureDTO>> findAll() {
        return ResponseEntity.ok(voitureService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoitureDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(voitureService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoitureDTO> update(
            @PathVariable Long id,
            @RequestBody VoitureDTO dto
    ) {
        return ResponseEntity.ok(voitureService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        voitureService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/places-libres")
    public ResponseEntity<Integer> getPlacesLibres(@PathVariable Long id) {
        int placesLibres = voitureService.getPlacesLibres(id);
        return ResponseEntity.ok(placesLibres);
    }
}
