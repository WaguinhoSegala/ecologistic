package com.projeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.projeto.api.entity.PontoModel;
import com.projeto.api.exception.NotFoundException;
import com.projeto.api.repository.PontoRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PontoController {

    @Autowired
    private PontoRepository repository;

    @GetMapping("/ponto/{userId}")
    public List<PontoModel> getAllPontosByUserId(@PathVariable Long userId) {
        return repository.findByUserId(userId);
    }

    @PostMapping("/ponto")
    public PontoModel adicionar(@RequestBody PontoModel Ponto) {
        return repository.save(Ponto);
    }

    @DeleteMapping("/ponto/{id}")
    public void deletar(@PathVariable Long id) {
        PontoModel PontoEncontrado = repository.findById(id).orElse(null);

        if (PontoEncontrado == null) {
            throw new NotFoundException("Ponto");
        } 

        repository.delete(PontoEncontrado);

    }
}