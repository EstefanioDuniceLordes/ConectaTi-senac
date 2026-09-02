package br.com.senac.conectati.mapper;

import br.com.senac.conectati.dto.request.EquipamentoRequest;
import br.com.senac.conectati.dto.response.EquipamentoResponse;
import br.com.senac.conectati.model.Equipamento;

public final class EquipamentoMapper {
    private EquipamentoMapper() { }

    public static Equipamento toEntity(EquipamentoRequest request) {
        Equipamento equipamento = new Equipamento();
        updateEntity(equipamento, request);
        return equipamento;
    }

    public static void updateEntity(Equipamento equipamento, EquipamentoRequest request) {
        equipamento.setNome(request.getNome());
        equipamento.setPatrimonio(request.getPatrimonio());
        equipamento.setTipo(request.getTipo());
        equipamento.setFabricante(request.getFabricante());
        equipamento.setModelo(request.getModelo());
    }

    public static EquipamentoResponse toResponse(Equipamento equipamento) {
        EquipamentoResponse response = new EquipamentoResponse();
        response.setId(equipamento.getId());
        response.setNome(equipamento.getNome());
        response.setPatrimonio(equipamento.getPatrimonio());
        response.setTipo(equipamento.getTipo());
        response.setFabricante(equipamento.getFabricante());
        response.setModelo(equipamento.getModelo());
        response.setStatus(equipamento.getStatus());
        response.setSalaId(equipamento.getSala().getId());
        response.setSalaNome(equipamento.getSala().getNome());
        response.setCategoriaId(equipamento.getCategoria().getId());
        response.setCategoriaNome(equipamento.getCategoria().getNome());
        return response;
    }
}
