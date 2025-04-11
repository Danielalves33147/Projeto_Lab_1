<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gerar Orçamento - Sistema de Frota</title>
  <link rel="stylesheet" href="../css/orcamento_style.css">
</head>
<body>
  <header>
    <h1>Gerar Orçamento</h1>
    <nav><ul></ul></nav>
  </header>
  <main>
    <section class="formulario">
      <h2>Dados do Orçamento</h2>
      <form action="../orcamento" method="post">
        <!-- Descrição do orçamento -->
        <input type="text" name="descricao" placeholder="Descrição do Orçamento" required>
        
        <!-- Data de emissão -->
        <input type="date" name="dataEmissao" required>
        
        <!-- Tipo de serviço -->
        <input type="text" name="tipoServico" placeholder="Tipo de Serviço (Ex: Entrega, Montagem)" required>
        
        <!-- Detalhes (descrição detalhada) -->
        <textarea name="detalhes" rows="4" placeholder="Descrição detalhada do serviço"></textarea>
        
        <!-- Valor diário -->
        <input type="number" step="0.01" name="valorDiaria" placeholder="Valor Diário (R$)" required>
        
        <!-- Valor total -->
        <input type="number" step="0.01" name="valorTotal" placeholder="Valor Total (R$)" required>
                 
        <button type="submit">Gerar Orçamento</button>
      </form>
    </section>
  </main>
  <footer>
    <p>&copy; 2025 - Sistema de Frota</p>
  </footer>
  <script>
  $('#valorDiaria').mask('R$ #.##0,00', { reverse: true });
  $('#valorTotal').mask('R$ #.##0,00', { reverse: true });
</script>
</body>
</html>
