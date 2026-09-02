package br.com.senac.conectati.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para retornar os dados de uma sala.
 */
@Getter
@Setter
public class SalaResponse {

    private Long id;

    private String nome;

    private Integer capacidade;

    private Long laboratorioId;

    private String laboratorioNome;

}