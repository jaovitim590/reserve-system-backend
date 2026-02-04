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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuartoService quartoService;

    @Autowired
    private ReservaService reservaService;

    private boolean isAdmin(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if (email == null) {
            return false;
        }

        return userService.isadmin(email);
    }

    private ResponseEntity<?> forbiddenResponse() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Acesso negado: apenas administradores podem realizar esta ação");
    }


    @GetMapping("/usuario")
    public ResponseEntity<?> getAllUsers(HttpServletRequest request) {
        if (!isAdmin(request)) {
            return forbiddenResponse();
        }

        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }


    @GetMapping("/reserva")
    public ResponseEntity<?> getAllReservas(HttpServletRequest request) {
        if (!isAdmin(request)) {
            return forbiddenResponse();
        }

        List<Reserva> allReservas = reservaService.getAllReservas();
        return ResponseEntity.ok(allReservas);
    }

    @DeleteMapping("/reserva/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Integer id,
                                             HttpServletRequest request) {
        if (!isAdmin(request)) {
            return forbiddenResponse();
        }

        try {
            reservaService.cancelReserva(id);
            return ResponseEntity.ok("Reserva cancelada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao cancelar reserva: " + e.getMessage());
        }
    }


    @PostMapping("/quarto")
    public ResponseEntity<?> createQuarto(HttpServletRequest request,
                                          @RequestBody @Valid QuartoDto quarto) {
        if (!isAdmin(request)) {
            return forbiddenResponse();
        }

        try {
            Quarto novoQuarto = quartoService.createQuarto(quarto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoQuarto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao criar quarto: " + e.getMessage());
        }
    }

    @PutMapping("/quarto/{id}")
    public ResponseEntity<?> updateQuarto(HttpServletRequest request,
                                          @PathVariable Integer id,
                                          @RequestBody @Valid QuartoDto quarto) {
        if (!isAdmin(request)) {
            return forbiddenResponse();
        }

        try {
            Quarto quartoAtualizado = quartoService.update(quarto);
            return ResponseEntity.ok(quartoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar quarto: " + e.getMessage());
        }
    }

    @DeleteMapping("/quarto/{id}")
    public ResponseEntity<?> deleteQuarto(HttpServletRequest request,
                                          @PathVariable Integer id) {
        if (!isAdmin(request)) {
            return forbiddenResponse();
        }

        try {
            quartoService.deleteQuarto(id);
            return ResponseEntity.ok("Quarto deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao deletar quarto: " + e.getMessage());
        }
    }

    @GetMapping("/quarto")
    public ResponseEntity<?> getAllQuartos(HttpServletRequest request) {
        if (!isAdmin(request)) {
            return forbiddenResponse();
        }

        List<Quarto> allQuartos = quartoService.getQuartos();
        return ResponseEntity.ok(allQuartos);
    }
}