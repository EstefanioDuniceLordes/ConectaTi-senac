package br.com.senac.conectati.controller;

import br.com.senac.conectati.dto.request.CategoriaRequest;
import br.com.senac.conectati.dto.response.CategoriaResponse;
import br.com.senac.conectati.service.CategoriaService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {


    private final CategoriaService service;



    @PostMapping
    public ResponseEntity<CategoriaResponse> salvar(
            @Valid @RequestBody CategoriaRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(request));

    }



    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar(){

        return ResponseEntity.ok(service.listar());

    }



    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(
            @PathVariable Long id){

        return ResponseEntity.ok(service.buscarPorId(id));

    }



    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest request){

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
