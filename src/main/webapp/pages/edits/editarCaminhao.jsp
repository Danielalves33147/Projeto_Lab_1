<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.projeto.model.*, br.com.projeto.dao.*, utils.Conexao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    String placa = request.getParameter("placa");
    Caminhao caminhao = null;
    List<Transportador> transportadores = null;

    try {
        CaminhaoDao caminhaoDao = new CaminhaoDao(Conexao.getConnection());
        caminhao = caminhaoDao.buscarPorId(placa);

        TransportadorDao transportadorDao = new TransportadorDao(Conexao.getConnection());
        transportadores = transportadorDao.listarTodos();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <title>Editar Caminhão</title>
  <link rel="stylesheet" href="../css/cadastro_style.css" />
</head>
<body>
  <header>
    <h1>Editar Caminhão</h1>
  </header>

  <main class="cadastro-container">
    <section class="cadastro-esquerda">
      <form class="formulario" method="post" action="../caminhao">
        <input type="hidden" name="acao" value="atualizar">
        <input type="hidden" name="placa" value="<%= caminhao.getPlaca() %>">

        <input type="text" name="modelo" placeholder="Modelo" value="<%= caminhao.getModelo() %>" required>
        <input type="text" name="tipo" placeholder="Tipo" value="<%= caminhao.getTipo() %>" required>
        <input type="date" name="anoFabricacao" value="<%= caminhao.getAnoFabricacao() %>" required>
        <input type="number" name="capacidade" step="0.01" placeholder="Capacidade Máxima (kg)" value="<%= caminhao.getCapacidadeMax() %>" required>

        <label for="transportadorCpf">Transportador Vinculado:</label>
        <select name="transportadorCpf" id="transportadorCpf" required>
          <option value="">Selecione</option>
          <% for (Transportador t : transportadores) { %>
            <option value="<%= t.getCpfCnpj() %>" <%= t.getCpfCnpj().equals(caminhao.getCpfCnpj()) ? "selected" : "" %>>
              <%= t.getCpfCnpj() %> - <%= t.getNomeTransportador() %>
            </option>
          <% } %>
        </select>

        <button type="submit">Salvar Alterações</button>
      </form>
    </section>
  </main>

  <footer>
    <p>&copy; 2025 - Sistema de Frota</p>
  </footer>
</body>
</html>
