package br.com.senac.conectati.mapper;

import br.com.senac.conectati.dto.request.SalaRequest;
import br.com.senac.conectati.dto.response.SalaResponse;
import br.com.senac.conectati.model.Sala;

public final class SalaMapper {
    private SalaMapper() { }

    public static Sala toEntity(SalaRequest request) {
        Sala sala = new Sala();
        updateEntity(sala, request);
        return sala;
    }

    public static void updateEntity(Sala sala, SalaRequest request) {
        sala.setNome(request.getNome());
        sala.setCapacidade(request.getCapacidade());
    }

    public static SalaResponse toResponse(Sala sala) {
        SalaResponse response = new SalaResponse();
        response.setId(sala.getId());
        response.setNome(sala.getNome());
        response.setCapacidade(sala.getCapacidade());
        response.setLaboratorioId(sala.getLaboratorio().getId());
        response.setLaboratorioNome(sala.getLaboratorio().getNome());
        return response;
    }
}
