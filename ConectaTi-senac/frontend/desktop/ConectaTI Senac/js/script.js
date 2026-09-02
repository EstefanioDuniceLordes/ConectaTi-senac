// ============================================================
// CONECTATI SENAC - SCRIPT COMPLETO
// NOVAS PERMISSÕES
// ============================================================

// ========================
// CONTROLE DE TEMA
// ========================

function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-theme');
    const newTheme = currentTheme === 'light' ? 'dark' : 'light';
    document.documentElement.setAttribute('data-theme', newTheme);
    localStorage.setItem('theme', newTheme);
    mostrarNotificacao(`Tema ${newTheme === 'light' ? 'Claro' : 'Escuro'} ativado!`, 'info');
}

function loadTheme() {
    const savedTheme = localStorage.getItem('theme') || 'dark';
    document.documentElement.setAttribute('data-theme', savedTheme);
}

// ========================
// CONTROLE DE PERFIL E PERMISSÕES - NOVO
// ========================

const permissoes = {
    'Instrutor': {
        dashboard: true,
        novoChamado: true,
        chat: true,
        acompanharChamado: true,
        duvidasFrequentes: true,
        chamados: false,
        equipamentos: false,
        relatorios: false,
        usuarios: false,
        unidades: false,
        categorias: false,
        auditoria: false,
        gerenciarChamado: false,
        editarChamado: false
    },
    'Coordenacao': {
        dashboard: true,
        novoChamado: true,
        chat: true,
        acompanharChamado: true,
        duvidas: true,
        equipamentos: true,
        relatorios: true,
        chamados: false,
        usuarios: false,
        unidades: false,
        categorias: false,
        auditoria: false,
        gerenciarChamado: false,
        editarChamado: false
    },
    'Suporte TI': {
        dashboard: true,
        acompanharChamado: true,
        relatorio: true,
        gerenciarChamado: true,
        editarChamado: true,
        duvidasFrequentes: true,
        chat: true,
        chamados: false,
        novoChamado: false,
        equipamentos: false,
        usuarios: false,
        unidades: false,
        categorias: false,
        auditoria: false
    },
    'Administrador': {
        dashboard: true,
        criarUsuario: true,
        criarUnidade: true,
        gerenciarChamado: true,
        relatorios: true,
        equipamentos: true,
        categorias: true,
        auditoria: true,
        chamados: false,
        novoChamado: false,
        chat: false,
        acompanharChamado: false,
        duvidasFrequentes: false,
        usuarios: false,
        unidades: false
    }
};

function getPerfilUsuario() {
    const tipo = JSON.parse(localStorage.getItem('sessaoConectaTI') || 'null')?.usuario?.tipo;
    const perfis = {
        ADMINISTRADOR: 'Administrador',
        ADMIN: 'Administrador',
        TECNICO: 'Suporte TI',
        INSTRUTOR: 'Instrutor',
        PROFESSOR: 'Instrutor',
        COORDENADOR: 'Coordenacao',
        ALUNO: 'Coordenacao'
    };
    return perfis[tipo] || 'Visitante';
}

function getUnidadeUsuario() {
    return localStorage.getItem('unidadeUsuario') || '';
}

function getEmailUsuario() {
    return JSON.parse(localStorage.getItem('sessaoConectaTI') || 'null')?.usuario?.email || '';
}

function getPaginaPadrao(perfil) {
    const permissoesPerfil = permissoes[perfil];
    if (!permissoesPerfil) return 'login.html';
    
    if (permissoesPerfil.dashboard) return 'dashboard.html';
    if (permissoesPerfil.chamados) return 'chamados.html';
    if (permissoesPerfil.equipamentos) return 'equipamentos.html';
    if (permissoesPerfil.relatorios) return 'relatorios.html';
    if (permissoesPerfil.novoChamado) return 'novo-chamado.html';
    if (permissoesPerfil.acompanharChamado) return 'acompanhar-chamado.html';
    if (permissoesPerfil.chat) return 'chat.html';
    if (permissoesPerfil.duvidasFrequentes) return 'duvidas-frequentes.html';
    if (permissoesPerfil.gerenciarChamado) return 'gerenciar-chamado.html';
    if (permissoesPerfil.criarUsuario) return 'criar-usuario.html';
    if (permissoesPerfil.criarUnidade) return 'criar-unidade.html';
    
    return 'dashboard.html';
}

