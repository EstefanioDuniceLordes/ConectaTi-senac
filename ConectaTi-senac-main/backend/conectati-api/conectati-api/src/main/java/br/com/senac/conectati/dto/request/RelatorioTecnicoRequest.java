package br.com.senac.conectati.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioTecnicoRequest {

    @NotBlank(message = "O problema identificado e obrigatorio.")
    private String problemaIdentificado;

    @NotBlank(message = "O diagnostico e obrigatorio.")
    private String diagnostico;

    @NotBlank(message = "Os procedimentos realizados sao obrigatorios.")
    private String procedimentosRealizados;

    @NotBlank(message = "A solucao aplicada e obrigatoria.")
    private String solucaoAplicada;

    private String observacoes;
}
