package com.eni.preserve.entity;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "place")
public class Place {
    @Id
    @ManyToOne
    @JoinColumn(name = "idvoit", nullable = false)
    private Voiture voiture;

    @Column(name = "place", nullable = false)
    private int place;

    @Column(name = "occupation", nullable = false)
    private boolean occupation;

}
