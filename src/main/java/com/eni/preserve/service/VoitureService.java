package com.eni.preserve.service;

import com.eni.preserve.dto.VoitureDTO;
import com.eni.preserve.entity.Place;
import com.eni.preserve.entity.PlaceId;
import com.eni.preserve.entity.Voiture;
import com.eni.preserve.exception.BusinessException;
import com.eni.preserve.mapper.VoitureMapper;
import com.eni.preserve.repository.PlaceRepository;
import com.eni.preserve.repository.VoitureRepository;
import com.eni.preserve.util.IdGenerator;
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
    private final IdGenerator idGenerator;

    @Transactional
    public VoitureDTO create(VoitureDTO dto) {
        if (dto.getNbrplace() <= 0) {
            throw new BusinessException("Nombre de places invalide");
        }

        Voiture voiture = voitureMapper.toEntity(dto);
        voiture.setIdvoit(idGenerator.generateVoitureId());
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

    public VoitureDTO findById(String id) {
        return voitureMapper.toDTO(voitureRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Voiture introuvable")));
    }

@Transactional
public VoitureDTO update(String id, VoitureDTO dto) {
    Voiture existing = voitureRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Voiture introuvable"));

    int ancienNbrPlace = existing.getNbrplace();
    int nouveauNbrPlace = dto.getNbrplace();

    voitureMapper.updateEntity(existing, dto);
    Voiture updated = voitureRepository.save(existing);

    if (nouveauNbrPlace > ancienNbrPlace) {
        for (int i = ancienNbrPlace + 1; i <= nouveauNbrPlace; i++) {
            Place place = new Place();
            place.setId(new PlaceId(updated.getIdvoit(), i));
            place.setVoiture(updated);
            place.setOccupation(false);
            placeRepository.save(place);
        }
    }

    if (nouveauNbrPlace < ancienNbrPlace) {
        for (int i = nouveauNbrPlace + 1; i <= ancienNbrPlace; i++) {
            PlaceId placeId = new PlaceId(updated.getIdvoit(), i);
            Place place = placeRepository.findById(placeId)
                    .orElse(null);

            if (place != null && place.isOccupation()) {
                throw new BusinessException(
                    "Impossible de réduire les places — la place " + i + " est occupée"
                );
            }

            placeRepository.deleteById(placeId);
        }
    }

    return voitureMapper.toDTO(updated);
}

    public int getPlacesLibres(String id) {
        Voiture v = voitureRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Voiture introuvable"));
        return placeRepository.findByVoitureAndOccupation(v, false).size();
    }
}