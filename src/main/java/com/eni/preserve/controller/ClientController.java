package com.eni.preserve.controller;

import com.eni.preserve.dto.ClientDTO;
import com.eni.preserve.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO dto) {
        ClientDTO created = clientService.create(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable int id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(
            @PathVariable int id,
            @RequestBody ClientDTO dto
    ) {
        return ResponseEntity.ok(clientService.update(id, dto));
    }

    @GetMapping("/recherche")
    public ResponseEntity<List<ClientDTO>> recherche(@RequestParam String q) {
        return ResponseEntity.ok(clientService.recherche(q));
    }
}