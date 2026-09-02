package br.com.senac.conectati.dto.response;

import br.com.senac.conectati.enums.Prioridade;
import br.com.senac.conectati.enums.StatusChamado;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChamadoResponse {

    private Long id;
    private String titulo;
    private String descricao;
    private Prioridade prioridade;
    private StatusChamado status;
    private Long solicitanteId;
    private String solicitanteNome;
    private Long tecnicoResponsavelId;
    private String tecnicoResponsavelNome;
    private Long categoriaId;
    private String categoriaNome;
    private Long equipamentoId;
    private String equipamentoNome;
    private Long salaId;
    private String salaNome;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataFechamento;
    private String solucao;
    private String problemaIdentificado;
    private String diagnostico;
    private String procedimentosRealizados;
    private String observacoesRelatorio;
    private LocalDateTime dataAtendimento;
    private String justificativaCancelamento;
}
