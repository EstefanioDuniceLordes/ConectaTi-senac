package br.com.senac.conectati.dto.response;

import br.com.senac.conectati.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MensagemChatResponse {

    private Long id;
    private Long chamadoId;
    private Long remetenteId;
    private String remetenteNome;
    private TipoUsuario remetenteTipo;
    private String mensagem;
    private LocalDateTime dataCriacao;
}
