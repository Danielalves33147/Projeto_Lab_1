<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="utils.Conexao" %>
<%@ page import="br.com.projeto.dao.CaminhaoDao" %>
<%@ page import="br.com.projeto.model.Caminhao" %>
<%@ page import="br.com.projeto.model.Motorista" %>
<%@ page import="java.util.List" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    CaminhaoDao caminhaoDao = new CaminhaoDao(Conexao.getConnection());
    List<Caminhao> caminhoes = caminhaoDao.listarTodos();
    
    Motorista motoristaEditando = (Motorista) request.getAttribute("motoristaEditando");
    boolean editandoMotorista = motoristaEditando != null;
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Cadastro de Usuários - Sistema de Frota</title>
  <link rel="stylesheet" href="../css/cadastro_style.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.js"></script>
  <script>
    function selecionarTipo(tipo) {
      document.getElementById("formTransportador").style.display = "none";
      document.getElementById("formMotorista").style.display = "none";
      document.getElementById("btnTransportador").classList.remove("ativo");
      document.getElementById("btnMotorista").classList.remove("ativo");

      if (tipo === "transportador") {
        document.getElementById("formTransportador").style.display = "block";
        document.getElementById("btnTransportador").classList.add("ativo");
      } else if (tipo === "motorista") {
        document.getElementById("formMotorista").style.display = "block";
        document.getElementById("btnMotorista").classList.add("ativo");
      }
    }

    window.onload = function () {
      const urlParams = new URLSearchParams(window.location.search);

      if (urlParams.get("sucessoMotorista") === "true") {
        alert("Motorista cadastrado com sucesso!");
        selecionarTipo("motorista");
      }
      if (urlParams.get("sucessoTransportador") === "true") {
        alert("Transportador cadastrado com sucesso!");
        selecionarTipo("transportador");
      }
      if (urlParams.get("erroMotorista")) {
        alert(urlParams.get("erroMotorista"));
        selecionarTipo("motorista");
      }
      if (urlParams.get("erroTransportador")) {
        alert(urlParams.get("erroTransportador"));
        selecionarTipo("transportador");
      }

      if (window.location.search) {
        window.history.replaceState({}, document.title, window.location.pathname);
      }
    };
  </script>
</head>
<body>
  <header>
    <h1>Cadastro de Usuários</h1>
    <nav><ul></ul></nav>
  </header>

  <main class="cadastro-container">
    <section class="cadastro-esquerda">
      <h2>Escolha o Tipo de Usuário</h2>

      <div class="botoes-toggle">
        <button id="btnTransportador" onclick="selecionarTipo('transportador')">Transportador</button>
        <button id="btnMotorista" onclick="selecionarTipo('motorista')">Motorista</button>
      </div>

      <!-- Formulário de Transportador -->
      <form id="formTransportador" class="formulario" style="display:none;" method="post" action="../transportador">
        <input type="hidden" name="acao" value="cadastrar">
        <input type="text" name="cpfCnpj" placeholder="CPF ou CNPJ" required id="cpfCnpj">
        <input type="text" name="nome" placeholder="Nome" required>
        <input type="text" name="telefone" placeholder="Telefone" required id="telefone">
        <input type="text" name="endereco" placeholder="Endereço" required>
        <button type="submit">Cadastrar Transportador</button>
      </form>

      <!-- Formulário de Motorista -->
		<form id="formMotorista" class="formulario" style="display:none;" method="post" action="../motorista">
		  <input type="hidden" name="acao" value="<%= editandoMotorista ? "atualizar" : "cadastrar" %>">
		
		  <input type="text" name="cpf" placeholder="CPF" required id="cpfMotorista"
		         value="<%= editandoMotorista ? motoristaEditando.getCpf() : "" %>"
		         <%= editandoMotorista ? "readonly" : "" %>>
		
		  <input type="text" name="nome" placeholder="Nome" required
		         value="<%= editandoMotorista ? motoristaEditando.getNomeMotorista() : "" %>">
		
		  <input type="text" name="cnh" placeholder="CNH" required
		         value="<%= editandoMotorista ? motoristaEditando.getCnh() : "" %>">
		
		  <input type="text" name="telefone" placeholder="Telefone" required id="telefoneMotorista"
		         value="<%= editandoMotorista ? motoristaEditando.getTelefone() : "" %>">
		
		  <label for="caminhaoPlaca">Vincular ao Caminhão:</label>
		  <select name="caminhaoPlaca" id="caminhaoPlaca" required>
		    <option value="">Selecione um Caminhão</option>
		    <% for (Caminhao c : caminhoes) {
		         boolean selecionado = editandoMotorista && request.getParameter("placaSelecionada") != null &&
		                               request.getParameter("placaSelecionada").equals(c.getPlaca());
		    %>
		      <option value="<%= c.getPlaca() %>" <%= selecionado ? "selected" : "" %>>
		        <%= c.getPlaca() %> - <%= c.getModelo() %>
		      </option>
		    <% } %>
		  </select>
		
		  <button type="submit"><%= editandoMotorista ? "Atualizar Motorista" : "Cadastrar Motorista" %></button>
		</form>

    </section>
  </main>

  <footer>
    <p>&copy; 2025 - Sistema de Frota</p>
  </footer>

  <script>
    $('#cpfCnpj').mask('000.000.000-00', {reverse: true});
    $('#cpfMotorista').mask('000.000.000-00', {reverse: true});
    $('#telefone').mask('(00) 00000-0000');
    $('#telefoneMotorista').mask('(00) 00000-0000');
  </script>
</body>
</html>
