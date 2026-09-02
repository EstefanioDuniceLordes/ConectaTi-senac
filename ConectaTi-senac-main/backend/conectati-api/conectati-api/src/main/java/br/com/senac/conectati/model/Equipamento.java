package br.com.senac.conectati.model;

import br.com.senac.conectati.enums.StatusEquipamento;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Equipamento de TI (computador, impressora, projetor, etc.)
 * alocado em uma sala.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "equipamentos",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_equipamento_patrimonio",
                        columnNames = "patrimonio"
                )
        }
)
public class Equipamento extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 50)
    private String patrimonio;

    @Column(length = 50)
    private String tipo;

    @Column(length = 100)
    private String fabricante;

    @Column(length = 100)
    private String modelo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusEquipamento status = StatusEquipamento.DISPONIVEL;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "equipamento", fetch = FetchType.LAZY)
    private List<Chamado> chamados = new ArrayList<>();

}
