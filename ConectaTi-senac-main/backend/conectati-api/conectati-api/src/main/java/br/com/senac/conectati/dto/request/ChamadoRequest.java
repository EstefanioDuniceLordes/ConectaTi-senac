package br.com.senac.conectati.dto.request;

import br.com.senac.conectati.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChamadoRequest {

    @NotBlank(message = "O titulo e obrigatorio.")
    @Size(max = 150, message = "O titulo deve possuir no maximo 150 caracteres.")
    private String titulo;

    @NotBlank(message = "A descricao e obrigatoria.")
    private String descricao;

    @NotNull(message = "A prioridade e obrigatoria.")
    private Prioridade prioridade;

    private Long solicitanteId;

    @NotNull(message = "A categoria e obrigatoria.")
    private Long categoriaId;

    private Long tecnicoResponsavelId;

    private Long equipamentoId;

    private Long salaId;
}
