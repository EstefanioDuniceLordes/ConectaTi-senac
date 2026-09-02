package br.com.senac.conectati.mapper;

import br.com.senac.conectati.dto.request.UsuarioRequest;
import br.com.senac.conectati.dto.response.UsuarioResponse;
import br.com.senac.conectati.model.Usuario;

public final class UsuarioMapper {
    private UsuarioMapper() { }

    public static Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        updateEntity(usuario, request);
        return usuario;
    }

    public static void updateEntity(Usuario usuario, UsuarioRequest request) {
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setTelefone(request.getTelefone());
        usuario.setTipo(request.getTipo());
        usuario.setAtivo(request.getAtivo());
    }

    public static UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setTelefone(usuario.getTelefone());
        response.setTipo(usuario.getTipo());
        response.setAtivo(usuario.getAtivo());
        return response;
    }
}
