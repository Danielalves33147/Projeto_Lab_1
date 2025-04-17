<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.projeto.model.Servico" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    Servico servico = (Servico) request.getAttribute("servicoEditando");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <title>Editar Serviço</title>
  <link rel="stylesheet" href="../../css/cadastro_style.css" />
</head>
<body>
<header>
    <h1>✏️ Editar Serviço</h1>
</header>

<main class="cadastro-container">
  <section class="cadastro-esquerda">
    <form method="post" action="<%= request.getContextPath() %>/servico">
      <input type="hidden" name="acao" value="atualizar">
      <input type="hidden" name="idServico" value="<%= servico.getIdServico() %>">

      <label>Tipo de Serviço:</label>
      <input type="text" name="tipoServico" value="<%= servico.getTipoServico() %>" required>

      <label>Descrição:</label>
      <textarea name="descricaoServico" required><%= servico.getDescricaoServico() %></textarea>

      <label>Data de Início:</label>
      <input type="date" name="dataInicio" value="<%= servico.getDataInicio() %>" required>

      <label>Data de Término:</label>
      <input type="date" name="dataTermino" value="<%= servico.getDataTermino() %>" required>

      <label>Valor Total (R$):</label>
      <input type="number" step="0.01" name="valorTotal" value="<%= servico.getValorTotal() %>" required>

      <button type="submit">Salvar Alterações</button>
    </form>
  </section>
</main>

<footer>
  <p>&copy; 2025 - Sistema de Frota</p>
</footer>
</body>
</html>