function verificarPermissoes() {
    const perfil = getPerfilUsuario();
    const permissoesPerfil = permissoes[perfil];
    
    if (!permissoesPerfil || perfil === 'Visitante') {
        window.location.href = 'login.html';
        return;
    }

    const pagina = window.location.pathname.split('/').pop();
    const mapeamento = {
        'dashboard.html': 'dashboard',
        'chamados.html': 'chamados',
        'novo-chamado.html': 'novoChamado',
        'equipamentos.html': 'equipamentos',
        'relatorios.html': 'relatorios',
        'usuarios.html': 'usuarios',
        'unidades.html': 'unidades',
        'categorias.html': 'categorias',
        'auditoria.html': 'auditoria',
        'chat.html': 'chat',
        'acompanhar-chamado.html': 'acompanharChamado',
        'duvidas-frequentes.html': 'duvidasFrequentes',
        'gerenciar-chamado.html': 'gerenciarChamado',
        'criar-usuario.html': 'criarUsuario',
        'criar-unidade.html': 'criarUnidade'
    };

    const chave = mapeamento[pagina] || '';
    
    if (chave && !permissoesPerfil[chave]) {
        const paginaPermitida = getPaginaPadrao(perfil);
        if (paginaPermitida) {
            mostrarNotificacao(`Acesso negado! Redirecionando...`, 'error');
            setTimeout(() => {
                window.location.href = paginaPermitida;
            }, 500);
        } else {
            window.location.href = 'login.html';
        }
    }

    atualizarSidebar(permissoesPerfil);
}

function atualizarSidebar(permissoesPerfil) {
    const links = document.querySelectorAll('.sidebar-nav a');
    const menuMap = {
        'dashboard.html': 'dashboard',
        'chamados.html': 'chamados',
        'novo-chamado.html': 'novoChamado',
        'equipamentos.html': 'equipamentos',
        'relatorios.html': 'relatorios',
        'usuarios.html': 'usuarios',
        'unidades.html': 'unidades',
        'categorias.html': 'categorias',
        'auditoria.html': 'auditoria',
        'chat.html': 'chat',
        'acompanhar-chamado.html': 'acompanharChamado',
        'duvidas-frequentes.html': 'duvidasFrequentes',
        'gerenciar-chamado.html': 'gerenciarChamado',
        'criar-usuario.html': 'criarUsuario',
        'criar-unidade.html': 'criarUnidade'
    };

    links.forEach(link => {
        const href = link.getAttribute('href');
        const chave = menuMap[href];
        if (chave && permissoesPerfil[chave] === false) {
            link.style.display = 'none';
        } else {
            link.style.display = 'flex';
        }
    });
}

// ========================
// FUNÇÕES DE NAVEGAÇÃO
// ========================

function irPara(pagina) {
    window.location.href = pagina;
}

function abrirChat() {
    window.location.href = 'chat.html';
}

function abrirDuvidas() {
    window.location.href = 'duvidas-frequentes.html';
}

function abrirAcompanharChamado() {
    window.location.href = 'acompanhar-chamado.html';
}

function abrirGerenciarChamado() {
    window.location.href = 'gerenciar-chamado.html';
}

function abrirCriarUsuario() {
    window.location.href = 'criar-usuario.html';
}

function abrirCriarUnidade() {
    window.location.href = 'criar-unidade.html';
}

// ========================
// DADOS SIMULADOS
// ========================

const chamados = [
    {
        id: 1,
        solicitante: 'Rayssa Paiva',
        perfil: 'Instrutor',
        categoria: 'Reserva de notebooks',
        sala: 'Lab 02',
        prioridade: 'Alta',
        status: 'Em atendimento',
        data: '03/07/2026',
        descricao: 'Preciso reservar 15 notebooks para a turma de TADS'
    },
    {
        id: 2,
        solicitante: 'Carla Mendes',
        perfil: 'Coordenacao',
        categoria: 'Projetor',
        sala: 'Sala 12',
        prioridade: 'Média',
        status: 'Aberto',
        data: '03/07/2026',
        descricao: 'Projetor da sala 12 está com a imagem desfocada'
    },
    {
        id: 3,
        solicitante: 'Joao Suporte',
        perfil: 'Suporte TI',
        categoria: 'Internet/Rede',
        sala: 'Lab 01',
        prioridade: 'Urgente',
        status: 'Resolvido',
        data: '02/07/2026',
        descricao: 'Internet do Lab 01 caiu - já restaurada'
    },
    {
        id: 4,
        solicitante: 'Ana Souza',
        perfil: 'Instrutor',
        categoria: 'Computador com defeito',
        sala: 'Lab 03',
        prioridade: 'Alta',
        status: 'Aberto',
        data: '03/07/2026',
        descricao: '3 computadores do Lab 03 não estão ligando'
    },
    {
        id: 5,
        solicitante: 'Pedro Santos',
        perfil: 'Coordenacao',
        categoria: 'Impressora',
        sala: 'Secretaria',
        prioridade: 'Média',
        status: 'Atrasado',
        data: '01/07/2026',
        descricao: 'Impressora da secretaria sem papel e com erro'
    }
];

