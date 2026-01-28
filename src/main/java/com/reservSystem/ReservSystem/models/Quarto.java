package com.reservSystem.ReservSystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StatusQuarto status;

    private String descricao;

    private Integer capacidade;

    private String name;

    private BigDecimal valor;

    @Column(nullable = false, updatable = false)
    private Instant data_criacao;
}
