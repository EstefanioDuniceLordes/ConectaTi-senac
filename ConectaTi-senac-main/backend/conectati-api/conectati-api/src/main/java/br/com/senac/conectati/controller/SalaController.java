package br.com.senac.conectati.controller;

import br.com.senac.conectati.dto.request.SalaRequest;
import br.com.senac.conectati.dto.response.SalaResponse;
import br.com.senac.conectati.service.SalaService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/salas")
@RequiredArgsConstructor
public class SalaController {


    private final SalaService service;



    @PostMapping
    public ResponseEntity<SalaResponse> salvar(
            @Valid @RequestBody SalaRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(request));

    }



    @GetMapping
    public ResponseEntity<List<SalaResponse>> listar(){

        return ResponseEntity.ok(service.listar());

    }



    @GetMapping("/{id}")
    public ResponseEntity<SalaResponse> buscarPorId(
            @PathVariable Long id){

        return ResponseEntity.ok(service.buscarPorId(id));

    }



    @PutMapping("/{id}")
    public ResponseEntity<SalaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody SalaRequest request){

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
