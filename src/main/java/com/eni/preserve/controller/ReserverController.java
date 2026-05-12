package com.eni.preserve.controller;

import com.eni.preserve.dto.ReserverDTO;
import com.eni.preserve.enums.TypePaiement;
import com.eni.preserve.service.ReserverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/reservations")
public class ReserverController {

    private final ReserverService reserverService;

    @PostMapping
    public ResponseEntity<ReserverDTO> create(@RequestBody ReserverDTO dto) {
        return ResponseEntity.ok(reserverService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ReserverDTO>> findAll() {
        return ResponseEntity.ok(reserverService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReserverDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(reserverService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReserverDTO> update(
            @PathVariable Long id,
            @RequestBody ReserverDTO dto) {
        return ResponseEntity.ok(reserverService.update(id, dto));
    }

    @GetMapping("/voiture/{idvoit}")
    public ResponseEntity<List<ReserverDTO>> findByVoiture(@PathVariable Long idvoit) {
        return ResponseEntity.ok(reserverService.findByVoiture(idvoit));
    }

    @GetMapping("/payment/{payment}")
    public ResponseEntity<List<ReserverDTO>> findByPayment(@PathVariable TypePaiement payment) {
        return ResponseEntity.ok(reserverService.findByPayment(payment));
    }

    @GetMapping("/voiture/{idvoit}/payment/{payment}")
    public ResponseEntity<List<ReserverDTO>> findByVoitureAndPayment(
            @PathVariable Long idvoit,
            @PathVariable TypePaiement payment) {
        return ResponseEntity.ok(reserverService.findByVoitureAndPayment(idvoit, payment));
    }

    @GetMapping("/voiture/{idvoit}/payment/{payment}/count")
    public ResponseEntity<Long> countByVoitureAndPayment(
            @PathVariable Long idvoit,
            @PathVariable TypePaiement payment) {
        return ResponseEntity.ok(reserverService.countByVoitureAndPayment(idvoit, payment));
    }

    @GetMapping("/recette")
    public ResponseEntity<Long> getTotalRecette() {
        return ResponseEntity.ok(reserverService.getTotalRecette());
    }
}