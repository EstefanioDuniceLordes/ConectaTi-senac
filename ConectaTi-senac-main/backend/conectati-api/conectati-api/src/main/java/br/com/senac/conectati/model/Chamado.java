package br.com.senac.conectati.model;

import br.com.senac.conectati.enums.Prioridade;
import br.com.senac.conectati.enums.StatusChamado;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Representa um chamado de suporte.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "chamados")
public class Chamado extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String titulo;

    @Lob
    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade = Prioridade.MEDIA;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status = StatusChamado.ABERTO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_responsavel_id")
    private Usuario tecnicoResponsavel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento_id")
    private Equipamento equipamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @Lob
    @Column(name = "solucao")
    private String solucao;

    @Lob
    @Column(name = "problema_identificado")
    private String problemaIdentificado;

    @Lob
    @Column(name = "diagnostico")
    private String diagnostico;

    @Lob
    @Column(name = "procedimentos_realizados")
    private String procedimentosRealizados;

    @Lob
    @Column(name = "observacoes_relatorio")
    private String observacoesRelatorio;

    @Column(name = "data_atendimento")
    private LocalDateTime dataAtendimento;

    @Lob
    @Column(name = "justificativa_cancelamento")
    private String justificativaCancelamento;

}
