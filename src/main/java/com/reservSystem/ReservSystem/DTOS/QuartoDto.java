package com.reservSystem.ReservSystem.DTOS;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record QuartoDto(

        Integer id,

        @NotBlank
        String name,

        @NotBlank
        String descricao,

        @NotNull
        @Min(1)
        Integer capacidade,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal valor,

        @NotBlank
        @Pattern(regexp = "OCUPADO|DISPONIVEL")
        String status,

        @NotBlank
        @Pattern(regexp = "SOLTEIRO|CASAL|SUITE|LUXO")
        String tipo
) {}