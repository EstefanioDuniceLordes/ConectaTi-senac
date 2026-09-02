package br.com.senac.conectati.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para cadastro e atualização de laboratórios.
 */
@Getter
@Setter
public class LaboratorioRequest {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve possuir no máximo 100 caracteres.")
    private String nome;

    @Size(max = 150, message = "A localização deve possuir no máximo 150 caracteres.")
    private String localizacao;

    @Positive(message = "A capacidade deve ser maior que zero.")
    private Integer capacidade;

}