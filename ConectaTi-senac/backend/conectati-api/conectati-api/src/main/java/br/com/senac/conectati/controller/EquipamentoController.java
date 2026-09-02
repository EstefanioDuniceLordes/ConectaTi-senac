package br.com.senac.conectati.controller;

import br.com.senac.conectati.dto.request.EquipamentoRequest;
import br.com.senac.conectati.dto.response.EquipamentoResponse;
import br.com.senac.conectati.service.EquipamentoService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/equipamentos")
@RequiredArgsConstructor
public class EquipamentoController {


    private final EquipamentoService service;



    @PostMapping
    public ResponseEntity<EquipamentoResponse> salvar(
            @Valid @RequestBody EquipamentoRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(request));

    }



    @GetMapping
    public ResponseEntity<List<EquipamentoResponse>> listar(){

        return ResponseEntity.ok(service.listar());

    }



    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> buscarPorId(
            @PathVariable Long id){

        return ResponseEntity.ok(service.buscarPorId(id));

    }



    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EquipamentoRequest request){

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