const equipamentos = [
    {
        nome: 'Notebook Dell 01',
        tipo: 'Notebook',
        patrimonio: '2024001',
        status: 'Disponível',
        localizacao: 'Sala TI'
    },
    {
        nome: 'Notebook Dell 02',
        tipo: 'Notebook',
        patrimonio: '2024002',
        status: 'Reservado',
        localizacao: 'Lab 02'
    },
    {
        nome: 'Projetor Epson 01',
        tipo: 'Projetor',
        patrimonio: '2024010',
        status: 'Em manutenção',
        localizacao: 'Sala TI'
    },
    {
        nome: 'Cabo HDMI 01',
        tipo: 'Cabo',
        patrimonio: '2024020',
        status: 'Disponível',
        localizacao: 'Sala TI'
    },
    {
        nome: 'Impressora HP 01',
        tipo: 'Impressora',
        patrimonio: '2024030',
        status: 'Em uso',
        localizacao: 'Secretaria'
    }
];

// Dúvidas Frequentes
const duvidasFrequentes = [
    {
        id: 1,
        pergunta: 'Como reservar notebooks para minha turma?',
        resposta: 'Acesse o menu "Novo Chamado", selecione "Reserva de notebooks" e informe a quantidade e data.',
        categoria: 'Reservas'
    },
    {
        id: 2,
        pergunta: 'O que fazer se o projetor não funcionar?',
        resposta: 'Verifique os cabos e se o projetor está ligado. Se não resolver, abra um chamado na categoria "Projetor".',
        categoria: 'Equipamentos'
    },
    {
        id: 3,
        pergunta: 'Como acompanhar meu chamado?',
        resposta: 'Acesse o menu "Acompanhar Chamado" e veja o status da sua solicitação.',
        categoria: 'Chamados'
    },
    {
        id: 4,
        pergunta: 'Qual o prazo para atendimento de um chamado?',
        resposta: 'Chamados urgentes são atendidos em até 2 horas, os demais em até 24 horas úteis.',
        categoria: 'Chamados'
    }
];

// ========================
// FUNÇÕES DE STATUS
// ========================

function getBadgeStatus(status) {
    const mapa = {
        'aberto': 'badge-aberto',
        'em atendimento': 'badge-atendimento',
        'resolvido': 'badge-resolvido',
        'atrasado': 'badge-atrasado',
        'cancelado': 'badge-cancelado',
        'disponível': 'badge-disponivel',
        'reservado': 'badge-reservado',
        'em uso': 'badge-em-uso',
        'em manutenção': 'badge-em-manutencao'
    };
    const classe = mapa[status.toLowerCase()] || 'badge-aberto';
    return `<span class="badge ${classe}">${status}</span>`;
}

// ========================
// EXIBIR CHAMADOS
// ========================

function exibirChamados(filtroStatus = 'todos', filtroPrioridade = 'todos') {
    const tbody = document.getElementById('listaChamados');
    if (!tbody) return;

    let filtrados = chamados;

    if (filtroStatus !== 'todos') {
        filtrados = filtrados.filter(c => 
            c.status.toLowerCase().replace(' ', '-') === filtroStatus ||
            c.status.toLowerCase() === filtroStatus
        );
    }

    if (filtroPrioridade !== 'todos') {
        filtrados = filtrados.filter(c => 
            c.prioridade.toLowerCase() === filtroPrioridade
        );
    }

    tbody.innerHTML = filtrados.map(c => `
        <tr>
            <td><strong>#${c.id.toString().padStart(3, '0')}</strong></td>
            <td><strong>${c.solicitante}</strong></td>
            <td>${c.perfil}</td>
            <td>${c.categoria}</td>
            <td>${c.prioridade}</td>
            <td>${getBadgeStatus(c.status)}</td>
            <td>${c.data}</td>
        </tr>
    `).join('');

    const contador = document.getElementById('resultadoContador');
    if (contador) {
        contador.textContent = `Mostrando ${filtrados.length} chamado${filtrados.length > 1 ? 's' : ''}`;
    }
}

