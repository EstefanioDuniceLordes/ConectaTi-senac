package br.com.senac.conectati.model;

import br.com.senac.conectati.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um usuário do sistema.
 * Pode ser Administrador, Técnico ou Solicitante.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_usuario_email",
                        columnNames = "email"
                )
        }
)
public class Usuario extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoUsuario tipo;

    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToMany(mappedBy = "solicitante", fetch = FetchType.LAZY)
    private List<Chamado> chamadosAbertos = new ArrayList<>();

    @OneToMany(mappedBy = "tecnicoResponsavel", fetch = FetchType.LAZY)
    private List<Chamado> chamadosAtendidos = new ArrayList<>();

}