package br.com.senac.conectati.dto.request;

import br.com.senac.conectati.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para cadastro e atualização de usuários.
 */
@Getter
@Setter
public class UsuarioRequest {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 120, message = "O nome deve possuir no máximo 120 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 150, message = "O e-mail deve possuir no máximo 150 caracteres.")
    private String email;

    @Size(min = 6, max = 255, message = "A senha deve possuir entre 6 e 255 caracteres.")
    private String senha;

    @Size(max = 20, message = "O telefone deve possuir no máximo 20 caracteres.")
    private String telefone;

    @NotNull(message = "O tipo de usuário é obrigatório.")
    private TipoUsuario tipo;

    private Boolean ativo = true;

}
