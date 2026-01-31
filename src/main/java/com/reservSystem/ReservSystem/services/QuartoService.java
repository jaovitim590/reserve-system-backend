package com.reservSystem.ReservSystem.services;


import com.reservSystem.ReservSystem.DTOS.QuartoDto;
import com.reservSystem.ReservSystem.models.Quarto;
import com.reservSystem.ReservSystem.models.StatusQuarto;
import com.reservSystem.ReservSystem.repositories.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository repository;

    public String createQuarto(QuartoDto quarto){
        Quarto q =  new Quarto();

        try {
            q.setStatus(StatusQuarto.valueOf(quarto.status()));
        }catch (Exception e){
            throw new RuntimeException("Status invalido");
        }
        q.setDescricao(quarto.descricao());
        q.setCapacidade(quarto.capacidade());
        q.setName(quarto.name());
        q.setValor(quarto.valor());
        q.setData_criacao(Instant.now());

        repository.save(q);

        return "quarto criado com sucesso";
    }

    public Quarto getQuarto(Integer id) throws Exception{
        Quarto q = repository.findById(id).
                orElseThrow(() -> new Exception("quarto not found"));
        return q;
    }

    public List<Quarto> getQuartos(){
        return repository.findAll();
    }

    public List<Quarto> getQuartosAtivos(){
        return repository.findAllByStatus("VAGO");
    }

    public String update(QuartoDto quarto) throws Exception {
        Quarto existingQuarto = repository.findById(quarto.id())
                .orElseThrow(() -> new Exception("Quarto not found"));

        Optional.ofNullable(quarto.name())
                .filter(name -> !name.isEmpty())
                .ifPresent(existingQuarto::setName);

        Optional.ofNullable(quarto.descricao())
                .filter(desc -> !desc.isEmpty())
                .ifPresent(existingQuarto::setDescricao);

        Optional.ofNullable(quarto.capacidade())
                .filter(cap -> cap > 0)
                .ifPresent(existingQuarto::setCapacidade);

        Optional.ofNullable(quarto.valor())
                .filter(val -> val.compareTo(BigDecimal.ZERO) > 0)
                .ifPresent(existingQuarto::setValor);

        Optional.ofNullable(quarto.status())
                .filter(statusStr -> !statusStr.isEmpty())
                .ifPresent(statusStr -> {
                    try {
                        existingQuarto.setStatus(StatusQuarto.valueOf(statusStr));
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Invalid status");
                    }
                });

        repository.save(existingQuarto);
        return "Quarto updated successfully";
    }
}
