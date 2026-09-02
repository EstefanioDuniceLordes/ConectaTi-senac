package br.com.senac.conectati.repository;

import br.com.senac.conectati.enums.StatusEquipamento;
import br.com.senac.conectati.model.Equipamento;
import br.com.senac.conectati.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    Optional<Equipamento> findByPatrimonio(String patrimonio);

    boolean existsByPatrimonio(String patrimonio);

    List<Equipamento> findByStatus(StatusEquipamento status);

    List<Equipamento> findBySala(Sala sala);

    List<Equipamento> findByNomeContainingIgnoreCase(String nome);

}