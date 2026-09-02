package br.com.senac.conectati.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para cadastro e atualização de equipamentos.
 */
@Getter
@Setter
public class EquipamentoRequest {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve possuir no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "O patrimônio é obrigatório.")
    @Size(max = 50, message = "O patrimônio deve possuir no máximo 50 caracteres.")
    private String patrimonio;

    @Size(max = 50, message = "O tipo deve possuir no máximo 50 caracteres.")
    private String tipo;

    @Size(max = 100, message = "O fabricante deve possuir no máximo 100 caracteres.")
    private String fabricante;

    @Size(max = 100, message = "O modelo deve possuir no máximo 100 caracteres.")
    private String modelo;

    @NotNull(message = "A sala é obrigatória.")
    private Long salaId;

    @NotNull(message = "A categoria e obrigatoria.")
    private Long categoriaId;

}
