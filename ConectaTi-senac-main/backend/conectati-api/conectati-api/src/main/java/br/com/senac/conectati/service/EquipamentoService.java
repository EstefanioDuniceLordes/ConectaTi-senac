package br.com.senac.conectati.service;

import br.com.senac.conectati.dto.request.EquipamentoRequest;
import br.com.senac.conectati.dto.response.EquipamentoResponse;
import br.com.senac.conectati.exception.BusinessException;
import br.com.senac.conectati.exception.ResourceNotFoundException;
import br.com.senac.conectati.mapper.EquipamentoMapper;
import br.com.senac.conectati.model.Categoria;
import br.com.senac.conectati.model.Equipamento;
import br.com.senac.conectati.model.Sala;
import br.com.senac.conectati.repository.CategoriaRepository;
import br.com.senac.conectati.repository.EquipamentoRepository;
import br.com.senac.conectati.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional(readOnly = true)
public class EquipamentoService {
    private final EquipamentoRepository repository;
    private final SalaRepository salaRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public EquipamentoResponse salvar(EquipamentoRequest request) {
        validarPatrimonioDisponivel(request.getPatrimonio(), null);
        Equipamento equipamento = EquipamentoMapper.toEntity(request);
        preencherRelacionamentos(equipamento, request);
        return EquipamentoMapper.toResponse(repository.save(equipamento));
    }
    public List<EquipamentoResponse> listar() { return repository.findAll().stream().map(EquipamentoMapper::toResponse).toList(); }
    public EquipamentoResponse buscarPorId(Long id) { return EquipamentoMapper.toResponse(obter(id)); }
    @Transactional
    public EquipamentoResponse atualizar(Long id, EquipamentoRequest request) {
        Equipamento equipamento = obter(id);
        validarPatrimonioDisponivel(request.getPatrimonio(), id);
        EquipamentoMapper.updateEntity(equipamento, request);
        preencherRelacionamentos(equipamento, request);
        return EquipamentoMapper.toResponse(repository.save(equipamento));
    }
    @Transactional public void deletar(Long id) { repository.delete(obter(id)); }
    private void preencherRelacionamentos(Equipamento equipamento, EquipamentoRequest request) { equipamento.setSala(obterSala(request.getSalaId())); equipamento.setCategoria(obterCategoria(request.getCategoriaId())); }
    private Equipamento obter(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipamento nao encontrado.")); }
    private Sala obterSala(Long id) { return salaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sala nao encontrada.")); }
    private Categoria obterCategoria(Long id) { return categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria nao encontrada.")); }
    private void validarPatrimonioDisponivel(String patrimonio, Long idAtual) { repository.findByPatrimonio(patrimonio).filter(e -> !e.getId().equals(idAtual)).ifPresent(e -> { throw new BusinessException("Ja existe um equipamento com este patrimonio."); }); }
}
