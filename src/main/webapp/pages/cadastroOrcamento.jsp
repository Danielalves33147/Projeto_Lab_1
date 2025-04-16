<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
    String sucesso = request.getParameter("sucesso");
    String erro = request.getParameter("erro");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gerar Orçamento - Sistema de Frota</title>
  
  <link rel="stylesheet" href="../css/orcamento_style.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>

  <script>
    window.onload = function () {
      const sucesso = "<%= (sucesso != null && !"null".equals(sucesso)) ? sucesso : "" %>";
      const erro = "<%= (erro != null && !"null".equals(erro)) ? erro : "" %>";

      if (sucesso) {
        alert("Orçamento gerado com sucesso!");
        document.querySelector("form")?.reset();
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
    <h1>Gerar Orçamento</h1>
  </header>

  <main>
<section class="formulario">
  <h2>Dados do Orçamento</h2>

  <!-- REMOVIDO class="formulario" daqui -->
  <form action="../orcamento" method="post">
    <input type="hidden" name="acao" value="cadastrar">

    <label for="descricao">Descrição</label>
    <input type="text" name="descricao" id="descricao" placeholder="Digite um nome para identificar" required>

    <label for="dataEmissao">Data de Emissão</label>
    <input type="date" name="dataEmissao" id="dataEmissao" required>

    <label for="tipoServico">Tipo de Serviço</label>
    <input type="text" name="tipoServico" id="tipoServico" placeholder="Ex: Entrega, Montagem" required>

    <label for="detalhes">Detalhes</label>
    <textarea name="detalhes" id="detalhes" rows="4" placeholder="Descrição detalhada do serviço"></textarea>

    <label for="valorDiaria">Valor Diário</label>
    <input type="text" name="valorDiaria" id="valorDiaria" placeholder="R$" required>

    <label for="valorTotal">Valor Total</label>
    <input type="text" name="valorTotal" id="valorTotal" placeholder="R$" required>

    <button type="submit">Gerar Orçamento</button>
  </form>
</section>


  </main>

  <footer>
    <p>&copy; 2025 - Sistema de Frota</p>
  </footer>

  <script>
    $('#valorDiaria').mask('#.##0,00', { reverse: true });
    $('#valorTotal').mask('#.##0,00', { reverse: true });
  </script>
</body>
</html>
