package com.reservSystem.ReservSystem.DTOS;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String message,
        Instant timestamp
) {}