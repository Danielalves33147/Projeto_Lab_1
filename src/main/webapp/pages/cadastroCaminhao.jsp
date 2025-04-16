<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="utils.Conexao" %>
<%@ page import="br.com.projeto.dao.TransportadorDao" %>
<%@ page import="br.com.projeto.model.Transportador" %>
<%@ page import="java.util.List" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    TransportadorDao transportadorDao = new TransportadorDao(Conexao.getConnection());
    List<Transportador> transportadores = transportadorDao.listarTodos();
    String sucesso = request.getParameter("sucesso");
    String erro = request.getParameter("erro");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Cadastro de Caminhão - Sistema de Frota</title>
  <link rel="stylesheet" href="../css/caminhao_style.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>

  <script>
    window.onload = function () {
      const sucesso = "<%= (sucesso != null && !"null".equals(sucesso)) ? sucesso : "" %>";
      const erro = "<%= (erro != null && !"null".equals(erro)) ? erro : "" %>";

      if (sucesso) {
        alert("Caminhão cadastrado com sucesso!");
        window.history.replaceState(null, "", window.location.pathname);
      } else if (erro) {
        alert("Erro: " + erro);
        window.history.replaceState(null, "", window.location.pathname);
      }
    };
  </script>
</head>
<body>
  <header>
    <h1>Cadastro de Caminhão</h1>
  </header>

  <main class="cadastro-container">
    <section class="cadastro-direita">
      <form action="../caminhao" method="post" class="formulario">
        <input type="hidden" name="acao" value="cadastrar">

        <input type="text" name="placa" placeholder="Placa" required>
        <input type="text" name="modelo" placeholder="Modelo" required>
        <input type="text" name="tipo" placeholder="Tipo" required>

        <div class="form-grupo">
          <label for="anoFabricacao">Data de Cadastro:</label>
          <input type="date" id="anoFabricacao" name="anoFabricacao" required>
        </div>

        <div class="form-grupo">
          <label for="capacidadeMax">Capacidade Máxima (kg):</label>
          <input type="text" id="capacidadeMax" name="capacidadeMax" required />
        </div>

        <label for="transportador">Transportador</label>
        <select name="cpfCnpj" id="transportador" required>
          <option value="">Selecione um Transportador</option>
          <% for (Transportador t : transportadores) { %>
            <option value="<%= t.getCpfCnpj() %>"><%= t.getNomeTransportador() %> - <%= t.getCpfCnpj() %></option>
          <% } %>
        </select>

        <button type="submit">Cadastrar</button>
      </form>
    </section>
  </main>

  <footer>
    <p>&copy; 2025 - Sistema de Frota</p>
  </footer>

  <script>
    $('#capacidadeMax').mask('#.##0,00', { reverse: true });
  </script>
</body>
</html>
