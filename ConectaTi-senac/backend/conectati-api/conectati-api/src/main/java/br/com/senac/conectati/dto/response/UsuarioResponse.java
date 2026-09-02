package br.com.senac.conectati.dto.response;

import br.com.senac.conectati.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para retornar os dados de um usuário.
 */
@Getter
@Setter
public class UsuarioResponse {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private TipoUsuario tipo;

    private Boolean ativo;

}