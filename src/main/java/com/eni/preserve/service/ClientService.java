package com.eni.preserve.service;

import com.eni.preserve.dto.ClientDTO;
import com.eni.preserve.entity.Client;
import com.eni.preserve.exception.BusinessException;
import com.eni.preserve.mapper.ClientMapper;
import com.eni.preserve.repository.ClientRepository;
import com.eni.preserve.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final IdGenerator idGenerator;

    public ClientDTO create(ClientDTO dto) {
        Client client = clientMapper.toEntity(dto);
        client.setIdcli(idGenerator.generateClientId());
        Client saved = clientRepository.save(client);
        return clientMapper.toDTO(saved);
    }

    public List<ClientDTO> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO findById(String id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Client introuvable"));
        return clientMapper.toDTO(client);
    }

    public ClientDTO update(String id, ClientDTO dto) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Client introuvable"));
        clientMapper.updateEntity(existing, dto);
        return clientMapper.toDTO(clientRepository.save(existing));
    }

    public List<ClientDTO> recherche(String q) {
        return clientRepository.findByNomContainingOrNumtelContaining(q, q)
                .stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }
}