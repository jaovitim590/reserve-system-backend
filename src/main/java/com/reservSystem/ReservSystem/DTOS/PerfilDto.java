package com.reservSystem.ReservSystem.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record PerfilDto(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String name,

        @NotBlank
        Instant data_criado
        ) {
}
