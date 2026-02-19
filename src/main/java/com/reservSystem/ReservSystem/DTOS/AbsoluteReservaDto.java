package com.reservSystem.ReservSystem.DTOS;

import com.reservSystem.ReservSystem.models.Quarto;
import com.reservSystem.ReservSystem.models.User;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AbsoluteReservaDto(
        @NotNull
        Integer id,

        @NotNull
        Quarto quartoId,

        @NotNull
        User usuarioId,

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
