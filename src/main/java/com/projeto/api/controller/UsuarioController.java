package com.projeto.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.api.dto.UsuarioDTO;
import com.projeto.api.entity.UsuarioModel;
import com.projeto.api.exception.BadRequestException;
import com.projeto.api.exception.NotFoundException;
import com.projeto.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping("/login")
    public UsuarioDTO login(@RequestBody UsuarioModel usuario) {
        UsuarioModel usuarioEncontrado = repository.findByEmail(usuario.getEmail());

        if (usuarioEncontrado == null) {
            throw new NotFoundException("Usuario");
        }

        if (!usuarioEncontrado.getSenha().equals(usuario.getSenha())) {
            throw new BadRequestException("Senha incorreta.");
        }

        return toDTO(usuarioEncontrado);
    }
    @GetMapping("/users")
    public List<UsuarioDTO> getAllUsers() {
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>(); 

        for (UsuarioModel usuario : repository.findAll()) { 
            usuarioDTOList.add(toDTO(usuario));
        }

        return usuarioDTOList; 
    }

    @PostMapping("/users")
    public UsuarioDTO adicionar(@RequestBody UsuarioModel usuario) {
        UsuarioModel usuarioEncontrado = repository.findByEmail(usuario.getEmail());
        if (usuarioEncontrado != null) {
            throw new BadRequestException("Usuário já cadastrado com este email.");
        }
        return toDTO(repository.save(usuario));
    }

    @DeleteMapping("/users/{id}")
    public void deletar(@PathVariable Long id) {
        UsuarioModel usuarioEncontrado = repository.findById(id).orElse(null);

        if (usuarioEncontrado == null) {
            throw new NotFoundException("Usuario");
        } 

        repository.delete(usuarioEncontrado);

    }

    public UsuarioDTO toDTO(UsuarioModel usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        return dto;
    }

}