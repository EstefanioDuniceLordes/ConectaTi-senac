package br.com.senac.conectati.mapper;

import br.com.senac.conectati.dto.request.LaboratorioRequest;
import br.com.senac.conectati.dto.response.LaboratorioResponse;
import br.com.senac.conectati.model.Laboratorio;

public final class LaboratorioMapper {
    private LaboratorioMapper() { }

    public static Laboratorio toEntity(LaboratorioRequest request) {
        Laboratorio laboratorio = new Laboratorio();
        updateEntity(laboratorio, request);
        return laboratorio;
    }

    public static void updateEntity(Laboratorio laboratorio, LaboratorioRequest request) {
        laboratorio.setNome(request.getNome());
        laboratorio.setLocalizacao(request.getLocalizacao());
        laboratorio.setCapacidade(request.getCapacidade());
    }

    public static LaboratorioResponse toResponse(Laboratorio laboratorio) {
        LaboratorioResponse response = new LaboratorioResponse();
        response.setId(laboratorio.getId());
        response.setNome(laboratorio.getNome());
        response.setLocalizacao(laboratorio.getLocalizacao());
        response.setCapacidade(laboratorio.getCapacidade());
        return response;
    }
}
