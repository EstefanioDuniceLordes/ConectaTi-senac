package br.com.senac.conectati.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Laboratório de informática. Agrupa várias salas.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "laboratorios")
public class Laboratorio extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 150)
    private String localizacao;

    @Column
    private Integer capacidade;

    @OneToMany(mappedBy = "laboratorio", fetch = FetchType.LAZY)
    private List<Sala> salas = new ArrayList<>();

}