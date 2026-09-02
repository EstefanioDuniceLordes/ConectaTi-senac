package br.com.senac.conectati.service;

import br.com.senac.conectati.dto.request.CategoriaRequest;
import br.com.senac.conectati.dto.response.CategoriaResponse;
import br.com.senac.conectati.exception.BusinessException;
import br.com.senac.conectati.exception.ResourceNotFoundException;
import br.com.senac.conectati.mapper.CategoriaMapper;
import br.com.senac.conectati.model.Categoria;
import br.com.senac.conectati.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriaService {
    private final CategoriaRepository repository;

    @Transactional
    public CategoriaResponse salvar(CategoriaRequest request) {
        validarNomeDisponivel(request.getNome(), null);
        return CategoriaMapper.toResponse(repository.save(CategoriaMapper.toEntity(request)));
    }
    public List<CategoriaResponse> listar() { return repository.findAll().stream().map(CategoriaMapper::toResponse).toList(); }
    public CategoriaResponse buscarPorId(Long id) { return CategoriaMapper.toResponse(obter(id)); }
    @Transactional
    public CategoriaResponse atualizar(Long id, CategoriaRequest request) {
        Categoria categoria = obter(id);
        validarNomeDisponivel(request.getNome(), id);
        CategoriaMapper.updateEntity(categoria, request);
        return CategoriaMapper.toResponse(repository.save(categoria));
    }
    @Transactional
    public void deletar(Long id) { repository.delete(obter(id)); }
    private Categoria obter(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada.")); }
    private void validarNomeDisponivel(String nome, Long idAtual) {
        repository.findByNomeIgnoreCase(nome).filter(c -> !c.getId().equals(idAtual)).ifPresent(c -> { throw new BusinessException("Já existe uma categoria com este nome."); });
    }
}
