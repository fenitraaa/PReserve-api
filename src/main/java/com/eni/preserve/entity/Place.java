package com.eni.preserve.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "place")
public class Place {

    @EmbeddedId
    private PlaceId id;

    @ManyToOne
    @MapsId("idvoit")
    @JoinColumn(name = "idvoit", nullable = false)
    private Voiture voiture;

    @Column(name = "occupation", nullable = false)
    private boolean occupation;
}