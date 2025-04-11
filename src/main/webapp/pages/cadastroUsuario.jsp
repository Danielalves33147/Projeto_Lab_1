<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Cadastro - Sistema de Frota</title>
  <link rel="stylesheet" href="../css/cadastro_style.css">
  
  <script>
    function selecionarTipo(tipo) {
      const btnTransportador = document.getElementById("btnTransportador");
      const btnMotorista = document.getElementById("btnMotorista");
      const formTransportador = document.getElementById("formTransportador");
      const formMotorista = document.getElementById("formMotorista");

      btnTransportador.classList.remove("ativo");
      btnMotorista.classList.remove("ativo");
      formTransportador.style.display = "none";
      formMotorista.style.display = "none";

      if (tipo === "transportador") {
        btnTransportador.classList.add("ativo");
        formTransportador.style.display = "block";
      } else if (tipo === "motorista") {
        btnMotorista.classList.add("ativo");
        formMotorista.style.display = "block";
      }
    }

    window.onload = function () {
      const urlParams = new URLSearchParams(window.location.search);
      if (urlParams.get("sucessoMotorista") === "true") {
        document.getElementById("formMotorista")?.reset();
        selecionarTipo("motorista");
        alert("Motorista cadastrado com sucesso!");
      }
      if (urlParams.get("sucessoTransportador") === "true") {
        document.getElementById("formTransportador")?.reset();
        selecionarTipo("transportador");
        alert("Transportador cadastrado com sucesso!");
      }
      if (urlParams.get("erroMotorista")) {
        alert(urlParams.get("erroMotorista"));
      }
      if (urlParams.get("erroTransportador")) {
        alert(urlParams.get("erroTransportador"));
      }
    };
  </script>
  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.js"></script> <!-- Biblioteca para máscara -->
</head>
<body>

<header>
  <h1>Cadastro</h1>
  <nav>
    <ul></ul>
  </nav>
</header>

<main class="cadastro-container">
  <section class="cadastro-esquerda">
    <h2>Cadastro de Usuários</h2>

    <section class="seletor-cadastro">
      <h3>Tipo de Cadastro</h3>
      <div class="botoes-toggle">
        <button type="button" id="btnTransportador" onclick="selecionarTipo('transportador')">Transportador</button>
        <button type="button" id="btnMotorista" onclick="selecionarTipo('motorista')">Motorista</button>
      </div>
    </section>

    <!-- Transportador -->
    <form id="formTransportador" action="../transportador" method="post" style="display:none;" class="formulario">
      <h3>Transportador</h3>
      <input type="text" name="cpfCnpj" placeholder="CPF/CNPJ" required id="cpfCnpj">
      <input type="text" name="nome" placeholder="Nome" required>
      <input type="text" name="telefone" placeholder="Telefone" required id="telefone">
      <input type="text" name="endereco" placeholder="Endereço" required>
      <input type="date" name="dataRegistro" required>
      <button type="submit">Cadastrar</button>
    </form>

    <!-- Motorista -->
    <form id="formMotorista" action="../motorista" method="post" style="display:none;" class="formulario">
      <h3>Motorista</h3>
      <input type="text" name="cpf" placeholder="CPF" required id="cpf">
      <input type="text" name="nome" placeholder="Nome" required>
      <input type="text" name="cnh" placeholder="CNH" required>
      <input type="text" name="telefone" placeholder="Telefone" required id="telefoneMotorista">
      <input type="text" name="idTransportador" placeholder="CPF do Transportador" required>
      <button type="submit">Cadastrar</button>
    </form>

  </section>
</main>

<footer>
  <p>&copy; 2025 - Sistema de Frota</p>
</footer>

<script>
  // Máscara para o campo CPF/CNPJ
  $('#cpfCnpj').mask('000.000.000-00', {reverse: true}); // Para CPF
  // Máscara para o telefone
  $('#telefone').mask('(00) 00000-0000');
  $('#telefoneMotorista').mask('(00) 00000-0000'); // Para Motorista
  
  // Máscara para CPF
  $('#cpf').mask('000.000.000-00', {reverse: true});  // Para CPF
</script>

</body>
</html>
