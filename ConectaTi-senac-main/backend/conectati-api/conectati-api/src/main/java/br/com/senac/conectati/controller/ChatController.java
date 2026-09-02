package br.com.senac.conectati.controller;

import br.com.senac.conectati.dto.request.MensagemChatRequest;
import br.com.senac.conectati.dto.response.MensagemChatResponse;
import br.com.senac.conectati.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat/chamados/{chamadoId}/mensagens")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService service;

    @GetMapping
    public ResponseEntity<List<MensagemChatResponse>> listar(@PathVariable Long chamadoId) {
        return ResponseEntity.ok(service.listarMensagens(chamadoId));
    }

    @PostMapping
    public ResponseEntity<MensagemChatResponse> enviar(
            @PathVariable Long chamadoId,
            @Valid @RequestBody MensagemChatRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.enviarMensagem(chamadoId, request));
    }
}
