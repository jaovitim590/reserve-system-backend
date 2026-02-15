package com.reservSystem.ReservSystem.services;


import com.reservSystem.ReservSystem.DTOS.QuartoDto;
import com.reservSystem.ReservSystem.models.Quarto;
import com.reservSystem.ReservSystem.models.StatusQuarto;
import com.reservSystem.ReservSystem.models.TipoQuarto;
import com.reservSystem.ReservSystem.repositories.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository repository;

    public Quarto createQuarto(QuartoDto quarto){
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
        q.setTipo(TipoQuarto.valueOf(quarto.tipo()));
        q.setData_criacao(Instant.now());

        repository.save(q);

        return q;
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
        return repository.findAllByStatus(StatusQuarto.DISPONIVEL);
    }

    public Quarto update(QuartoDto quarto) throws Exception {
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

        Optional.ofNullable(quarto.tipo())
                        .filter(tipo -> !tipo.isEmpty())
                        .ifPresent(tipo -> {
                            try {
                                existingQuarto.setTipo(TipoQuarto.valueOf(tipo));
                            }catch (IllegalArgumentException e){
                                throw new RuntimeException("Invalid role");
                            }
                        });

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
        return existingQuarto;
    }

    public String deleteQuarto(Integer id) throws Exception {
        Quarto existingQuarto = repository.findById(id)
                .orElseThrow(() -> new Exception("Quarto not found"));

        repository.deleteById(id);
        return "quarto deletado com sucesso!";
    }

    public boolean quartoExists(Integer id) {
        Optional<Quarto> q = repository.findById(id);

        if (q.isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}
