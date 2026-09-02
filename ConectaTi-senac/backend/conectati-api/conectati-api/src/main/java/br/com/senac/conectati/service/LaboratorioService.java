package br.com.senac.conectati.service;

import br.com.senac.conectati.dto.request.LaboratorioRequest;
import br.com.senac.conectati.dto.response.LaboratorioResponse;
import br.com.senac.conectati.exception.BusinessException;
import br.com.senac.conectati.exception.ResourceNotFoundException;
import br.com.senac.conectati.mapper.LaboratorioMapper;
import br.com.senac.conectati.model.Laboratorio;
import br.com.senac.conectati.repository.LaboratorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LaboratorioService {
    private final LaboratorioRepository repository;
    @Transactional public LaboratorioResponse salvar(LaboratorioRequest request) { validarNomeDisponivel(request.getNome(), null); return LaboratorioMapper.toResponse(repository.save(LaboratorioMapper.toEntity(request))); }
    public List<LaboratorioResponse> listar() { return repository.findAll().stream().map(LaboratorioMapper::toResponse).toList(); }
    public LaboratorioResponse buscarPorId(Long id) { return LaboratorioMapper.toResponse(obter(id)); }
    @Transactional public LaboratorioResponse atualizar(Long id, LaboratorioRequest request) { Laboratorio laboratorio = obter(id); validarNomeDisponivel(request.getNome(), id); LaboratorioMapper.updateEntity(laboratorio, request); return LaboratorioMapper.toResponse(repository.save(laboratorio)); }
    @Transactional public void deletar(Long id) { repository.delete(obter(id)); }
    private Laboratorio obter(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Laboratório não encontrado.")); }
    private void validarNomeDisponivel(String nome, Long idAtual) { repository.findByNomeIgnoreCase(nome).filter(l -> !l.getId().equals(idAtual)).ifPresent(l -> { throw new BusinessException("Já existe um laboratório com este nome."); }); }
}
