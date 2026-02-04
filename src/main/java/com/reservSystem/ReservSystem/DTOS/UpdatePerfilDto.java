package com.reservSystem.ReservSystem.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePerfilDto(
        @NotBlank(message = "Nome não pode estar vazio")
        @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
        String name


){}