// ========================
// EXIBIR EQUIPAMENTOS
// ========================

function exibirEquipamentos() {
    const tbody = document.getElementById('listaEquipamentos');
    if (!tbody) return;

    tbody.innerHTML = equipamentos.map(e => `
        <tr>
            <td><strong>${e.nome}</strong></td>
            <td>${e.tipo}</td>
            <td>${e.patrimonio}</td>
            <td>${getBadgeStatus(e.status)}</td>
            <td>${e.localizacao}</td>
        </tr>
    `).join('');
}

// ========================
// EXIBIR DÚVIDAS FREQUENTES
// ========================

function exibirDuvidas() {
    const container = document.getElementById('listaDuvidas');
    if (!container) return;

    const busca = document.getElementById('buscaDuvidas')?.value.toLowerCase() || '';

    let filtradas = duvidasFrequentes.filter(d => 
        d.pergunta.toLowerCase().includes(busca) || 
        d.categoria.toLowerCase().includes(busca)
    );

    container.innerHTML = filtradas.map(d => `
        <div class="duvida-card" onclick="toggleDuvida(${d.id})">
            <div class="duvida-pergunta">
                <span class="duvida-icon">❓</span>
                <strong>${d.pergunta}</strong>
                <span class="duvida-toggle">▼</span>
            </div>
            <div class="duvida-resposta" id="resposta-${d.id}" style="display:none;">
                <p>${d.resposta}</p>
                <span class="duvida-categoria">${d.categoria}</span>
            </div>
        </div>
    `).join('');
}

function toggleDuvida(id) {
    const resposta = document.getElementById(`resposta-${id}`);
    if (resposta) {
        const isVisible = resposta.style.display === 'block';
        resposta.style.display = isVisible ? 'none' : 'block';
    }
}

// ========================
// FILTRAR CHAMADOS
// ========================

function filtrarChamados() {
    const status = document.getElementById('filtroStatus')?.value || 'todos';
    const prioridade = document.getElementById('filtroPrioridade')?.value || 'todos';
    exibirChamados(status, prioridade);
}

// ========================
// BUSCAR CHAMADOS
// ========================

function buscarChamados() {
    const termo = document.getElementById('buscaChamados')?.value.toLowerCase().trim() || '';
    const linhas = document.querySelectorAll('#listaChamados tr');
    let visiveis = 0;

    linhas.forEach(linha => {
        const texto = linha.textContent.toLowerCase();
        const match = texto.includes(termo);
        linha.style.display = match ? '' : 'none';
        if (match) visiveis++;
    });

    const contador = document.getElementById('resultadoContador');
    if (contador) {
        const total = linhas.length;
        if (termo.length > 0) {
            contador.textContent = `Encontrado${visiveis > 1 ? 's' : ''} ${visiveis} resultado${visiveis > 1 ? 's' : ''} para "${termo}"`;
        } else {
            contador.textContent = `Mostrando ${total} chamado${total > 1 ? 's' : ''}`;
        }
    }
}

// ========================
// ENVIAR CHAMADO
// ========================

function enviarChamado() {
    const solicitante = document.getElementById('solicitante')?.value;
    const perfil = document.getElementById('perfilChamado')?.value;
    const turma = document.getElementById('turma')?.value || '';
    const sala = document.getElementById('sala')?.value;
    const horario = document.getElementById('horario')?.value;
    const categoria = document.getElementById('categoria')?.value;
    const prioridade = document.getElementById('prioridade')?.value;
    const descricao = document.getElementById('descricao')?.value;

    if (!solicitante || !sala || !horario || !descricao) {
        mostrarNotificacao('Preencha todos os campos obrigatórios!', 'error');
        return false;
    }

    let turmaInfo = '';
    if (perfil === 'Instrutor' && turma) {
        turmaInfo = ` (Turma: ${turma})`;
    }

    const novoChamado = {
        id: chamados.length + 1,
        solicitante: solicitante + turmaInfo,
        perfil: perfil,
        categoria: categoria,
        sala: sala,
        horario: horario,
        prioridade: prioridade,
        status: 'Aberto',
        data: new Date().toLocaleDateString('pt-BR'),
        descricao: descricao
    };

    chamados.push(novoChamado);
    salvarChamadosLocalStorage();
    lancarConfetes();
    mostrarNotificacao(`Chamado #${novoChamado.id.toString().padStart(3, '0')} enviado com sucesso!`, 'success');

    document.getElementById('formChamado')?.reset();
    return false;
}

