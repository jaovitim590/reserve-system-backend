package com.reservSystem.ReservSystem.DTOS;

public record StatsDto(
        Long totalUsuarios,
        Long totalReservas,
        Long totalQuartos,
        Long reservasAtivas,
        Long reservasCanceladas,
        Double taxaOcupacao
) {}