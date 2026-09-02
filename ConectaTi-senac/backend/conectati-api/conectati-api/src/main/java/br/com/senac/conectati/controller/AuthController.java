package br.com.senac.conectati.controller;

import br.com.senac.conectati.dto.request.LoginRequest;
import br.com.senac.conectati.dto.response.LoginResponse;
import br.com.senac.conectati.exception.BusinessException;
import br.com.senac.conectati.mapper.UsuarioMapper;
import br.com.senac.conectati.repository.UsuarioRepository;
import br.com.senac.conectati.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        var usuario = usuarios.findByEmail(request.getEmail())
                .filter(item -> Boolean.TRUE.equals(item.getAtivo()))
                .filter(item -> passwordEncoder.matches(request.getSenha(), item.getSenha()))
                .orElseThrow(() -> new BusinessException("E-mail ou senha invalidos."));

        return ResponseEntity.ok(new LoginResponse(
                UsuarioMapper.toResponse(usuario),
                tokenService.emitirToken(usuario),
                "Bearer"
        ));
    }
}
