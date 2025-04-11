<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <title>Gerar Relatório - Sistema de Frota</title>
  <link rel="stylesheet" href="../css/relatorio_style.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <script>
    function alternarRelatorio() {
      const tipo = document.getElementById("tipoRelatorio").value;
      document.getElementById("formServico").style.display = tipo === "servico" ? "block" : "none";
      document.getElementById("formOrcamento").style.display = tipo === "orcamento" ? "block" : "none";
    }
  </script>
</head>
<body>
  <header>
    <h1>📄 Gerar Relatórios</h1>
  </header>

  <main>
    <section class="formulario">
      <h2><i class="fas fa-filter"></i> Selecione o Tipo de Relatório</h2>
      <div class="select-group">
        <select id="tipoRelatorio" onchange="alternarRelatorio()">
          <option value="">-- Escolha uma opção --</option>
          <option value="servico">🛠 Relatório de Serviço</option>
          <option value="orcamento">💰 Relatório de Orçamento</option>
        </select>
      </div>

      <form id="formServico" method="get" action="relatorioServico.jsp" style="display:none;">
        <input type="number" name="idServico" placeholder="Digite o ID do Serviço" required />
        <button type="submit"><i class="fas fa-file-alt"></i> Gerar Relatório</button>
      </form>

      <form id="formOrcamento" method="get" action="relatorioOrcamento.jsp" style="display:none;">
        <input type="number" name="idOrcamento" placeholder="Digite o ID do Orçamento" required />
        <button type="submit"><i class="fas fa-file-invoice-dollar"></i> Gerar Relatório</button>
      </form>
    </section>
  </main>

  <footer>
    <p>&copy; 2025 - Sistema de Frota</p>
  </footer>
  <script>
  document.addEventListener('DOMContentLoaded', function () {
    const select = document.getElementById('tipoRelatorio');

    select.addEventListener('focus', function () {
      this.style.borderColor = '#4a148c';
      this.style.boxShadow = '0 0 5px rgba(74, 20, 140, 0.5)';
    });

    select.addEventListener('blur', function () {
      this.style.borderColor = '#ccc';
      this.style.boxShadow = 'none';
    });
  });
</script>
</body>
</html>
