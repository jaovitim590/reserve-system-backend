package com.reservSystem.ReservSystem.services;


import com.reservSystem.ReservSystem.DTOS.QuartoDto;
import com.reservSystem.ReservSystem.models.Quarto;
import com.reservSystem.ReservSystem.models.StatusQuarto;
import com.reservSystem.ReservSystem.repositories.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository repository;

    public void createQuarto(QuartoDto quarto){
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
}
