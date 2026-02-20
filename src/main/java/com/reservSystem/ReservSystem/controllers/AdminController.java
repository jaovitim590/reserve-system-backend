package com.reservSystem.ReservSystem.controllers;

import com.reservSystem.ReservSystem.DTOS.QuartoDto;
import com.reservSystem.ReservSystem.DTOS.QuartoPopularDto;
import com.reservSystem.ReservSystem.DTOS.ReceitaDto;
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

import java.time.LocalDate;
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

    @GetMapping("/receita")
    public ResponseEntity<?> getReceita(){
        try{
            ReceitaDto receitas = reservaService.getReceitasGerais();
            return ResponseEntity.ok(receitas);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("erro ao procurar receitas: "+ e.getMessage());
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

    @GetMapping("/quarto/best")
    public ResponseEntity<?> getBestQuarto(){
        QuartoPopularDto quarto = quartoService.getQuartoMaisReservado();
        return ResponseEntity.ok(quarto);
    }
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        try {
            return ResponseEntity.ok(reservaService.getStats());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar estatísticas: " + e.getMessage());
        }
    }

    @GetMapping("/reserva/recentes")
    public ResponseEntity<?> getRecentReservas(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            return ResponseEntity.ok(reservaService.getRecentReservas(limit));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar reservas recentes: " + e.getMessage());
        }
    }

    @GetMapping("/reserva/periodo")
    public ResponseEntity<?> getReservasPorPeriodo(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        try {
            List<Reserva> reservas = reservaService.getReservasPorPeriodo(dataInicio, dataFim);
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar reservas: " + e.getMessage());
        }
    }

    @GetMapping("/receita/periodo")
    public ResponseEntity<?> getReceitaPorPeriodo(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        try {
            return ResponseEntity.ok(reservaService.getReceitaPorPeriodo(dataInicio, dataFim));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar receita: " + e.getMessage());
        }
    }

    @GetMapping("/quarto/status")
    public ResponseEntity<?> getQuartosPorStatus() {
        try {
            return ResponseEntity.ok(quartoService.getQuartosPorStatus());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar status dos quartos: " + e.getMessage());
        }
    }

}