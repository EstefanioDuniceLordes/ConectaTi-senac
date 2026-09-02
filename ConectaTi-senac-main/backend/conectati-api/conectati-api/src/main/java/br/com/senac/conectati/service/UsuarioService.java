package br.com.senac.conectati.service;

import br.com.senac.conectati.dto.request.UsuarioRequest;
import br.com.senac.conectati.dto.response.UsuarioResponse;
import br.com.senac.conectati.exception.BusinessException;
import br.com.senac.conectati.exception.ResourceNotFoundException;
import br.com.senac.conectati.mapper.UsuarioMapper;
import br.com.senac.conectati.model.Usuario;
import br.com.senac.conectati.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponse salvar(UsuarioRequest request) {
        if (request.getSenha() == null || request.getSenha().isBlank()) {
            throw new BusinessException("A senha e obrigatoria.");
        }
        validarCargo(request);
        validarEmailDisponivel(request.getEmail(), null);
        Usuario usuario = UsuarioMapper.toEntity(request);
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        return UsuarioMapper.toResponse(repository.save(usuario));
    }

    public List<UsuarioResponse> listar() {
        return repository.findAll().stream().map(UsuarioMapper::toResponse).toList();
    }

    public UsuarioResponse buscarPorId(Long id) {
        return UsuarioMapper.toResponse(obter(id));
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioRequest request) {
        Usuario usuario = obter(id);
        validarCargo(request);
        validarEmailDisponivel(request.getEmail(), id);
        UsuarioMapper.updateEntity(usuario, request);
        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        }
        return UsuarioMapper.toResponse(repository.save(usuario));
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = obter(id);
        usuario.setAtivo(false);
        repository.save(usuario);
    }

    private Usuario obter(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado."));
    }

    private void validarCargo(UsuarioRequest request) {
        if (request.getTipo() == null) {
            throw new BusinessException("Usuario deve possuir cargo valido.");
        }
        if (request.getAtivo() == null) {
            request.setAtivo(true);
        }
    }

    private void validarEmailDisponivel(String email, Long idAtual) {
        repository.findByEmail(email)
                .filter(u -> !u.getId().equals(idAtual))
                .ifPresent(u -> {
                    throw new BusinessException("Ja existe um usuario com este e-mail.");
                });
    }
}
