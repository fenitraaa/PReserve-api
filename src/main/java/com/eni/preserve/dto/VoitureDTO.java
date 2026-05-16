package com.eni.preserve.dto;

import com.eni.preserve.enums.TypeVoiture;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoitureDTO {
    private String idvoit;
    private String design;
    private TypeVoiture type;
    private int nbrplace;
    private int frais;
}