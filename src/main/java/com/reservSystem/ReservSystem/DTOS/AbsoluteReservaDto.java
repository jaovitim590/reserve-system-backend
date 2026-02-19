package com.reservSystem.ReservSystem.DTOS;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AbsoluteReservaDto(
        @NotNull
        Integer id,

        @NotNull
        Integer quartoId,

        @NotNull
        Integer usuarioId,

        @NotNull
        @FutureOrPresent
        LocalDate dataInicio,

        @NotNull
        @Future
        LocalDate dataFim,

        @NotNull
        BigDecimal valorTotal,

        @NotBlank
        @Pattern(regexp = "ATIVA|CANCELADO")
        String status
) {
}
