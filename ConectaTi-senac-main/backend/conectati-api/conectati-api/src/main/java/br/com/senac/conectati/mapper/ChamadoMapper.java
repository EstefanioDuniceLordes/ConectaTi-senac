package br.com.senac.conectati.mapper;

import br.com.senac.conectati.dto.request.ChamadoRequest;
import br.com.senac.conectati.dto.response.ChamadoResponse;
import br.com.senac.conectati.model.Chamado;
import br.com.senac.conectati.model.Usuario;

public final class ChamadoMapper {
    private ChamadoMapper() { }

    public static Chamado toEntity(ChamadoRequest request) {
        Chamado chamado = new Chamado();
        updateEntity(chamado, request);
        return chamado;
    }

    public static void updateEntity(Chamado chamado, ChamadoRequest request) {
        chamado.setTitulo(request.getTitulo());
        chamado.setDescricao(request.getDescricao());
        chamado.setPrioridade(request.getPrioridade());
    }

    public static ChamadoResponse toResponse(Chamado chamado) {
        ChamadoResponse response = new ChamadoResponse();
        response.setId(chamado.getId());
        response.setTitulo(chamado.getTitulo());
        response.setDescricao(chamado.getDescricao());
        response.setPrioridade(chamado.getPrioridade());
        response.setStatus(chamado.getStatus());
        response.setSolicitanteId(chamado.getSolicitante().getId());
        response.setSolicitanteNome(chamado.getSolicitante().getNome());
        setTecnicoResponsavel(response, chamado.getTecnicoResponsavel());
        response.setCategoriaId(chamado.getCategoria().getId());
        response.setCategoriaNome(chamado.getCategoria().getNome());
        if (chamado.getEquipamento() != null) {
            response.setEquipamentoId(chamado.getEquipamento().getId());
            response.setEquipamentoNome(chamado.getEquipamento().getNome());
        }
        if (chamado.getSala() != null) {
            response.setSalaId(chamado.getSala().getId());
            response.setSalaNome(chamado.getSala().getNome());
        }
        response.setDataCriacao(chamado.getDataCriacao());
        response.setDataFechamento(chamado.getDataFechamento());
        response.setSolucao(chamado.getSolucao());
        response.setProblemaIdentificado(chamado.getProblemaIdentificado());
        response.setDiagnostico(chamado.getDiagnostico());
        response.setProcedimentosRealizados(chamado.getProcedimentosRealizados());
        response.setObservacoesRelatorio(chamado.getObservacoesRelatorio());
        response.setDataAtendimento(chamado.getDataAtendimento());
        response.setJustificativaCancelamento(chamado.getJustificativaCancelamento());
        return response;
    }

    private static void setTecnicoResponsavel(ChamadoResponse response, Usuario tecnico) {
        if (tecnico != null) {
            response.setTecnicoResponsavelId(tecnico.getId());
            response.setTecnicoResponsavelNome(tecnico.getNome());
        }
    }
}
