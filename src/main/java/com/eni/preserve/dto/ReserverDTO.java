package com.eni.preserve.dto;

import com.eni.preserve.enums.TypePaiement;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserverDTO {
    private String idreserv;
    private String idvoit;
    private String idcli;
    private int place;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateReserv;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateVoyage;
    private TypePaiement payment;
    private int montantAvance;
}