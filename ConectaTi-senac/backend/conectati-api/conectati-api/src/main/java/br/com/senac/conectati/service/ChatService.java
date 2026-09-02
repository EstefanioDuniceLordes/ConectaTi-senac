package br.com.senac.conectati.service;

import br.com.senac.conectati.dto.request.MensagemChatRequest;
import br.com.senac.conectati.dto.response.MensagemChatResponse;
import br.com.senac.conectati.enums.TipoUsuario;
import br.com.senac.conectati.exception.BusinessException;
import br.com.senac.conectati.mapper.MensagemChatMapper;
import br.com.senac.conectati.model.Chamado;
import br.com.senac.conectati.model.MensagemChat;
import br.com.senac.conectati.model.Usuario;
import br.com.senac.conectati.repository.MensagemChatRepository;
import br.com.senac.conectati.security.UsuarioAutenticadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final MensagemChatRepository repository;
    private final ChamadoService chamadoService;
    private final UsuarioAutenticadoService autenticadoService;

    public List<MensagemChatResponse> listarMensagens(Long chamadoId) {
        Chamado chamado = chamadoService.obterComPermissao(chamadoId);
        exigirParticipanteDoChat(chamado, autenticadoService.atual());
        return repository.findByChamadoIdOrderByDataCriacaoAsc(chamadoId).stream()
                .map(MensagemChatMapper::toResponse)
                .toList();
    }

    @Transactional
    public MensagemChatResponse enviarMensagem(Long chamadoId, MensagemChatRequest request) {
        Chamado chamado = chamadoService.obterComPermissao(chamadoId);
        Usuario usuario = autenticadoService.atual();
        exigirParticipanteDoChat(chamado, usuario);
        if (chamado.getTecnicoResponsavel() == null) {
            throw new BusinessException("Chat tecnico disponivel somente apos atribuicao de tecnico.");
        }

        MensagemChat mensagem = new MensagemChat();
        mensagem.setChamado(chamado);
        mensagem.setRemetente(usuario);
        mensagem.setMensagem(request.getMensagem().trim());
        return MensagemChatMapper.toResponse(repository.save(mensagem));
    }

    private void exigirParticipanteDoChat(Chamado chamado, Usuario usuario) {
        boolean solicitante = chamado.getSolicitante().getId().equals(usuario.getId())
                && (usuario.getTipo() == TipoUsuario.INSTRUTOR || usuario.getTipo() == TipoUsuario.COORDENADOR);
        boolean tecnicoResponsavel = chamado.getTecnicoResponsavel() != null
                && chamado.getTecnicoResponsavel().getId().equals(usuario.getId())
                && usuario.getTipo() == TipoUsuario.TECNICO;

        if (!solicitante && !tecnicoResponsavel) {
            throw new AccessDeniedException("Usuario nao participa do chat deste chamado.");
        }
    }
}
