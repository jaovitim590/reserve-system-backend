package com.reservSystem.ReservSystem.DTOS;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResReservaDto(

        Integer id,

        @NotNull
        Integer quartoId,

        @NotNull
        @FutureOrPresent
        LocalDate dataInicio,

        @NotNull
        @Future
        LocalDate dataFim,

        @NotBlank
        @Pattern(regexp = "ATIVA|CANCELADO")
        String status,

        @NotNull
        BigDecimal valorTotal
) {

}