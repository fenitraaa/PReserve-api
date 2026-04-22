package com.eni.preserve.service;

import com.eni.preserve.dto.ClientDTO;
import com.eni.preserve.entity.Client;
import com.eni.preserve.mapper.ClientMapper;
import com.eni.preserve.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository repo;

    @Mock
    private ClientMapper mapper;

    @InjectMocks
    private ClientService service;

    private Client client;
    private ClientDTO dto;

    @BeforeEach
    void init() {
        client = new Client();
        client.setIdcli(1);
        client.setNom("Fenitra");
        client.setNumtel("0345351885");

        dto = new ClientDTO();
        dto.setNom("Fenitra");
        dto.setNumtel("0345351885");
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(client);
        when(repo.save(client)).thenReturn(client);
        when(mapper.toDTO(client)).thenReturn(dto);

        ClientDTO res = service.create(dto);

        assertThat(res).isEqualTo(dto);
        verify(repo).save(client);
    }

    @Test
    void testFindAll() {
        when(repo.findAll()).thenReturn(List.of(client));
        when(mapper.toDTO(client)).thenReturn(dto);

        List<ClientDTO> res = service.findAll();

        assertThat(res).hasSize(1).containsExactly(dto);
    }

    @Test
    void testFindAllEmpty() {
        when(repo.findAll()).thenReturn(List.of());

        List<ClientDTO> res = service.findAll();

        assertThat(res).isEmpty();
    }

    @Test
    void testFindById() {
        when(repo.findById(1)).thenReturn(Optional.of(client));
        when(mapper.toDTO(client)).thenReturn(dto);

        ClientDTO res = service.findById(1);

        assertThat(res).isEqualTo(dto);
    }

    @Test
    void testFindByIdFail() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Client introuvable");
    }

    @Test
    void testUpdate() {
        ClientDTO newDto = new ClientDTO();
        newDto.setNom("Tojo");
        newDto.setNumtel("0330551248");

        when(repo.findById(1)).thenReturn(Optional.of(client));
        when(repo.save(client)).thenReturn(client);
        when(mapper.toDTO(client)).thenReturn(newDto);

        ClientDTO res = service.update(1, newDto);

        assertThat(res).isEqualTo(newDto);
        verify(mapper).updateEntity(client, newDto);
        verify(repo).save(client);
    }

    @Test
    void testUpdateFail() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Client introuvable");

        verify(repo, never()).save(any());
    }

    @Test
    void testSearch() {
        when(repo.findByNomContainingOrNumtelContaining("Fen", "Fen"))
                .thenReturn(List.of(client));
        when(mapper.toDTO(client)).thenReturn(dto);

        List<ClientDTO> res = service.recherche("Fen");

        assertThat(res).hasSize(1).containsExactly(dto);
    }

    @Test
    void testSearchEmpty() {
        when(repo.findByNomContainingOrNumtelContaining("XYZ", "XYZ"))
                .thenReturn(List.of());

        List<ClientDTO> res = service.recherche("XYZ");

        assertThat(res).isEmpty();
    }
}
