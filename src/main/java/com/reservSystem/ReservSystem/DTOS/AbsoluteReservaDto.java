package com.reservSystem.ReservSystem.DTOS;

import jakarta.validation.constraints.*;

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

        @NotBlank
        @Pattern(regexp = "ATIVA|CANCELADO")
        String status
) {
}
