package br.com.senac.conectati.mapper;

import br.com.senac.conectati.dto.response.MensagemChatResponse;
import br.com.senac.conectati.model.MensagemChat;

public final class MensagemChatMapper {
    private MensagemChatMapper() {
    }

    public static MensagemChatResponse toResponse(MensagemChat mensagem) {
        MensagemChatResponse response = new MensagemChatResponse();
        response.setId(mensagem.getId());
        response.setChamadoId(mensagem.getChamado().getId());
        response.setRemetenteId(mensagem.getRemetente().getId());
        response.setRemetenteNome(mensagem.getRemetente().getNome());
        response.setRemetenteTipo(mensagem.getRemetente().getTipo());
        response.setMensagem(mensagem.getMensagem());
        response.setDataCriacao(mensagem.getDataCriacao());
        return response;
    }
}
