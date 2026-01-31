package com.reservSystem.ReservSystem.repositories;

import com.reservSystem.ReservSystem.models.Reserva;
import com.reservSystem.ReservSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query("SELECT r FROM Reserva r " +
            "WHERE r.quarto.id = :quartoId " +
            "AND r.data_inicio < :dataFim AND r.data_fim > :dataInicio")
    List<Reserva> findConflictingReservations(@Param("quartoId") Integer quartoId,
                                              @Param("dataInicio") LocalDate dataInicio,
                                              @Param("dataFim") LocalDate dataFim);

    List<Reserva> findAllByUsuario(User user);
}


