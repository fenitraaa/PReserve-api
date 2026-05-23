package com.eni.preserve.entity;

import com.eni.preserve.enums.TypePaiement;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reserver")
public class Reserver {

    @Id
    @Column(name = "idreserv", length = 10)
    private String idreserv;

    @ManyToOne
    @JoinColumn(name = "idvoit", nullable = false)
    private Voiture voiture;

    @ManyToOne
    @JoinColumn(name = "idcli", nullable = false)
    private Client client;

    @Column(name = "place", nullable = false)
    private int place;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_reserv", nullable = false)
    private LocalDateTime dateReserv;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_voyage", nullable = false)
    private LocalDateTime dateVoyage;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment", nullable = false)
    private TypePaiement payment;

    @Column(name = "montant_avance")
    private int montantAvance;
}