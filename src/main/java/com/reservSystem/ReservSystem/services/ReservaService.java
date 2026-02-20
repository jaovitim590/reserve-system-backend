package com.reservSystem.ReservSystem.services;

import com.reservSystem.ReservSystem.DTOS.*;
import com.reservSystem.ReservSystem.exceptions.RecursoNaoEncontradoException;
import com.reservSystem.ReservSystem.models.*;
import com.reservSystem.ReservSystem.repositories.ReservaRepository;
import jakarta.transaction.Transactional;
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

    public ResReservaDto cadastrarReserva(ReqReservaDto reserva, User usuario) throws Exception {
        Reserva r = new Reserva();

        Quarto quarto = quartoService.getQuarto(reserva.quartoId());

        r.setValorTotal(calcularTotal(reserva.dataInicio(), reserva.dataFim(), quarto.getValor()));

        r.setQuarto(quarto);
        r.setUsuario(usuario);
        r.setData_fim(reserva.dataFim());
        r.setData_inicio(reserva.dataInicio());
        r.setData_criado(Instant.now());

        repository.save(r);
        ResReservaDto newReserva = new ResReservaDto(r.getId(),r.getQuarto(),r.getData_inicio(), r.getData_fim(),StatusReserva.ATIVA.toString(),r.getValorTotal());

        return newReserva;
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

    public void deletarReserva(Integer id){
        Reserva reserva = repository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("reserva"));
        repository.delete(reserva);
    }

    public List<Reserva> getAllReservas(){
        return repository.findAll();
    }

    public boolean isQuartoDisponivel(Integer id,LocalDate data_inicio, LocalDate data_fim ){
        List<Reserva> reservasConflitantes = repository.findConflictingReservations(id,data_inicio,data_fim);
        return reservasConflitantes.isEmpty();
    }

    @Transactional
    public List<ResReservaDto> getAllReservasByUser(User user) throws Exception {

        return repository.findAllByUsuario(user)
                .stream()
                .map(r -> new ResReservaDto(
                        r.getId(),
                        r.getQuarto(),
                        r.getData_inicio(),
                        r.getData_fim(),
                        r.getStatus().toString(),
                        r.getValorTotal()
                ))
                .toList();
    }

    public String cancelReserva(Integer id){
        Reserva reserva =  repository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("reserva"));

        if (reserva.getStatus() == StatusReserva.CANCELADO){
            return "reserva ja foi cancelada";
        }else {
            reserva.setStatus(StatusReserva.CANCELADO);
            repository.save(reserva);
            return "Reserva cancelada!";
        }
    }

    public Reserva getReservaById(Integer id) throws Exception {
        Reserva reserva = repository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("reserva"));
        return reserva;
    }

    public ReceitaDto getReceitasGerais(){
        List<Reserva> allReservas = repository.findAll();
        BigDecimal receitaAtivas = BigDecimal.ZERO;
        BigDecimal receitaCanceladas= BigDecimal.ZERO;

        for (Reserva r : allReservas) {
            if (r.getStatus() == StatusReserva.ATIVA) {
                receitaAtivas = receitaAtivas.add(r.getValorTotal());
            } else if (r.getStatus() == StatusReserva.CANCELADO) {
                receitaCanceladas = receitaCanceladas.add(r.getValorTotal());
            }
        }

        return new ReceitaDto(receitaAtivas, receitaCanceladas);
    }

    public StatsDto getStats() {
        Long totalReservas = repository.count();
        Long ativas = repository.countByStatus(StatusReserva.ATIVA);
        Long canceladas = repository.countByStatus(StatusReserva.CANCELADO);
        Long totalUsuarios = userService.countUsers();
        Long totalQuartos = quartoService.countQuartos();
        Long ocupados = quartoService.countByStatus(StatusQuarto.OCUPADO);
        Double taxa = totalQuartos > 0 ? (ocupados * 100.0 / totalQuartos) : 0.0;

        return new StatsDto(totalUsuarios, totalReservas, totalQuartos, ativas, canceladas, taxa);
    }

    public List<Reserva> getRecentReservas(int limit) {
        return repository.findRecentReservas(limit);
    }

    public ReceitaPeriodoDto getReceitaPorPeriodo(LocalDate inicio, LocalDate fim) {
        List<Reserva> reservas = repository.findByPeriodo(inicio, fim);
        BigDecimal ativas = BigDecimal.ZERO;
        BigDecimal canceladas = BigDecimal.ZERO;

        for (Reserva r : reservas) {
            if (r.getStatus() == StatusReserva.ATIVA) {
                ativas = ativas.add(r.getValorTotal());
            } else if (r.getStatus() == StatusReserva.CANCELADO) {
                canceladas = canceladas.add(r.getValorTotal());
            }
        }

        return new ReceitaPeriodoDto(inicio, fim, ativas, canceladas, ativas.add(canceladas));
    }

    public List<Reserva> getReservasPorPeriodo(LocalDate inicio, LocalDate fim) {
        return repository.findByPeriodo(inicio, fim);
    }
}
