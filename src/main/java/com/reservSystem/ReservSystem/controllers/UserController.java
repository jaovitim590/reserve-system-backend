package com.reservSystem.ReservSystem.controllers;

import com.reservSystem.ReservSystem.DTOS.PerfilDto;
import com.reservSystem.ReservSystem.DTOS.UserDto;
import com.reservSystem.ReservSystem.DTOS.UpdatePerfilDto;
import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.services.UserService;
import com.reservSystem.ReservSystem.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfil(HttpServletRequest request) {
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

            PerfilDto perfil = new PerfilDto(
                    user.getEmail(),
                    user.getName(),
                    user.getData_criado()
            );

            return ResponseEntity.ok(perfil);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erro ao buscar perfil"));
        }
    }

    @PutMapping("/perfil")
    public ResponseEntity<?> atualizarPerfil(
            HttpServletRequest request,
            @RequestBody @Valid UpdatePerfilDto updateDto) {

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

            User updatedUser = userService.updatePerfil(user.getId(), updateDto);

            PerfilDto perfil = new PerfilDto(
                    updatedUser.getEmail(),
                    updatedUser.getName(),
                    updatedUser.getData_criado()
            );

            return ResponseEntity.ok(Map.of(
                    "message", "Perfil atualizado com sucesso",
                    "perfil", perfil
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erro ao atualizar perfil"));
        }
    }

    @DeleteMapping("/perfil")
    public ResponseEntity<?> deletarConta(HttpServletRequest request) {
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

            userService.deleteUser(user.getId());

            return ResponseEntity.ok(Map.of(
                    "message", "Conta deletada com sucesso"
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erro ao deletar conta"));
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