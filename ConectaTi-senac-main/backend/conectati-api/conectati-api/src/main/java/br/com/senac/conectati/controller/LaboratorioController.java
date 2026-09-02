package br.com.senac.conectati.controller;

import br.com.senac.conectati.dto.request.LaboratorioRequest;
import br.com.senac.conectati.dto.response.LaboratorioResponse;
import br.com.senac.conectati.service.LaboratorioService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/laboratorios")
@RequiredArgsConstructor
public class LaboratorioController {


    private final LaboratorioService service;



    @PostMapping
    public ResponseEntity<LaboratorioResponse> salvar(
            @Valid @RequestBody LaboratorioRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(request));

    }



    @GetMapping
    public ResponseEntity<List<LaboratorioResponse>> listar(){

        return ResponseEntity.ok(service.listar());

    }



    @GetMapping("/{id}")
    public ResponseEntity<LaboratorioResponse> buscarPorId(
            @PathVariable Long id){

        return ResponseEntity.ok(service.buscarPorId(id));

    }



    @PutMapping("/{id}")
    public ResponseEntity<LaboratorioResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LaboratorioRequest request){

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
