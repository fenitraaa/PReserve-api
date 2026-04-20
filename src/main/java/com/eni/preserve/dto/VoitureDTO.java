package com.eni.preserve.dto;

import com.eni.preserve.enums.TypeVoiture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoitureDTO {
    private Long idvoit;
    private String design;
    private TypeVoiture type;
    private int nbrplace;
    private int frais;
}
