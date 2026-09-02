import { createFileRoute, Link } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { AppShell } from "@/components/conectati/AppLayout";
import { chamadosService, type Chamado } from "@/services/conectati";
import { getUsuario } from "@/services/session";
import { slug } from "@/lib/conectati/store";

export const Route = createFileRoute("/chamados/$chamadoId")({
  component: () => (
    <AppShell active="chamados">
      <DetalheChamado />
    </AppShell>
  ),
});

const formatarData = (valor?: string) => (valor ? new Date(valor).toLocaleString("pt-BR") : "-");

function DetalheChamado() {
  const { chamadoId } = Route.useParams();
  const usuario = getUsuario();
  const [chamado, setChamado] = useState<Chamado | null>(null);
  const [erro, setErro] = useState("");
  const [mensagem, setMensagem] = useState("");
  const [enviando, setEnviando] = useState(false);
  const [relatorio, setRelatorio] = useState({
    problemaIdentificado: "",
    diagnostico: "",
    procedimentosRealizados: "",
    solucaoAplicada: "",
    observacoes: "",
  });

  const carregar = () =>
    chamadosService
      .buscar(Number(chamadoId))
      .then(setChamado)
      .catch((error) => setErro(error.message));
  useEffect(() => {
    carregar();
  }, [chamadoId]);

  const assumir = async () => {
    setEnviando(true);
    setErro("");
    try {
      setChamado(await chamadosService.assumir(Number(chamadoId)));
      setMensagem("Chamado assumido com sucesso.");
    } catch (error) {
      setErro(error instanceof Error ? error.message : "Nao foi possivel assumir o chamado.");
    } finally {
      setEnviando(false);
    }
  };

  const iniciarAtendimento = async () => {
    setEnviando(true);
    setErro("");
    try {
      setChamado(
        await chamadosService.alterarStatus(Number(chamadoId), { status: "EM_ANDAMENTO" }),
      );
      setMensagem("Atendimento iniciado.");
    } catch (error) {
      setErro(error instanceof Error ? error.message : "Nao foi possivel iniciar o atendimento.");
    } finally {
      setEnviando(false);
    }
  };

  const concluir = async (event: React.FormEvent) => {
    event.preventDefault();
    setEnviando(true);
    setErro("");
    try {
      await chamadosService.registrarRelatorio(Number(chamadoId), relatorio);
      setChamado(
        await chamadosService.alterarStatus(Number(chamadoId), {
          status: "CONCLUIDO",
          solucao: relatorio.solucaoAplicada,
        }),
      );
      setMensagem("Chamado concluido com sucesso.");
    } catch (error) {
      setErro(error instanceof Error ? error.message : "Nao foi possivel concluir o chamado.");
    } finally {
      setEnviando(false);
    }
  };

  if (!chamado && !erro)
    return (
      <main className="content">
        <p className="muted">Carregando chamado...</p>
      </main>
    );
  if (!chamado)
    return (
      <main className="content">
        <p className="alerta erro">{erro}</p>
        <Link to="/chamados" className="btn btn-outline">
          Voltar
        </Link>
      </main>
    );

  const tecnico = usuario?.tipo === "TECNICO";
  const responsavel = chamado.tecnicoResponsavelId === usuario?.id;
  const podeAssumir =
    tecnico &&
    !chamado.tecnicoResponsavelId &&
    !["CONCLUIDO", "CANCELADO"].includes(chamado.status);
  const podeIniciar = tecnico && responsavel && chamado.status === "EM_ANALISE";
  const podeConcluir = tecnico && responsavel && chamado.status === "EM_ANDAMENTO";

  return (
    <main className="content">
      <header className="topbar">
        <div>
          <h1>Chamado #{chamado.id}</h1>
          <p className="muted">Detalhes e acompanhamento da solicitacao.</p>
        </div>
        <Link to="/chamados" className="btn btn-outline">
          Voltar
        </Link>
      </header>
      {erro && <p className="alerta erro">{erro}</p>}
      {mensagem && <p className="alerta sucesso">{mensagem}</p>}
      <section className="card reveal visible">
        <div className="detail-grid">
          <div>
            <small className="muted">Titulo</small>
            <p>
              <b>{chamado.titulo}</b>
            </p>
          </div>
          <div>
            <small className="muted">Status</small>
            <p>
              <span className={`status ${slug(chamado.status)}`}>{chamado.status}</span>
            </p>
          </div>
          <div>
            <small className="muted">Solicitante</small>
            <p>{chamado.solicitanteNome || "-"}</p>
          </div>
          <div>
            <small className="muted">Tecnico responsavel</small>
            <p>{chamado.tecnicoResponsavelNome || "Nao atribuido"}</p>
          </div>
          <div>
            <small className="muted">Categoria</small>
            <p>{chamado.categoriaNome || "-"}</p>
          </div>
          <div>
            <small className="muted">Sala</small>
            <p>{chamado.salaNome || "Nao informada"}</p>
          </div>
          <div>
            <small className="muted">Prioridade</small>
            <p>
              <span className={`prioridade ${slug(chamado.prioridade)}`}>{chamado.prioridade}</span>
            </p>
          </div>
          <div>
            <small className="muted">Criado em</small>
            <p>{formatarData(chamado.dataCriacao)}</p>
          </div>
        </div>
        <div>
          <small className="muted">Descricao</small>
          <p>{chamado.descricao}</p>
        </div>
        {chamado.solucao && (
          <div>
            <small className="muted">Solucao</small>
            <p>{chamado.solucao}</p>
          </div>
        )}
        {chamado.dataFechamento && (
          <div>
            <small className="muted">Concluido em</small>
            <p>{formatarData(chamado.dataFechamento)}</p>
          </div>
        )}
        <div className="actions-row">
          {podeAssumir && (
            <button className="btn btn-primary" disabled={enviando} onClick={assumir}>
              Assumir chamado
            </button>
          )}
          {podeIniciar && (
            <button className="btn btn-primary" disabled={enviando} onClick={iniciarAtendimento}>
              Iniciar atendimento
            </button>
          )}
        </div>
      </section>
      {podeConcluir && (
        <section className="card reveal visible">
          <h2>Concluir chamado</h2>
          <p className="muted">Preencha o relatorio tecnico para concluir o atendimento.</p>
          <form className="form form-grid" onSubmit={concluir}>
            <label>
              Problema identificado
              <textarea
                required
                rows={3}
                value={relatorio.problemaIdentificado}
                onChange={(event) =>
                  setRelatorio({ ...relatorio, problemaIdentificado: event.target.value })
                }
              />
            </label>
            <label>
              Diagnostico
              <textarea
                required
                rows={3}
                value={relatorio.diagnostico}
                onChange={(event) =>
                  setRelatorio({ ...relatorio, diagnostico: event.target.value })
                }
              />
            </label>
            <label className="full">
              Procedimentos realizados
              <textarea
                required
                rows={3}
                value={relatorio.procedimentosRealizados}
                onChange={(event) =>
                  setRelatorio({ ...relatorio, procedimentosRealizados: event.target.value })
                }
              />
            </label>
            <label>
              Solucao aplicada
              <textarea
                required
                rows={3}
                value={relatorio.solucaoAplicada}
                onChange={(event) =>
                  setRelatorio({ ...relatorio, solucaoAplicada: event.target.value })
                }
              />
            </label>
            <label>
              Observacoes
              <textarea
                rows={3}
                value={relatorio.observacoes}
                onChange={(event) =>
                  setRelatorio({ ...relatorio, observacoes: event.target.value })
                }
              />
            </label>
            <div className="full actions-row">
              <button className="btn btn-primary" disabled={enviando}>
                Concluir chamado
              </button>
            </div>
          </form>
        </section>
      )}
    </main>
  );
}
