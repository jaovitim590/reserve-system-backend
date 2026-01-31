package com.reservSystem.ReservSystem.controllers;

import com.reservSystem.ReservSystem.models.Quarto;
import com.reservSystem.ReservSystem.services.JwtService;
import com.reservSystem.ReservSystem.services.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quarto")
public class QuartoController {

    @Autowired
    private QuartoService service;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Quarto>> GetQuartos(){
            List<Quarto> quartos = service.getQuartosAtivos();
            return ResponseEntity.ok(quartos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetQuartoInfo(@PathVariable Integer id){
        try {
            Quarto quarto = service.getQuarto(id);
            return ResponseEntity.ok(quarto);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