// ========================
// DASHBOARD
// ========================

function atualizarDashboard() {
    const abertos = chamados.filter(c => c.status === 'Aberto').length;
    const atendimento = chamados.filter(c => c.status === 'Em atendimento').length;
    const resolvidos = chamados.filter(c => c.status === 'Resolvido').length;
    const atrasados = chamados.filter(c => c.status === 'Atrasado').length;

    const elementos = {
        'abertos': abertos,
        'atendimento': atendimento,
        'resolvidos': resolvidos,
        'atrasados': atrasados
    };

    Object.keys(elementos).forEach(id => {
        const el = document.getElementById(id);
        if (el) {
            el.textContent = elementos[id];
        }
    });

    const notebooksEl = document.getElementById('notebooksDisponiveis');
    if (notebooksEl) notebooksEl.textContent = '18';
    
    const manutencaoEl = document.getElementById('emManutencao');
    if (manutencaoEl) manutencaoEl.textContent = '4';
}

// ========================
// GRÁFICOS DINÂMICOS
// ========================

function gerarGraficoStatus() {
    const container = document.getElementById('graficoStatus');
    if (!container) return;

    const statusCount = {
        'Resolvidos': chamados.filter(c => c.status === 'Resolvido').length,
        'Abertos': chamados.filter(c => c.status === 'Aberto').length,
        'Em atendimento': chamados.filter(c => c.status === 'Em atendimento').length,
        'Atrasados': chamados.filter(c => c.status === 'Atrasado').length
    };

    const cores = {
        'Resolvidos': 'fill-success',
        'Abertos': 'fill-primary',
        'Em atendimento': 'fill-warning',
        'Atrasados': 'fill-danger'
    };

    const max = Math.max(...Object.values(statusCount), 1);

    container.innerHTML = Object.entries(statusCount).map(([status, total]) => `
        <div class="barra-item">
            <span class="label">${status}</span>
            <div class="track">
                <div class="fill ${cores[status] || 'fill-primary'}" style="width: ${(total / max) * 100}%; min-width: 24px;">
                    ${total}
                </div>
            </div>
        </div>
    `).join('');
}

function gerarGraficoCategoria() {
    const container = document.getElementById('graficoCategoria');
    if (!container) return;

    const categorias = {};
    chamados.forEach(c => {
        categorias[c.categoria] = (categorias[c.categoria] || 0) + 1;
    });

    const sorted = Object.entries(categorias).sort((a, b) => b[1] - a[1]);
    const max = Math.max(...Object.values(categorias), 1);

    const cores = ['fill-primary', 'fill-success', 'fill-warning', 'fill-info', 'fill-danger', 'fill-secondary', 'fill-accent'];

    container.innerHTML = sorted.map(([cat, total], index) => `
        <div class="barra-item">
            <span class="label">${cat}</span>
            <div class="track">
                <div class="fill ${cores[index % cores.length]}" style="width: ${(total / max) * 100}%; min-width: 24px;">
                    ${total}
                </div>
            </div>
        </div>
    `).join('');
}

function gerarGraficoTendencia() {
    const container = document.getElementById('graficoTendencia');
    if (!container) return;

    const meses = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul'];
    const dados = [12, 18, 24, 20, 30, 25, chamados.length];
    const max = Math.max(...dados, 1);

    container.innerHTML = dados.map((valor, i) => `
        <div class="ponto">
            <div class="bar fill-primary" style="height: ${(valor / max) * 100 + 10}px;">
                <span class="valor">${valor}</span>
            </div>
            <span class="label">${meses[i]}</span>
        </div>
    `).join('');
}

