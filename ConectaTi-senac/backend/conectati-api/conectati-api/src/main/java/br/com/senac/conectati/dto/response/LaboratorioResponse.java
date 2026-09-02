package br.com.senac.conectati.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para retornar os dados de um laboratório.
 */
@Getter
@Setter
public class LaboratorioResponse {

    private Long id;

    private String nome;

    private String localizacao;

    private Integer capacidade;

}