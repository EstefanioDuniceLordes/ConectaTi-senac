package br.com.senac.conectati.repository;

import br.com.senac.conectati.enums.StatusChamado;
import br.com.senac.conectati.model.Chamado;
import br.com.senac.conectati.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findByStatus(StatusChamado status);

    List<Chamado> findBySolicitante(Usuario solicitante);

    List<Chamado> findByTecnicoResponsavel(Usuario tecnicoResponsavel);

    List<Chamado> findByCategoriaId(Long categoriaId);

    List<Chamado> findByEquipamentoId(Long equipamentoId);

    List<Chamado> findBySalaId(Long salaId);

    long countByStatus(StatusChamado status);

    long countBySolicitante(Usuario solicitante);

    long countBySolicitanteAndStatus(Usuario solicitante, StatusChamado status);

    long countByTecnicoResponsavel(Usuario tecnicoResponsavel);

    long countByTecnicoResponsavelAndStatus(Usuario tecnicoResponsavel, StatusChamado status);

}
