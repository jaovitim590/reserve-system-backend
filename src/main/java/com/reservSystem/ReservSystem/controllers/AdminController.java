package com.reservSystem.ReservSystem.controllers;


import com.reservSystem.ReservSystem.DTOS.QuartoDto;
import com.reservSystem.ReservSystem.DTOS.ReservaDto;
import com.reservSystem.ReservSystem.models.Quarto;
import com.reservSystem.ReservSystem.models.Reserva;
import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.services.JwtService;
import com.reservSystem.ReservSystem.services.QuartoService;
import com.reservSystem.ReservSystem.services.ReservaService;
import com.reservSystem.ReservSystem.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean checkAdmin(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if (userService.isadmin(email)){
            return true;
        }else {
            return false;
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> getAllUsers(HttpServletRequest request) {
        if (checkAdmin(request)) {
            List<User> allUsers = userService.getAllUsers();
            return ResponseEntity.ok(allUsers);
        } else {
            return ResponseEntity.status(401).body("user nao é admin ou nao encontrado!");
        }
    }

    @GetMapping("/quarto")
    public ResponseEntity<?> getAllQuartos(HttpServletRequest request) {
        if (checkAdmin(request)){
            List<Quarto> allQuartos = quartoService.getQuartos();
            return ResponseEntity.ok(allQuartos);
        }else {
            return ResponseEntity.status(401).body("user nao é admin");
        }
    }

    @GetMapping("/reserva")
    public ResponseEntity<?> getAllReserva(HttpServletRequest request){
        if (checkAdmin(request)){
            List<Reserva> allReservas = reservaService.getAllReservas();
            return ResponseEntity.ok(allReservas);
        }else {
            return ResponseEntity.status(401).body("user nao é admin");
        }
    }

    @PostMapping("/quarto")
    public ResponseEntity<?> createQuarto(HttpServletRequest request, QuartoDto quarto){
        if (checkAdmin(request)){
            return ResponseEntity.ok(quartoService.createQuarto(quarto));
        }else {
            return ResponseEntity.status(401).body("user nao é admin");
        }
    }

    @PutMapping("/quarto")
    public ResponseEntity<?> updateQuarto(HttpServletRequest request,QuartoDto quarto) throws Exception {
        if (checkAdmin(request)){
            return ResponseEntity.ok(quartoService.update(quarto));
        }else {
            return ResponseEntity.status(401).body("user nao é admin");
        }
    }
}