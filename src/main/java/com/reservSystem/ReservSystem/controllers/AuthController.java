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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto dto) {
        try {
            User user = userService.findByEmail(dto.email());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(createErrorResponse("Credenciais inválidas"));
            }

            if (!encoder.matches(dto.password(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(createErrorResponse("Credenciais inválidas"));
            }

            String token = jwtService.generateToken(user);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("type", "Bearer");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erro ao processar login"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto dto) {
        try {
            boolean existingUser = userService.existByEmail(dto.email());
            if (existingUser) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(createErrorResponse("Email já cadastrado"));
            }

            String token = userService.createUser(dto);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("type", "Bearer");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("Erro ao criar usuário: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return ResponseEntity.ok(Map.of(
                "message", "Logout realizado com sucesso",
                "note", "O token será invalidado quando expirar"
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        Optional<String> emailOpt = extractEmail(request);

        if (emailOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Token inválido ou não fornecido"));
        }

        try {
            User user = userService.findByEmail(emailOpt.get());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Usuário não encontrado"));
            }

            MeDto userInfo = new MeDto(
                    user.getEmail(),
                    user.getName(),
                    user.getRole().name()
            );

            return ResponseEntity.ok(userInfo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erro ao buscar informações do usuário"));
        }
    }


    private Optional<String> extractEmail(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }

        try {
            String token = authHeader.substring(7);
            String email = jwtService.extractEmail(token);
            return Optional.ofNullable(email);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return error;
    }
}