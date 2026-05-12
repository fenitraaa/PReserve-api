package com.eni.preserve.dto;

import com.eni.preserve.enums.TypePaiement;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserverDTO {
    private Long idreserv;
    private Long idvoit;
    private Integer idcli;
    private int place;
    private LocalDateTime dateReserv;
    private LocalDate dateVoyage;
    private TypePaiement payment;
    private int montantAvance;
}