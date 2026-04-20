package com.eni.preserve.mapper;

import org.springframework.stereotype.Component;

import com.eni.preserve.dto.VoitureDTO;
import com.eni.preserve.entity.Voiture;

@Component
public class VoitureMapper {

    public VoitureDTO toDTO(Voiture v) {
        if (v == null) return null;

        return new VoitureDTO(
                v.getIdvoit(),
                v.getDesign(),
                v.getType(),
                v.getNbrplace(),
                v.getFrais()
        );
    }

    public Voiture toEntity(VoitureDTO dto) {
        if (dto == null) return null;

        Voiture v = new Voiture();
        v.setIdvoit(dto.getIdvoit()); 
        v.setDesign(dto.getDesign());
        v.setType(dto.getType());
        v.setNbrplace(dto.getNbrplace());
        v.setFrais(dto.getFrais());

        return v;
    }

    public void updateEntity(Voiture v, VoitureDTO dto) {
        v.setDesign(dto.getDesign());
        v.setType(dto.getType());
        v.setNbrplace(dto.getNbrplace());
        v.setFrais(dto.getFrais());
    }
}
