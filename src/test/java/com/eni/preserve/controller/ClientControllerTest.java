package com.eni.preserve.controller;

import com.eni.preserve.dto.ClientDTO;
import com.eni.preserve.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService service;

    @InjectMocks
    private ClientController controller;

    private ObjectMapper objectMapper;
    private ClientDTO dto;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        dto = new ClientDTO();
        dto.setNom("Fenitra");
        dto.setNumtel("0345351885");
    }

    @Test
    void testCreate() throws Exception {
        when(service.create(any(ClientDTO.class))).thenReturn(dto);
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Fenitra"))
                .andExpect(jsonPath("$.numtel").value("0345351885"));
        verify(service).create(any(ClientDTO.class));
    }

    @Test
    void testFindAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(dto));
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nom").value("Fenitra"));
    }

    @Test
    void testFindAllEmpty() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testFindById() throws Exception {
        when(service.findById(1)).thenReturn(dto);
        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Fenitra"))
                .andExpect(jsonPath("$.numtel").value("0345351885"));
    }

    @Test
    void testFindByIdFail() {
        when(service.findById(99)).thenThrow(new RuntimeException("Client introuvable"));
        org.junit.jupiter.api.Assertions.assertThrows(
            Exception.class,
            () -> mockMvc.perform(get("/api/clients/99"))
        );
    }
    
    @Test
    void testUpdate() throws Exception {
        ClientDTO newDto = new ClientDTO();
        newDto.setNom("Tojo");
        newDto.setNumtel("0330551248");
        when(service.update(eq(1), any(ClientDTO.class))).thenReturn(newDto);
        mockMvc.perform(put("/api/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Tojo"))
                .andExpect(jsonPath("$.numtel").value("0330551248"));
        verify(service).update(eq(1), any(ClientDTO.class));
    }

    @Test
    void testUpdateFail() {
        when(service.update(eq(99), any(ClientDTO.class)))
                .thenThrow(new RuntimeException("Client introuvable"));
        org.junit.jupiter.api.Assertions.assertThrows(
            Exception.class,
            () -> mockMvc.perform(put("/api/clients/99")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
        );
    }

    @Test
    void testSearch() throws Exception {
        when(service.recherche("Fen")).thenReturn(List.of(dto));
        mockMvc.perform(get("/api/clients/recherche")
                        .param("q", "Fen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nom").value("Fenitra"));
    }

    @Test
    void testSearchEmpty() throws Exception {
        when(service.recherche("XYZ")).thenReturn(List.of());
        mockMvc.perform(get("/api/clients/recherche")
                        .param("q", "XYZ"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}