package com.reservSystem.ReservSystem.DTOS;

import java.time.LocalDate;

public record ReqReservaDto(
        Integer quartoId,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
