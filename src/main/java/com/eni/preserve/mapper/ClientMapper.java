package com.eni.preserve.mapper;

import com.eni.preserve.dto.ClientDTO;
import com.eni.preserve.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setIdcli(client.getIdcli());
        dto.setNom(client.getNom());
        dto.setNumtel(client.getNumtel());
        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        Client client = new Client();
        client.setIdcli(dto.getIdcli());
        client.setNom(dto.getNom());
        client.setNumtel(dto.getNumtel());
        return client;
    }

    public void updateEntity(Client existing, ClientDTO dto) {
        existing.setNom(dto.getNom());
        existing.setNumtel(dto.getNumtel());
    }
}