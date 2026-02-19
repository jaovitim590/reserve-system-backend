package com.reservSystem.ReservSystem.controllers;

import com.reservSystem.ReservSystem.DTOS.ReqReservaDto;
import com.reservSystem.ReservSystem.DTOS.ResReservaDto;
import com.reservSystem.ReservSystem.exceptions.RecursoNaoEncontradoException;
import com.reservSystem.ReservSystem.models.Reserva;
import com.reservSystem.ReservSystem.models.StatusReserva;
import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.services.JwtService;
import com.reservSystem.ReservSystem.services.ReservaService;
import com.reservSystem.ReservSystem.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reserva")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private Optional<String> extractEmail(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }

        String token = authHeader.substring(7);
        return Optional.ofNullable(jwtService.extractEmail(token));
    }

    @SneakyThrows
    private User getAuthenticatedUser(HttpServletRequest request){
        return extractEmail(request)
                .map(email ->{
                    try {
                        return userService.findByEmail(email);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Usuário não autenticado"));
    }

    @PostMapping
    public ResponseEntity<?> criarReserva(HttpServletRequest request,
                                          @RequestBody @Valid ReqReservaDto reserva) {
        if (!service.isQuartoDisponivel(reserva.quartoId(),
                reserva.dataInicio(),
                reserva.dataFim())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Quarto indisponível para as datas selecionadas");
        }

        try {
            User user = getAuthenticatedUser(request);

            ResReservaDto novaReserva = service.cadastrarReserva(reserva, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/minhas")
    public ResponseEntity<?> getMinhasReservas(HttpServletRequest request) {
        try {
            String email = extractEmail(request)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("usuario"));

            List<Reserva> reservas = service.getAllReservasByUser(email);
            return ResponseEntity.ok(reservas);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar reservas");
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarReserva(@PathVariable Integer id,
                                             HttpServletRequest request) {
        try {
            User user = getAuthenticatedUser(request);
            Reserva reserva = service.getReservaById(id);

            if (!(reserva.getUsuario().getId() == user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Você só pode cancelar suas próprias reservas");
            }

            service.cancelReserva(id);
            return ResponseEntity.ok("Reserva cancelada com sucesso");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cancelar reserva");
        }
    }
}