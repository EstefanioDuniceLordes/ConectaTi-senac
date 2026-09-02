package br.com.senac.conectati.dto.response;

import br.com.senac.conectati.enums.StatusEquipamento;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para retornar os dados de um equipamento.
 */
@Getter
@Setter
public class EquipamentoResponse {

    private Long id;

    private String nome;

    private String patrimonio;

    private String tipo;

    private String fabricante;

    private String modelo;

    private StatusEquipamento status;

    private Long salaId;

    private String salaNome;

    private Long categoriaId;

    private String categoriaNome;

}