package br.com.senac.conectati.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para cadastro e atualização de salas.
 */
@Getter
@Setter
public class SalaRequest {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 50, message = "O nome deve possuir no máximo 50 caracteres.")
    private String nome;

    @Positive(message = "A capacidade deve ser maior que zero.")
    private Integer capacidade;

    @NotNull(message = "O laboratório é obrigatório.")
    private Long laboratorioId;

}