function gerarGraficoSala() {
    const container = document.getElementById('graficoSala');
    if (!container) return;

    const salas = {};
    chamados.forEach(c => {
        salas[c.sala] = (salas[c.sala] || 0) + 1;
    });

    const sorted = Object.entries(salas).sort((a, b) => b[1] - a[1]);
    const max = Math.max(...Object.values(salas), 1);

    container.innerHTML = sorted.map(([sala, total]) => `
        <div class="barra-item">
            <span class="label">${sala}</span>
            <div class="track">
                <div class="fill fill-info" style="width: ${(total / max) * 100}%; min-width: 24px;">
                    ${total}
                </div>
            </div>
        </div>
    `).join('');
}

function gerarGraficoPrioridade() {
    const container = document.getElementById('graficoPrioridade');
    if (!container) return;

    const prioridades = { 'Baixa': 0, 'Média': 0, 'Alta': 0, 'Urgente': 0 };
    chamados.forEach(c => {
        if (prioridades[c.prioridade] !== undefined) {
            prioridades[c.prioridade]++;
        }
    });

    const cores = {
        'Baixa': 'fill-success',
        'Média': 'fill-warning',
        'Alta': 'fill-danger',
        'Urgente': 'fill-danger'
    };

    const max = Math.max(...Object.values(prioridades), 1);

    container.innerHTML = Object.entries(prioridades).map(([prio, total]) => `
        <div class="barra-item">
            <span class="label">${prio}</span>
            <div class="track">
                <div class="fill ${cores[prio] || 'fill-primary'}" style="width: ${(total / max) * 100}%; min-width: 24px;">
                    ${total}
                </div>
            </div>
        </div>
    `).join('');
}

function gerarGraficoEquipamentos() {
    const container = document.getElementById('graficoEquipamentos');
    if (!container) return;

    const dados = {
        'Notebook': 8,
        'Projetor': 5,
        'Impressora': 3,
        'Computador': 4,
        'Cabo HDMI': 2
    };

    const max = Math.max(...Object.values(dados), 1);

    container.innerHTML = Object.entries(dados).map(([nome, total]) => `
        <div class="barra-item">
            <span class="label">${nome}</span>
            <div class="track">
                <div class="fill fill-secondary" style="width: ${(total / max) * 100}%; min-width: 24px;">
                    ${total}
                </div>
            </div>
        </div>
    `).join('');
}

function gerarRelatorioMensal() {
    const container = document.getElementById('relatorioMensal');
    if (!container) return;

    const meses = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul'];
    const dados = [12, 18, 24, 20, 30, 25, chamados.length];
    const max = Math.max(...dados, 1);

    container.innerHTML = dados.map((valor, i) => `
        <div class="ponto">
            <div class="bar fill-primary" style="height: ${(valor / max) * 100 + 10}px;">
                <span class="valor">${valor}</span>
            </div>
            <span class="label">${meses[i]}</span>
        </div>
    `).join('');
}

function gerarRelatorioCategoria() {
    const container = document.getElementById('relatorioCategoria');
    if (!container) return;

    const categorias = {};
    chamados.forEach(c => {
        categorias[c.categoria] = (categorias[c.categoria] || 0) + 1;
    });

    const sorted = Object.entries(categorias).sort((a, b) => b[1] - a[1]);
    const max = Math.max(...Object.values(categorias), 1);

    container.innerHTML = sorted.map(([cat, total]) => `
        <div class="barra-item">
            <span class="label">${cat}</span>
            <div class="track">
                <div class="fill fill-success" style="width: ${(total / max) * 100}%; min-width: 24px;">
                    ${total}
                </div>
            </div>
        </div>
    `).join('');
}

function carregarGraficos() {
    setTimeout(() => {
        gerarGraficoStatus();
        gerarGraficoCategoria();
        gerarGraficoTendencia();
        gerarGraficoSala();
        gerarGraficoPrioridade();
        gerarGraficoEquipamentos();
        gerarRelatorioMensal();
        gerarRelatorioCategoria();
    }, 300);
}

// ========================
// RELATÓRIOS
// ========================

function gerarRelatorio() {
    mostrarNotificacao('Relatório gerado com sucesso!', 'success');
}

function filtrarRelatorio() {
    mostrarNotificacao('Filtrando dados por mês...', 'info');
}

function exportarPDF() {
    mostrarNotificacao('PDF exportado com sucesso!', 'success');
}

// ========================
// NOTIFICAÇÕES
// ========================

