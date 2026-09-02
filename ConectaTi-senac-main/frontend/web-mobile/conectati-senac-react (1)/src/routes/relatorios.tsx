import { createFileRoute } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { AppShell } from "@/components/conectati/AppLayout";
import { toast } from "@/lib/conectati/store";
import { chamadosService, equipamentosService } from "@/services/conectati";

export const Route = createFileRoute("/relatorios")({
  head: () => ({
    meta: [
      { title: "ConectaTI Senac — Relatórios" },
      { name: "description", content: "Indicadores e exportações da unidade." },
    ],
  }),
  component: () => (
    <AppShell active="relatorios">
      <Rel />
    </AppShell>
  ),
});

function Counter({ to }: { to: number }) {
  const [n, setN] = useState(0);
  useEffect(() => {
    const t0 = performance.now();
    const dur = 1400;
    let raf = 0;
    const step = (now: number) => {
      const p = Math.min((now - t0) / dur, 1);
      setN(Math.round(to * (1 - Math.pow(1 - p, 3))));
      if (p < 1) raf = requestAnimationFrame(step);
    };
    raf = requestAnimationFrame(step);
    return () => cancelAnimationFrame(raf);
  }, [to]);
  return <b>{n}</b>;
}

function Rel() {
  const act = (n: string) => toast(`${n} — ação simulada`);
  const [gerando, setGerando] = useState(false);

  const gerarRelatorio = async () => {
    setGerando(true);
    try {
      const [chamados, equipamentos] = await Promise.all([
        chamadosService.listar(),
        equipamentosService.listar(),
      ]);
      const linhas = [
        ["Relatorio ConectaTI Senac"],
        ["Gerado em", new Date().toLocaleString("pt-BR")],
        [],
        ["Resumo"],
        ["Total de chamados", chamados.length],
        ["Chamados abertos", chamados.filter((item) => item.status === "ABERTO").length],
        ["Chamados concluidos", chamados.filter((item) => item.status === "CONCLUIDO").length],
        ["Total de equipamentos", equipamentos.length],
        [],
        ["Chamados"],
        ["ID", "Titulo", "Status", "Prioridade", "Categoria", "Solicitante", "Data de criacao"],
        ...chamados.map((item) => [
          item.id,
          item.titulo,
          item.status,
          item.prioridade,
          item.categoriaNome || "",
          item.solicitanteNome || "",
          item.dataCriacao,
        ]),
      ];
      const csv = linhas
        .map((linha) =>
          linha.map((valor) => `"${String(valor ?? "").replaceAll('"', '""')}"`).join(";"),
        )
        .join("\n");
      const arquivo = new Blob(["\uFEFF", csv], { type: "text/csv;charset=utf-8" });
      const url = URL.createObjectURL(arquivo);
      const link = document.createElement("a");
      link.href = url;
      link.download = `relatorio-conectati-${new Date().toISOString().slice(0, 10)}.csv`;
      link.click();
      URL.revokeObjectURL(url);
      toast("Relatorio gerado e baixado com sucesso.");
    } catch (error) {
      toast(error instanceof Error ? error.message : "Nao foi possivel gerar o relatorio.");
    } finally {
      setGerando(false);
    }
  };
  return (
    <main className="content">
      <header className="topbar">
        <div>
          <h1>Relatórios</h1>
          <p className="muted">Indicadores e exportações da unidade.</p>
        </div>
        <div className="actions-row">
          <button className="btn btn-outline" onClick={() => act("Filtrar por mês")}>
            Filtrar por mês
          </button>
          <button className="btn btn-outline" onClick={() => act("Filtrar por categoria")}>
            Filtrar por categoria
          </button>
          <button className="btn btn-primary" onClick={gerarRelatorio} disabled={gerando}>
            {gerando ? "Gerando..." : "Gerar relatório"}
          </button>
          <button className="btn btn-ghost" onClick={() => act("Exportar PDF")}>
            Exportar PDF
          </button>
        </div>
      </header>
      <section className="cards-grid">
        {[
          { n: 128, l: "Chamados no mês", g: "var(--grad-blue)" },
          { n: 96, l: "Resolvidos", g: "var(--grad-green)" },
          { n: 12, l: "Pendentes", g: "var(--grad-red)" },
          { n: 47, l: "Reservas de notebooks", g: "var(--grad-orange)" },
        ].map((k, i) => (
          <div key={i} className="card kpi reveal visible">
            <span className="kpi-icon" style={{ background: k.g }} />
            <div>
              <Counter to={k.n} />
              <small>{k.l}</small>
            </div>
          </div>
        ))}
      </section>
      <section className="charts">
        <div className="card reveal visible">
          <h3>Salas com mais problemas</h3>
          <div className="bars">
            {[
              ["Lab 02", 100, "var(--grad-red)", 22],
              ["Lab 01", 82, "var(--grad-orange)", 18],
              ["Sala 12", 60, "var(--grad-yellow)", 13],
              ["Secretaria", 35, "var(--grad-blue)", 8],
              ["Sala TI", 22, "var(--grad-teal)", 5],
            ].map(([lab, w, bg, v]) => (
              <div key={lab as string} className="bar-row">
                <span>{lab}</span>
                <div className="bar">
                  <i style={{ ["--w" as any]: `${w}%`, background: bg as string }} />
                </div>
                <b>{v}</b>
              </div>
            ))}
          </div>
        </div>
        <div className="card reveal visible">
          <h3>Equipamentos mais solicitados</h3>
          <div className="bars">
            {[
              ["Notebook", 100, "var(--grad-purple)", 34],
              ["Projetor", 70, "var(--grad-pink)", 24],
              ["Cabo HDMI", 50, "var(--grad-blue)", 17],
              ["Mouse", 30, "var(--grad-green)", 10],
            ].map(([lab, w, bg, v]) => (
              <div key={lab as string} className="bar-row">
                <span>{lab}</span>
                <div className="bar">
                  <i style={{ ["--w" as any]: `${w}%`, background: bg as string }} />
                </div>
                <b>{v}</b>
              </div>
            ))}
          </div>
        </div>
      </section>
    </main>
  );
}
