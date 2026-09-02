package br.com.senac.conectati.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemChatRequest {

    @NotBlank(message = "A mensagem e obrigatoria.")
    private String mensagem;
}
