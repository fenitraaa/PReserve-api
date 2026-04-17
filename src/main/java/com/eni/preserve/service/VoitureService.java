package com.eni.preserve.service;

import com.eni.preserve.entity.Voiture;
import com.eni.preserve.repository.VoitureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoitureService {

    private final VoitureRepository voitureRepository;

    public Voiture create(Voiture v) {
        if (v.getNbrplace() <= 0) {
            throw new RuntimeException("Nombre de places invalide");
        }
        return voitureRepository.save(v);
    }

    public List<Voiture> findAll() {
        return voitureRepository.findAll();
    }

    public Voiture findById(Long id) {
        return voitureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voiture introuvable"));
    }

    public Voiture update(Long id, Voiture v) {
        Voiture existing = findById(id);
        existing.setDesign(v.getDesign());
        existing.setType(v.getType());
        existing.setNbrplace(v.getNbrplace());
        existing.setFrais(v.getFrais());
        return voitureRepository.save(existing);
    }

    public void delete(Long id) {
        voitureRepository.deleteById(id);
    }
}
