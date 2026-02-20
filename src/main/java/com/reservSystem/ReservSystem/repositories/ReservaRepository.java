package com.reservSystem.ReservSystem.repositories;

import com.reservSystem.ReservSystem.DTOS.QuartoPopularDto;
import com.reservSystem.ReservSystem.models.Quarto;
import com.reservSystem.ReservSystem.models.Reserva;
import com.reservSystem.ReservSystem.models.StatusReserva;
import com.reservSystem.ReservSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query("SELECT r FROM Reserva r " +
            "WHERE r.quarto.id = :quartoId " +
            "AND r.data_inicio < :dataFim AND r.data_fim > :dataInicio")
    List<Reserva> findConflictingReservations(@Param("quartoId") Integer quartoId,
                                              @Param("dataInicio") LocalDate dataInicio,
                                              @Param("dataFim") LocalDate dataFim);

    List<Reserva> findAllByUsuario(User user);

    @Query("SELECT r.quarto AS quarto, COUNT(r) AS total FROM Reserva r " +
            "GROUP BY r.quarto " +
            "ORDER BY COUNT(r) DESC " +
            "LIMIT 1")
    Optional<QuartoPopularDto> findMostReservedQuarto();

    @Query("SELECT r FROM Reserva r ORDER BY r.data_criado DESC LIMIT :limit")
    List<Reserva> findRecentReservas(@Param("limit") int limit);

    @Query("SELECT r FROM Reserva r WHERE r.data_inicio >= :inicio AND r.data_fim <= :fim")
    List<Reserva> findByPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.status = :status")
    Long countByStatus(@Param("status") StatusReserva status);

}


