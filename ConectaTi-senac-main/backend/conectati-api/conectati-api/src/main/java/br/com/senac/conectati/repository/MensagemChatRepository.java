package br.com.senac.conectati.repository;

import br.com.senac.conectati.model.MensagemChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemChatRepository extends JpaRepository<MensagemChat, Long> {

    List<MensagemChat> findByChamadoIdOrderByDataCriacaoAsc(Long chamadoId);
}
