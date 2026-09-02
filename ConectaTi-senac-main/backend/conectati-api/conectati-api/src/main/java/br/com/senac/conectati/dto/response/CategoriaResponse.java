package br.com.senac.conectati.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para retornar os dados de uma categoria.
 */
@Getter
@Setter
public class CategoriaResponse {

    private Long id;

    private String nome;

    private String descricao;

}