package com.eni.preserve.entity;

import com.eni.preserve.enums.TypeVoiture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voiture" )
public class Voiture {

    @Id
    @Column(name = "idvoit")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idvoit;

    @Column(name = "design", nullable = false, length = 100)
    private String design;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeVoiture type;

    @Column(name = "nbrplace")
    private int nbrplace;

    @Column(name = "frais")
    private int frais;

}
