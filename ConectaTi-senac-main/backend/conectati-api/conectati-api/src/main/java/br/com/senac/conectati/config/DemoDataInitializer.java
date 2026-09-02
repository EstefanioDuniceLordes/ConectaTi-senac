package br.com.senac.conectati.config;

import br.com.senac.conectati.enums.TipoUsuario;
import br.com.senac.conectati.model.Categoria;
import br.com.senac.conectati.model.Equipamento;
import br.com.senac.conectati.model.Laboratorio;
import br.com.senac.conectati.model.Sala;
import br.com.senac.conectati.model.Usuario;
import br.com.senac.conectati.repository.CategoriaRepository;
import br.com.senac.conectati.repository.EquipamentoRepository;
import br.com.senac.conectati.repository.LaboratorioRepository;
import br.com.senac.conectati.repository.SalaRepository;
import br.com.senac.conectati.repository.UsuarioRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("!test")
public class DemoDataInitializer {

    @Bean
    ApplicationRunner seedDemoData(
            UsuarioRepository usuarios,
            CategoriaRepository categorias,
            LaboratorioRepository laboratorios,
            SalaRepository salas,
            EquipamentoRepository equipamentos,
            JdbcTemplate jdbcTemplate,
            PasswordEncoder passwordEncoder) {
        return args -> {
            migrarEnumsLegados(jdbcTemplate);

            criarUsuarioDemo(usuarios, passwordEncoder, "Administrador", "admin@conectati.local", "Admin@123", TipoUsuario.ADMINISTRADOR);
            criarUsuarioDemo(usuarios, passwordEncoder, "Instrutor de Demonstracao", "instrutor@conectati.local", "Instrutor@123", TipoUsuario.INSTRUTOR);
            criarUsuarioDemo(usuarios, passwordEncoder, "Tecnico de Demonstracao", "tecnico@conectati.local", "Tecnico@123", TipoUsuario.TECNICO);
            criarUsuarioDemo(usuarios, passwordEncoder, "Coordenador de Demonstracao", "coordenador@conectati.local", "Coordenador@123", TipoUsuario.COORDENADOR);

            Categoria categoria = criarCategoria(categorias, "Informatica", "Equipamentos e suporte de TI");
            criarCategoria(categorias, "Redes e Internet", "Conectividade, Wi-Fi e rede cabeada");
            criarCategoria(categorias, "Audiovisual", "Projetores, telas, som e video");
            criarCategoria(categorias, "Software", "Sistemas, licencas e aplicativos");
            criarCategoria(categorias, "Impressao", "Impressoras, toners e digitalizacao");
            criarCategoria(categorias, "Infraestrutura", "Energia, mobiliario e manutencao predial");
            criarCategoria(categorias, "Telefonia", "Telefones, ramais e comunicacao interna");
            criarCategoria(categorias, "Seguranca", "Cameras, controle de acesso e alarmes");
            criarCategoria(categorias, "Ar-condicionado", "Climatizacao e ventilacao dos ambientes");
            criarCategoria(categorias, "Eletrica", "Tomadas, iluminacao e quadro eletrico");
            criarCategoria(categorias, "Acessibilidade", "Recursos e equipamentos de acessibilidade");
            criarCategoria(categorias, "Outros", "Demandas que nao se encaixam nas demais categorias");

            Laboratorio informatica = criarLaboratorio(laboratorios, "Laboratorio de Informatica", "Bloco A", 30);
            Laboratorio redes = criarLaboratorio(laboratorios, "Laboratorio de Redes", "Bloco A", 24);
            Laboratorio multimidia = criarLaboratorio(laboratorios, "Laboratorio de Multimidia", "Bloco B", 28);
            Laboratorio manutencao = criarLaboratorio(laboratorios, "Laboratorio de Manutencao", "Bloco B", 20);

            Sala sala = criarSala(salas, "Laboratorio 01", 30, informatica);
            criarSala(salas, "Laboratorio 02", 30, informatica);
            criarSala(salas, "Laboratorio 03", 30, informatica);
            criarSala(salas, "Laboratorio de Redes 01", 24, redes);
            criarSala(salas, "Laboratorio de Redes 02", 24, redes);
            criarSala(salas, "Laboratorio de Multimidia", 28, multimidia);
            criarSala(salas, "Laboratorio de Manutencao", 20, manutencao);
            criarSala(salas, "Sala 101", 35, informatica);
            criarSala(salas, "Sala 102", 35, informatica);
            criarSala(salas, "Sala 201", 40, multimidia);
            criarSala(salas, "Sala 202", 40, multimidia);
            criarSala(salas, "Sala 103", 35, informatica);
            criarSala(salas, "Sala 104", 35, informatica);
            criarSala(salas, "Sala 105", 35, informatica);
            criarSala(salas, "Sala 203", 40, multimidia);
            criarSala(salas, "Sala 204", 40, multimidia);
            criarSala(salas, "Sala 205", 40, multimidia);
            criarSala(salas, "Auditorio Principal", 120, multimidia);
            criarSala(salas, "Biblioteca", 50, informatica);
            criarSala(salas, "Secretaria", 15, manutencao);
            criarSala(salas, "Sala dos Professores", 25, manutencao);

            if (!equipamentos.existsByPatrimonio("SENAC-001")) {
                Equipamento equipamento = new Equipamento();
                equipamento.setNome("Notebook de suporte");
                equipamento.setPatrimonio("SENAC-001");
                equipamento.setTipo("Notebook");
                equipamento.setFabricante("Dell");
                equipamento.setModelo("Latitude");
                equipamento.setSala(sala);
                equipamento.setCategoria(categoria);
                equipamentos.save(equipamento);
            }
        };
    }

