package com.reservSystem.ReservSystem.controllers;

import com.reservSystem.ReservSystem.DTOS.ReservaDto;
import com.reservSystem.ReservSystem.services.JwtService;
import com.reservSystem.ReservSystem.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reserva")
public class ReservaController {
    @Autowired
    private ReservaService service;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> criarReserva(ReservaDto reserva) throws Exception {
        if (!service.isQuartoDisponivel(reserva.quartoId(), reserva.dataInicio(), reserva.dataFim())){
            return ResponseEntity.status(401).body("quarto indisponivel");
        }
        try{
            return ResponseEntity.ok(
                    service.cadastrarReserva(reserva)
            );
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
