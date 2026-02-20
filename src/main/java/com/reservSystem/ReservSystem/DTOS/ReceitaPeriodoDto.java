package com.reservSystem.ReservSystem.DTOS;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReceitaPeriodoDto(
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal receitaAtivas,
        BigDecimal receitaCanceladas,
        BigDecimal receitaTotal
) {}