    private void criarUsuarioDemo(UsuarioRepository usuarios, PasswordEncoder passwordEncoder, String nome, String email, String senha, TipoUsuario tipo) {
        if (!usuarios.existsByEmail(email)) {
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(passwordEncoder.encode(senha));
            usuario.setTipo(tipo);
            usuario.setAtivo(true);
            usuarios.save(usuario);
        }
    }

    private Categoria criarCategoria(CategoriaRepository categorias, String nome, String descricao) {
        return categorias.findByNomeIgnoreCase(nome).orElseGet(() -> {
            Categoria item = new Categoria();
            item.setNome(nome);
            item.setDescricao(descricao);
            return categorias.save(item);
        });
    }

    private Laboratorio criarLaboratorio(LaboratorioRepository laboratorios, String nome, String localizacao, int capacidade) {
        return laboratorios.findByNomeIgnoreCase(nome).orElseGet(() -> {
            Laboratorio item = new Laboratorio();
            item.setNome(nome);
            item.setLocalizacao(localizacao);
            item.setCapacidade(capacidade);
            return laboratorios.save(item);
        });
    }

    private Sala criarSala(SalaRepository salas, String nome, int capacidade, Laboratorio laboratorio) {
        return salas.findByNomeIgnoreCase(nome).orElseGet(() -> {
            Sala item = new Sala();
            item.setNome(nome);
            item.setCapacidade(capacidade);
            item.setLaboratorio(laboratorio);
            return salas.save(item);
        });
    }

    private void migrarEnumsLegados(JdbcTemplate jdbcTemplate) {
        try {
            jdbcTemplate.update("update usuarios set tipo = 'ADMINISTRADOR' where cast(tipo as varchar) = 'ADMIN'");
            jdbcTemplate.update("update usuarios set tipo = 'INSTRUTOR' where cast(tipo as varchar) in ('PROFESSOR', 'ALUNO')");
            jdbcTemplate.update("update chamados set status = 'CONCLUIDO' where cast(status as varchar) = 'FINALIZADO'");
        } catch (Exception ignored) {
            // Ignora se o banco nao contiver valores legados ou se a coluna for restrita por enum
        }
    }
}
