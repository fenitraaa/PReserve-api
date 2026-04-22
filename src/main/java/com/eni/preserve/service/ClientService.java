package com.eni.preserve.service;

import com.eni.preserve.dto.ClientDTO;
import com.eni.preserve.entity.Client;
import com.eni.preserve.mapper.ClientMapper;
import com.eni.preserve.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientDTO create(ClientDTO dto) {
        Client client = clientMapper.toEntity(dto);
        Client saved = clientRepository.save(client);
        return clientMapper.toDTO(saved);
    }

    public List<ClientDTO> findAll() {
        return clientRepository.findAll()
            .stream()
            .map(clientMapper::toDTO)
            .collect(Collectors.toList());
    }

    public ClientDTO findById(int id) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client introuvable"));
        return clientMapper.toDTO(client);
    }

    public ClientDTO update(int id, ClientDTO dto) {
        Client existing = clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client introuvable"));
        clientMapper.updateEntity(existing, dto);
        Client updated = clientRepository.save(existing);
        return clientMapper.toDTO(updated);
    }

    public List<ClientDTO> recherche(String q) {
        return clientRepository.findByNomContainingOrNumtelContaining(q, q)
            .stream()
            .map(clientMapper::toDTO)
            .collect(Collectors.toList());
    }
}