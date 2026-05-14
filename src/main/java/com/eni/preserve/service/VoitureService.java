package com.eni.preserve.service;

import com.eni.preserve.dto.VoitureDTO;
import com.eni.preserve.entity.Place;
import com.eni.preserve.entity.PlaceId;
import com.eni.preserve.entity.Voiture;
import com.eni.preserve.exception.BusinessException;
import com.eni.preserve.mapper.VoitureMapper;
import com.eni.preserve.repository.PlaceRepository;
import com.eni.preserve.repository.VoitureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoitureService {

    private final VoitureRepository voitureRepository;
    private final VoitureMapper voitureMapper;
    private final PlaceRepository placeRepository;

    @Transactional
    public VoitureDTO create(VoitureDTO dto) {
        if (dto.getNbrplace() <= 0) {
            throw new RuntimeException("Nombre de places invalide");
        }

        Voiture voiture = voitureMapper.toEntity(dto);
        Voiture saved = voitureRepository.save(voiture);
        for (int i = 1; i <= saved.getNbrplace(); i++) {
            Place place = new Place();
            place.setId(new PlaceId(saved.getIdvoit(), i));
            place.setVoiture(saved);
            place.setOccupation(false);
            placeRepository.save(place);
        }

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
                    .orElseThrow(() -> new BusinessException("Voiture introuvable"));

        return voitureMapper.toDTO(v);
    }

    public VoitureDTO update(Long id, VoitureDTO dto) {
        Voiture existing = voitureRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Voiture introuvable"));

        voitureMapper.updateEntity(existing, dto);
        Voiture updated = voitureRepository.save(existing);
        return voitureMapper.toDTO(updated);
    }

    public int getPlacesLibres(Long id) {
        Voiture v = voitureRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Voiture introuvable"));

        return placeRepository.findByVoitureAndOccupation(v, false).size();
    }
}