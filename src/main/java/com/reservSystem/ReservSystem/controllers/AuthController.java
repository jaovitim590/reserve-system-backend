package com.reservSystem.ReservSystem.controllers;

import com.reservSystem.ReservSystem.DTOS.LoginDto;
import com.reservSystem.ReservSystem.DTOS.MeDto;
import com.reservSystem.ReservSystem.DTOS.UserDto;
import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.services.JwtService;
import com.reservSystem.ReservSystem.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService service;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto dto) throws Exception {
        User user = service.findByEmail(dto.email());

        if (!encoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return JwtService.generateToken(user);
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid UserDto dto) throws Exception {
        return service.createUser(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout realizado");
    }

    @GetMapping("/me")
    public ResponseEntity<MeDto> me(HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        try {
            User user = service.findByEmail(email);
            return ResponseEntity.ok(
                    new MeDto(user.getEmail(), user.getName(), user.getRole().name()));
        }catch (Exception e){
            return ResponseEntity.status(401).build();
        }

    }

}