function mostrarNotificacao(mensagem, tipo = 'info') {
    const notificacao = document.createElement('div');
    notificacao.className = `notificacao notificacao-${tipo}`;
    notificacao.textContent = mensagem;
    document.body.appendChild(notificacao);

    setTimeout(() => {
        notificacao.style.animation = 'slideOut 0.4s ease forwards';
        setTimeout(() => notificacao.remove(), 500);
    }, 3500);
}

// ========================
// CONFETES
// ========================

function lancarConfetes() {
    const cores = ['#6C63FF', '#FF6B6B', '#FFD93D', '#6BCB77', '#4D96FF', '#FFB347'];
    for (let i = 0; i < 60; i++) {
        const confete = document.createElement('div');
        confete.className = 'confete';
        confete.style.left = Math.random() * 100 + '%';
        confete.style.top = '-10px';
        confete.style.background = cores[Math.floor(Math.random() * cores.length)];
        confete.style.width = Math.random() * 10 + 4 + 'px';
        confete.style.height = Math.random() * 10 + 4 + 'px';
        confete.style.borderRadius = Math.random() > 0.5 ? '50%' : '2px';
        confete.style.animationDuration = (Math.random() * 2 + 2) + 's';
        confete.style.animationDelay = (Math.random() * 0.5) + 's';
        document.body.appendChild(confete);
        setTimeout(() => confete.remove(), 4000);
    }
}

// ========================
// LOGIN
// ========================

async function validarLogin(event) {
    event?.preventDefault();
    const email = document.getElementById('email')?.value || '';
    const senha = document.getElementById('senha')?.value || '';

    if (!email || !senha) {
        mostrarNotificacao('Preencha todos os campos para continuar.', 'error');
        return false;
    }

    try {
        const sessao = await ConectaTIAPI.login(email, senha);
        localStorage.setItem('sessaoConectaTI', JSON.stringify(sessao));
        localStorage.setItem('unidadeUsuario', document.getElementById('unidade')?.value || '');
        const perfil = getPerfilUsuario();
        const paginaPadrao = getPaginaPadrao(perfil);
        mostrarNotificacao(`Login realizado com sucesso como ${perfil}.`, 'success');
        setTimeout(() => { window.location.href = paginaPadrao; }, 500);
    } catch (error) {
        mostrarNotificacao(error.message, 'error');
    }
    return false;
}

// ========================
// SAUDAÇÃO E PERFIL
// ========================

function saudacaoDinamica() {
    const hora = new Date().getHours();
    let saudacao = '';
    if (hora >= 5 && hora < 12) saudacao = 'Bom dia';
    else if (hora >= 12 && hora < 18) saudacao = 'Boa tarde';
    else if (hora >= 18 && hora < 23) saudacao = 'Boa noite';
    else saudacao = 'Boa madrugada';

    const perfil = getPerfilUsuario();
    const unidade = getUnidadeUsuario();
    const el = document.getElementById('saudacao');
    if (el) el.textContent = `${saudacao}, ${perfil}!${unidade ? ' - ' + unidade : ''}`;
}

function carregarPerfil() {
    const perfil = getPerfilUsuario();
    const nome = getEmailUsuario()?.split('@')[0] || 'Usuário';
    const unidade = getUnidadeUsuario();

    const avatar = document.getElementById('userAvatar');
    if (avatar) avatar.textContent = nome.charAt(0).toUpperCase();

    const nomeEl = document.getElementById('userName');
    if (nomeEl) nomeEl.textContent = nome;

    const perfilEl = document.getElementById('userPerfil');
    if (perfilEl) perfilEl.textContent = perfil;

    const unidadeEl = document.getElementById('userUnidade');
    if (unidadeEl) unidadeEl.textContent = unidade;
}

