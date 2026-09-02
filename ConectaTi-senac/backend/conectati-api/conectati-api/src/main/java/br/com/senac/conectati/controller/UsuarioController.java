package br.com.senac.conectati.controller;

import br.com.senac.conectati.dto.request.UsuarioRequest;
import br.com.senac.conectati.dto.response.UsuarioResponse;
import br.com.senac.conectati.service.UsuarioService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {


    private final UsuarioService service;



    @PostMapping
    public ResponseEntity<UsuarioResponse> salvar(
            @Valid @RequestBody UsuarioRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(request));

    }



    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar(){

        return ResponseEntity.ok(service.listar());

    }



    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(
            @PathVariable Long id){

        return ResponseEntity.ok(service.buscarPorId(id));

    }



    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request){

        return ResponseEntity.ok(
                service.atualizar(id, request)
        );

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id){

        service.deletar(id);

        return ResponseEntity.noContent().build();

    }

}
