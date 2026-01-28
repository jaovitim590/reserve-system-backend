package com.reservSystem.ReservSystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusReserva status;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @Column(nullable = false)
    private Date data_inicio;

    @Column(nullable = false)
    private Date data_fim;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    private Date data_criado;
}