function exibirDataAtual() {
    const data = new Date().toLocaleDateString('pt-BR', {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
    const el = document.getElementById('dataAtual');
    if (el) el.textContent = data;
}

// ========================
// LOCALSTORAGE
// ========================

function salvarChamadosLocalStorage() {
    try {
        localStorage.setItem('chamadosConectaTI', JSON.stringify(chamados));
    } catch (e) { /* ignore */ }
}

function carregarChamadosLocalStorage() {
    try {
        const dados = localStorage.getItem('chamadosConectaTI');
        if (dados) {
            const carregados = JSON.parse(dados);
            chamados.length = 0;
            carregados.forEach(c => chamados.push(c));
            return true;
        }
    } catch (e) { /* ignore */ }
    return false;
}

function salvarEquipamentosLocalStorage() {
    try {
        localStorage.setItem('equipamentosConectaTI', JSON.stringify(equipamentos));
    } catch (e) { /* ignore */ }
}

function carregarEquipamentosLocalStorage() {
    try {
        const dados = localStorage.getItem('equipamentosConectaTI');
        if (dados) {
            const carregados = JSON.parse(dados);
            equipamentos.length = 0;
            carregados.forEach(e => equipamentos.push(e));
            return true;
        }
    } catch (e) { /* ignore */ }
    return false;
}

// ========================
// SIDEBAR MOBILE
// ========================

function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    const overlay = document.getElementById('sidebarOverlay');
    if (sidebar) sidebar.classList.toggle('aberto');
    if (overlay) overlay.classList.toggle('ativo');
}

function fecharSidebar() {
    const sidebar = document.getElementById('sidebar');
    const overlay = document.getElementById('sidebarOverlay');
    if (sidebar) sidebar.classList.remove('aberto');
    if (overlay) overlay.classList.remove('ativo');
}

// ========================
// EASTER EGG
// ========================

let easterEggBuffer = '';
let easterEggAtivado = false;

document.addEventListener('keydown', (e) => {
    if (e.target.tagName === 'INPUT' || e.target.tagName === 'TEXTAREA') return;

    easterEggBuffer += e.key.toLowerCase();
    if (easterEggBuffer.length > 10) easterEggBuffer = easterEggBuffer.slice(-10);

    if (easterEggBuffer.includes('senac') && !easterEggAtivado) {
        easterEggAtivado = true;
        lancarConfetes();
        mostrarNotificacao('Você encontrou o Easter Egg! Senac é demais!', 'success');
        setTimeout(() => {
            easterEggAtivado = false;
            easterEggBuffer = '';
        }, 5000);
    }
});

// ========================
// INICIALIZAÇÃO
// ========================

document.addEventListener('DOMContentLoaded', function() {
    const pagina = window.location.pathname.split('/').pop();

    if (pagina === 'login.html') {
        return;
    }

    if (pagina !== 'index.html' && pagina !== 'login.html' && pagina !== '') {
        verificarPermissoes();
    }

    loadTheme();
    carregarChamadosLocalStorage();
    carregarEquipamentosLocalStorage();

    carregarPerfil();
    exibirDataAtual();
    saudacaoDinamica();

    switch(pagina) {
        case 'dashboard.html':
            atualizarDashboard();
            setTimeout(carregarGraficos, 500);
            break;
        case 'chamados.html':
            exibirChamados();
            break;
        case 'equipamentos.html':
            exibirEquipamentos();
            break;
        case 'novo-chamado.html':
            const perfilSelect = document.getElementById('perfilChamado');
            const grupoTurma = document.getElementById('grupoTurma');
            if (perfilSelect && grupoTurma) {
                perfilSelect.addEventListener('change', function() {
                    grupoTurma.style.display = this.value === 'Instrutor' ? 'block' : 'none';
                });
            }
            break;
        case 'relatorios.html':
            setTimeout(() => {
                gerarRelatorioMensal();
                gerarRelatorioCategoria();
            }, 300);
            break;
        case 'duvidas-frequentes.html':
            exibirDuvidas();
            break;
    }

    const links = document.querySelectorAll('.tab-bar ul li a');
    links.forEach(link => {
        const href = link.getAttribute('href');
        if (href === pagina) {
            link.classList.add('active');
        } else {
            link.classList.remove('active');
        }
    });

    const sidebarLinks = document.querySelectorAll('.sidebar-nav a');
    sidebarLinks.forEach(link => {
        const href = link.getAttribute('href');
        if (href === pagina) {
            link.classList.add('active');
        } else {
            link.classList.remove('active');
        }
    });

    document.addEventListener('click', function(e) {
        if (window.innerWidth <= 768) {
            const sidebar = document.getElementById('sidebar');
            const overlay = document.getElementById('sidebarOverlay');
            if (sidebar && !sidebar.contains(e.target) && e.target.closest('.menu-toggle') === null) {
                sidebar.classList.remove('aberto');
                if (overlay) overlay.classList.remove('ativo');
            }
        }
    });
});
