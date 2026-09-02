package br.com.senac.conectati.security;

import br.com.senac.conectati.enums.TipoUsuario;
import br.com.senac.conectati.model.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAutenticadoService {

    public Usuario atual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario usuario)) {
            throw new AccessDeniedException("Usuario nao autenticado.");
        }
        if (!Boolean.TRUE.equals(usuario.getAtivo())) {
            throw new AccessDeniedException("Usuario inativo.");
        }
        return usuario;
    }

    public boolean temCargo(TipoUsuario tipo) {
        return atual().getTipo() == tipo;
    }

    public void exigirCargo(TipoUsuario tipo) {
        if (!temCargo(tipo)) {
            throw new AccessDeniedException("Acesso negado para este cargo.");
        }
    }

    public void exigirUmDosCargos(TipoUsuario... tipos) {
        TipoUsuario atual = atual().getTipo();
        for (TipoUsuario tipo : tipos) {
            if (atual == tipo) {
                return;
            }
        }
        throw new AccessDeniedException("Acesso negado para este cargo.");
    }
}
