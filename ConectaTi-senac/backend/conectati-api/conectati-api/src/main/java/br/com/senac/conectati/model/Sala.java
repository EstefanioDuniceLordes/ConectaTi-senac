package br.com.senac.conectati.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Sala pertencente a um laboratório, onde ficam os equipamentos.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "salas")
public class Sala extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String nome;

    @Column
    private Integer capacidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "laboratorio_id", nullable = false)
    private Laboratorio laboratorio;

    @OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
    private List<Equipamento> equipamentos = new ArrayList<>();

    @OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
    private List<Chamado> chamados = new ArrayList<>();
}