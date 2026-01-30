package com.reservSystem.ReservSystem.services;


import com.reservSystem.ReservSystem.DTOS.ReservaDto;
import com.reservSystem.ReservSystem.models.Quarto;
import com.reservSystem.ReservSystem.models.Reserva;
import com.reservSystem.ReservSystem.models.StatusReserva;
import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuartoService quartoService;

    public String cadastrarReserva(ReservaDto reserva) throws Exception {
        Reserva r = new Reserva();

        try {
            r.setStatus(StatusReserva.valueOf(reserva.status()));
        }catch(Exception e){
            throw new RuntimeException("Status invalido");
        }

        User usuario =  userService.getUser(reserva.usuarioId());
        Quarto quarto = quartoService.getQuarto(reserva.quartoId());

        r.setValorTotal(calcularTotal(reserva.dataInicio(), reserva.dataFim(), quarto.getValor()));

        r.setQuarto(quarto);
        r.setUsuario(usuario);
        r.setData_fim(reserva.dataFim());
        r.setData_inicio(reserva.dataInicio());
        r.setData_criado(Instant.now());

        repository.save(r);

        return "reserva feito com sucesso";
    }

    public BigDecimal calcularTotal(
            LocalDate inicio,
            LocalDate fim,
            BigDecimal valorDiaria
    ) {
        long dias = ChronoUnit.DAYS.between(inicio, fim);

        if (dias <= 0) {
            throw new IllegalArgumentException("Datas inválidas");
        }

        return valorDiaria.multiply(BigDecimal.valueOf(dias));
    }

    public void deletarReserva(Integer id) throws Exception {
        Reserva reserva = repository.findById(id).
                orElseThrow(() -> new Exception("Reserva inexistente"));
        repository.delete(reserva);
    }

    public List<Reserva> getAllReservas(){
        return repository.findAll();
    }

    public boolean isQuartoDisponivel(Integer id,LocalDate data_inicio, LocalDate data_fim ){
        List<Reserva> reservasConflitantes = repository.findConflictingReservations(id,data_inicio,data_fim);
        return reservasConflitantes.isEmpty();
    }
}
