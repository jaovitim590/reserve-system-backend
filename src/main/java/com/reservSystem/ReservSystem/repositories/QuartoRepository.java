package com.reservSystem.ReservSystem.repositories;

import com.reservSystem.ReservSystem.models.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Integer> {
    List<Quarto> findAllByStatus(String status);

    Quarto findByNome(String nome);
}
