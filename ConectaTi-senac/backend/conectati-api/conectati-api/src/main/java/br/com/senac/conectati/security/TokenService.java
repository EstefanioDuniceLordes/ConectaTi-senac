package br.com.senac.conectati.security;

import br.com.senac.conectati.model.Usuario;
import br.com.senac.conectati.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UsuarioRepository usuarioRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final Map<String, Long> tokens = new ConcurrentHashMap<>();

    public String emitirToken(Usuario usuario) {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        tokens.put(token, usuario.getId());
        return token;
    }

    public Optional<Authentication> autenticar(String token) {
        Long usuarioId = tokens.get(token);
        if (usuarioId == null) {
            return Optional.empty();
        }

        return usuarioRepository.findById(usuarioId)
                .filter(usuario -> Boolean.TRUE.equals(usuario.getAtivo()))
                .map(usuario -> new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getTipo().name()))
                ));
    }
}
