package com.eni.preserve.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "idcli", length = 10)
    private String idcli;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "numtel", nullable = false, length = 20)
    private String numtel;
}