package br.com.senac.conectati.repository;

import br.com.senac.conectati.model.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {

    Optional<Laboratorio> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

}
