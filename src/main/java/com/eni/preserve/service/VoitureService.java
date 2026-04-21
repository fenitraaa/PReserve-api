package com.eni.preserve.service;

import com.eni.preserve.dto.VoitureDTO;
import com.eni.preserve.entity.Voiture;
import com.eni.preserve.mapper.VoitureMapper;
import com.eni.preserve.repository.VoitureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoitureService {

    private final VoitureRepository voitureRepository;
    private final VoitureMapper voitureMapper;


    public VoitureDTO create(VoitureDTO dto) {
        if (dto.getNbrplace() <= 0) {
            throw new RuntimeException("Nombre de places invalide");
        }
        Voiture voiture = voitureMapper.toEntity(dto);
        Voiture saved = voitureRepository.save(voiture);
        return voitureMapper.toDTO(saved);
    }

    public List<VoitureDTO> findAll() {
        return voitureRepository.findAll()
            .stream()
            .map(voitureMapper::toDTO)
            .collect(Collectors.toList());
    }

    public VoitureDTO findById(Long id) {
        Voiture v = voitureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Voiture introuvable"));
        
        return voitureMapper.toDTO(v);
    }

    public VoitureDTO update(Long id, VoitureDTO dto) {
        Voiture existing = voitureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Voiture introuvable"));
        voitureMapper.updateEntity(existing, dto);
        Voiture updated = voitureRepository.save(existing);
        return voitureMapper.toDTO(updated);
    }
    
    public int getPlacesLibres(Long id) {
        Voiture v = voitureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Voiture introuvable"));
        return v.getNbrplace();
    }
}
