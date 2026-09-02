package br.com.senac.conectati.repository;

import br.com.senac.conectati.model.Laboratorio;
import br.com.senac.conectati.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

    Optional<Sala> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

    List<Sala> findByLaboratorio(Laboratorio laboratorio);

    List<Sala> findByNomeContainingIgnoreCase(String nome);

}