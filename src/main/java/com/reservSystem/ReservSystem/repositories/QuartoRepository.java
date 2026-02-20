package com.reservSystem.ReservSystem.repositories;

import com.reservSystem.ReservSystem.models.Quarto;
import com.reservSystem.ReservSystem.models.StatusQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Integer> {
    List<Quarto> findAllByStatus(StatusQuarto status);

    Quarto findByName(String name);
    Long countByStatus(StatusQuarto status);
}
