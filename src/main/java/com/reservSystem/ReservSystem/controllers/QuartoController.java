package com.reservSystem.ReservSystem.controllers;

import com.reservSystem.ReservSystem.models.Quarto;
import com.reservSystem.ReservSystem.services.JwtService;
import com.reservSystem.ReservSystem.services.QuartoService;
import com.reservSystem.ReservSystem.services.ReservaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/quarto")
public class QuartoController {

    @Autowired
    private QuartoService service;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Quarto>> GetQuartos(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                List<Quarto> quartos = service.getQuartosAtivos();
                return ResponseEntity.ok(quartos);
        }else if (!jwtService.isTokenValid(token)){
            List<Quarto> quartos = service.getQuartosAtivos();
            return ResponseEntity.ok(quartos);
        }else {
            List<Quarto> quartos = service.getQuartos();
            return ResponseEntity.ok(quartos);
        }
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

    @GetMapping("/{id}/disponibilidade")
    public ResponseEntity<?> verificarDisponibilidade(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {


        if (inicio == null || fim == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("As datas de início e fim são obrigatórias"));
        }

        if (inicio.isAfter(fim)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("A data de início deve ser anterior à data de fim"));
        }

        if (inicio.isBefore(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("A data de início não pode ser no passado"));
        }

        try {
            if (!service.quartoExists(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Quarto não encontrado"));
            }


            boolean disponivel = reservaService.isQuartoDisponivel(id, inicio, fim);

            Map<String, Object> response = new HashMap<>();
            response.put("quartoId", id);
            response.put("dataInicio", inicio);
            response.put("dataFim", fim);
            response.put("disponivel", disponivel);

            if (!disponivel) {
                response.put("mensagem", "Quarto não disponível para o período selecionado");
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erro ao verificar disponibilidade: " + e.getMessage()));
        }
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return error;
    }
}

