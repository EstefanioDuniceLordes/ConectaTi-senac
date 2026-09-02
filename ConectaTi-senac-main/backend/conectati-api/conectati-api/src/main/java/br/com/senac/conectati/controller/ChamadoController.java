package br.com.senac.conectati.controller;

import br.com.senac.conectati.dto.request.ChamadoRequest;
import br.com.senac.conectati.dto.request.RelatorioTecnicoRequest;
import br.com.senac.conectati.dto.request.StatusChamadoRequest;
import br.com.senac.conectati.dto.response.ChamadoResponse;
import br.com.senac.conectati.service.ChamadoService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/chamados")
@RequiredArgsConstructor
public class ChamadoController {


    private final ChamadoService service;



    @PostMapping
    public ResponseEntity<ChamadoResponse> salvar(
            @Valid @RequestBody ChamadoRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(request));

    }



    @GetMapping
    public ResponseEntity<List<ChamadoResponse>> listar(){

        return ResponseEntity.ok(service.listar());

    }



    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponse> buscarPorId(
            @PathVariable Long id){

        return ResponseEntity.ok(service.buscarPorId(id));

    }



    @PutMapping("/{id}")
    public ResponseEntity<ChamadoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ChamadoRequest request){

        return ResponseEntity.ok(
                service.atualizar(id, request)
        );

    }



    @PatchMapping("/{id}/assumir")
    public ResponseEntity<ChamadoResponse> assumir(
            @PathVariable Long id){

        return ResponseEntity.ok(service.assumir(id));

    }



    @PatchMapping("/{id}/status")
    public ResponseEntity<ChamadoResponse> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusChamadoRequest request){

        return ResponseEntity.ok(service.atualizarStatus(id, request));

    }



    @PostMapping("/{id}/relatorio-tecnico")
    public ResponseEntity<ChamadoResponse> registrarRelatorioTecnico(
            @PathVariable Long id,
            @Valid @RequestBody RelatorioTecnicoRequest request){

        return ResponseEntity.ok(service.registrarRelatorioTecnico(id, request));

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id){

        service.deletar(id);

        return ResponseEntity.noContent().build();

    }

}
