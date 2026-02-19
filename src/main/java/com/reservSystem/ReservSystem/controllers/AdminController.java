package com.reservSystem.ReservSystem.controllers;

import com.reservSystem.ReservSystem.DTOS.QuartoDto;
import com.reservSystem.ReservSystem.models.Quarto;
import com.reservSystem.ReservSystem.models.Reserva;
import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.services.JwtService;
import com.reservSystem.ReservSystem.services.QuartoService;
import com.reservSystem.ReservSystem.services.ReservaService;
import com.reservSystem.ReservSystem.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuartoService quartoService;

    @Autowired
    private ReservaService reservaService;


    @GetMapping("/usuario")
    public ResponseEntity<?> getAllUsers() {

        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }


    @GetMapping("/reserva")
    public ResponseEntity<?> getAllReservas() {
        List<Reserva> allReservas = reservaService.getAllReservas();
        return ResponseEntity.ok(allReservas);
    }

    @DeleteMapping("/reserva/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Integer id) {
        try {
            reservaService.cancelReserva(id);
            return ResponseEntity.ok("Reserva cancelada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao cancelar reserva: " + e.getMessage());
        }
    }


    @PostMapping("/quarto")
    public ResponseEntity<?> createQuarto(@RequestBody @Valid QuartoDto quarto) {
        try {
            Quarto novoQuarto = quartoService.createQuarto(quarto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoQuarto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao criar quarto: " + e.getMessage());
        }
    }

    @PutMapping("/quarto/{id}")
    public ResponseEntity<?> updateQuarto(@PathVariable Integer id,@RequestBody @Valid QuartoDto quarto) {
        try {
            Quarto quartoAtualizado = quartoService.update(quarto);
            return ResponseEntity.ok(quartoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar quarto: " + e.getMessage());
        }
    }

    @DeleteMapping("/quarto/{id}")
    public ResponseEntity<?> deleteQuarto(@PathVariable Integer id) {
        try {
            quartoService.deleteQuarto(id);
            return ResponseEntity.ok("Quarto deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao deletar quarto: " + e.getMessage());
        }
    }

    @GetMapping("/quarto")
    public ResponseEntity<?> getAllQuartos() {
        List<Quarto> allQuartos = quartoService.getQuartos();
        return ResponseEntity.ok(allQuartos);
    }
}