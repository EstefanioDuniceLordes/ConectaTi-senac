package br.com.senac.conectati.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private UsuarioResponse usuario;
    private String token;
    private String tipoToken;
}
