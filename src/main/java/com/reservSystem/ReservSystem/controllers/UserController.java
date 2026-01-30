package com.reservSystem.ReservSystem.controllers;

import com.reservSystem.ReservSystem.DTOS.LoginDto;
import com.reservSystem.ReservSystem.DTOS.PerfilDto;
import com.reservSystem.ReservSystem.DTOS.UserDto;
import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.services.UserService;
import com.reservSystem.ReservSystem.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/perfil")
    public ResponseEntity<PerfilDto> ver_perfil(HttpServletRequest request) throws Exception{
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        try {
            User user = service.findByEmail(email);
            return ResponseEntity.ok(
                    new PerfilDto(user.getEmail(),user.getName(), user.getData_criado())
            );
        }catch (Exception e){
            return ResponseEntity.status(401).build();
        }
    }

    @PutMapping("/perfil")
    public String atualizarUser(UserDto user) throws Exception {
        return service.update(user);
    }

}
