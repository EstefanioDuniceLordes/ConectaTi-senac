package br.com.senac.conectati.dto.request;

import br.com.senac.conectati.enums.StatusChamado;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusChamadoRequest {

    @NotNull(message = "O status e obrigatorio.")
    private StatusChamado status;

    private String solucao;

    private String justificativaCancelamento;
}
