package com.reservSystem.ReservSystem.controllers;


import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.services.JwtService;
import com.reservSystem.ReservSystem.services.QuartoService;
import com.reservSystem.ReservSystem.services.ReservaService;
import com.reservSystem.ReservSystem.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/usuarios")
    public ResponseEntity<?> getAllUsers(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if (userService.isadmin(email)){
            List<User> allUsers = userService.getAllUsers();
            return ResponseEntity.ok(allUsers);
        }else {
            return ResponseEntity.status(401).body("user nao é admin ou nao encontrado!");
        }

    }
}
