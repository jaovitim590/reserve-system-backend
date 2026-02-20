package com.reservSystem.ReservSystem.DTOS;

import java.math.BigDecimal;

public record ReceitaDto(
        BigDecimal ativa,
        BigDecimal canceladas
) {

}
