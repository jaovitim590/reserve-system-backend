package com.reservSystem.ReservSystem.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDto(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6)
        String password,

        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "ADMIN|USER")
        String role
) {
}
