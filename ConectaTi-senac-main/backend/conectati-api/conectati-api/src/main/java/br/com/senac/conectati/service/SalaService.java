package br.com.senac.conectati.service;

import br.com.senac.conectati.dto.request.SalaRequest;
import br.com.senac.conectati.dto.response.SalaResponse;
import br.com.senac.conectati.exception.BusinessException;
import br.com.senac.conectati.exception.ResourceNotFoundException;
import br.com.senac.conectati.mapper.SalaMapper;
import br.com.senac.conectati.model.Laboratorio;
import br.com.senac.conectati.model.Sala;
import br.com.senac.conectati.repository.LaboratorioRepository;
import br.com.senac.conectati.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional(readOnly = true)
public class SalaService {
    private final SalaRepository repository;
    private final LaboratorioRepository laboratorioRepository;
    @Transactional public SalaResponse salvar(SalaRequest request) { validarNomeDisponivel(request.getNome(), null); Sala sala = SalaMapper.toEntity(request); sala.setLaboratorio(obterLaboratorio(request.getLaboratorioId())); return SalaMapper.toResponse(repository.save(sala)); }
    public List<SalaResponse> listar() { return repository.findAll().stream().map(SalaMapper::toResponse).toList(); }
    public SalaResponse buscarPorId(Long id) { return SalaMapper.toResponse(obter(id)); }
    @Transactional public SalaResponse atualizar(Long id, SalaRequest request) { Sala sala = obter(id); validarNomeDisponivel(request.getNome(), id); SalaMapper.updateEntity(sala, request); sala.setLaboratorio(obterLaboratorio(request.getLaboratorioId())); return SalaMapper.toResponse(repository.save(sala)); }
    @Transactional public void deletar(Long id) { repository.delete(obter(id)); }
    private Sala obter(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada.")); }
    private Laboratorio obterLaboratorio(Long id) { return laboratorioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Laboratório não encontrado.")); }
    private void validarNomeDisponivel(String nome, Long idAtual) { repository.findByNomeIgnoreCase(nome).filter(s -> !s.getId().equals(idAtual)).ifPresent(s -> { throw new BusinessException("Já existe uma sala com este nome."); }); }
}
