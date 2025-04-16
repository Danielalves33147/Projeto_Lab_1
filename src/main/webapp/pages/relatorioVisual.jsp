<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.projeto.dao.*, br.com.projeto.model.*" %>
<%@ page import="utils.Conexao" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.*" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    String tipo = request.getParameter("tipo");
    String idParam = request.getParameter("id");

    Servico servico = null;
    Orcamento orcamento = null;
    Transportador transportador = null;
    Motorista motorista = null;
    Caminhao caminhao = null;

    if (tipo != null && idParam != null) {
        int id = Integer.parseInt(idParam);
        try {
            Connection conn = Conexao.getConnection();

            if ("servico".equals(tipo)) {
                servico = new ServicoDao(conn).buscarPorId(id);
                if (servico != null) {
                    transportador = new TransportadorDao(conn).buscarPorId(servico.getIdTransportador());
                    ServicoHasMotoristaDao shDao = new ServicoHasMotoristaDao(conn);
                    List<ServicoHasMotorista> vinculos = shDao.buscarPorServico(servico.getIdServico());

                    if (!vinculos.isEmpty()) {
                        ServicoHasMotorista v = vinculos.get(0);
                        motorista = new MotoristaDao(conn).buscarPorId(v.getMotoristaCpf());
                        caminhao = new CaminhaoDao(conn).buscarPorId(v.getCaminhaoPlaca());
                    }
                }
            } else if ("orcamento".equals(tipo)) {
                orcamento = new OrcamentoDao(conn).buscarPorId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Relatório Visual</title>
  <link rel="stylesheet" href="../css/relatorioVisual_style.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<header>
  <h1><%= (tipo == null) ? "" : tipo.equals("servico") ? "Relatório de Serviço" : "Relatório de Orçamento" %></h1>
</header>

<main>
  <% if (tipo == null || idParam == null) { %>
    <section class="formulario">
      <h2><i class="fas fa-filter"></i> Selecione o Tipo de Relatório</h2>
      <div class="select-group">
        <select id="tipoRelatorio" onchange="alternarRelatorio()">
          <option value="">-- Escolha uma opção --</option>
          <option value="servico">🛠 Serviço</option>
          <option value="orcamento">💰 Orçamento</option>
        </select>
      </div>

      <form id="formServico" method="get" action="relatorioVisual.jsp" style="display:none;">
        <input type="hidden" name="tipo" value="servico">
        <input type="number" name="id" placeholder="Digite o ID do Serviço" required />
        <button type="submit"><i class="fas fa-file-alt"></i> Gerar</button>
      </form>

      <form id="formOrcamento" method="get" action="relatorioVisual.jsp" style="display:none;">
        <input type="hidden" name="tipo" value="orcamento">
        <input type="number" name="id" placeholder="Digite o ID do Orçamento" required />
        <button type="submit"><i class="fas fa-file-invoice-dollar"></i> Gerar</button>
      </form>
    </section>

  <% } else if ("servico".equals(tipo) && servico != null) { %>
    <section class="relatorio-card">
      <h2>Detalhes do Serviço</h2>
      <p><strong>ID:</strong> <%= servico.getIdServico() %></p>
      <p><strong>Tipo:</strong> <%= servico.getTipoServico() %></p>
      <p><strong>Descrição:</strong> <%= servico.getDescricaoServico() %></p>
      <p><strong>Início:</strong> <%= servico.getDataInicio() %></p>
      <p><strong>Término:</strong> <%= servico.getDataTermino() %></p>
      <p><strong>Valor Total:</strong> R$ <%= servico.getValorTotal() %></p>
    </section>

    <section class="relatorio-card">
      <h2>Caminhão</h2>
      <% if (caminhao != null) { %>
        <p><strong>Placa:</strong> <%= caminhao.getPlaca() %></p>
        <p><strong>Modelo:</strong> <%= caminhao.getModelo() %></p>
        <p><strong>Tipo:</strong> <%= caminhao.getTipo() %></p>
        <p><strong>Capacidade:</strong> <%= caminhao.getCapacidadeMax() %> kg</p>
      <% } else { %><p>Não encontrado.</p><% } %>
    </section>

    <section class="relatorio-card">
      <h2>Motorista</h2>
      <% if (motorista != null) { %>
        <p><strong>Nome:</strong> <%= motorista.getNomeMotorista() %></p>
        <p><strong>CPF:</strong> <%= motorista.getCpf() %></p>
        <p><strong>CNH:</strong> <%= motorista.getCnh() %></p>
      <% } else { %><p>Não encontrado.</p><% } %>
    </section>

    <section class="relatorio-card">
      <h2>Transportador</h2>
      <% if (transportador != null) { %>
        <p><strong>Nome:</strong> <%= transportador.getNomeTransportador() %></p>
        <p><strong>CPF/CNPJ:</strong> <%= transportador.getCpfCnpj() %></p>
        <p><strong>Telefone:</strong> <%= transportador.getTelefone() %></p>
      <% } else { %><p>Não encontrado.</p><% } %>
    </section>

    <div class="area-imprimir">
      <button class="botao-imprimir" onclick="window.print()">📄 Salvar como PDF</button>
    </div>

  <% } else if ("orcamento".equals(tipo) && orcamento != null) { %>
    <section class="relatorio-card">
      <h2>Detalhes do Orçamento</h2>
      <p><strong>ID:</strong> <%= orcamento.getIdOrcamento() %></p>
      <p><strong>Data de Emissão:</strong> <%= orcamento.getDataEmissao() %></p>
      <p><strong>Tipo de Serviço:</strong> <%= orcamento.getTipoServico() %></p>
      <p><strong>Valor Diária:</strong> R$ <%= orcamento.getValorDiaria() %></p>
      <p><strong>Valor Total:</strong> R$ <%= orcamento.getValorTotal() %></p>
      <p><strong>Descrição:</strong> <%= orcamento.getDescricao() %></p>
      <p><strong>Detalhes:</strong> <%= orcamento.getDetalhes() %></p>
    </section>

    <div class="area-imprimir">
      <button class="botao-imprimir" onclick="window.print()">📄 Salvar como PDF</button>
    </div>
  <% } else { %>
    <p class="erro">⚠️ Nenhum dado encontrado para o ID informado.</p>
  <% } %>
</main>

<footer>
  <p>Relatório gerado em <%= new java.util.Date() %></p>
</footer>

<script>
  function alternarRelatorio() {
    const tipo = document.getElementById("tipoRelatorio").value;
    document.getElementById("formServico").style.display = tipo === "servico" ? "block" : "none";
    document.getElementById("formOrcamento").style.display = tipo === "orcamento" ? "block" : "none";
  }
</script>

</body>
</html>
