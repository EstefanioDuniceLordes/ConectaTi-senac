package br.com.senac.conectati.mapper;

import br.com.senac.conectati.dto.request.CategoriaRequest;
import br.com.senac.conectati.dto.response.CategoriaResponse;
import br.com.senac.conectati.model.Categoria;

public final class CategoriaMapper {
    private CategoriaMapper() { }

    public static Categoria toEntity(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        updateEntity(categoria, request);
        return categoria;
    }

    public static void updateEntity(Categoria categoria, CategoriaRequest request) {
        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());
    }

    public static CategoriaResponse toResponse(Categoria categoria) {
        CategoriaResponse response = new CategoriaResponse();
        response.setId(categoria.getId());
        response.setNome(categoria.getNome());
        response.setDescricao(categoria.getDescricao());
        return response;
    }
}
