<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.projeto.model.*, br.com.projeto.dao.*, utils.Conexao" %>
<%@ page import="java.util.List" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    String cpf = request.getParameter("cpf");
    Motorista motorista = null;
    List<Caminhao> caminhoes = null;
    String caminhaoAtual = "";

    try {
        MotoristaDao motoristaDao = new MotoristaDao(Conexao.getConnection());
        motorista = motoristaDao.buscarPorId(cpf);

        CaminhaoDao caminhaoDao = new CaminhaoDao(Conexao.getConnection());
        caminhoes = caminhaoDao.listarTodos();

        MotoristaHasCaminhaoDao mhDao = new MotoristaHasCaminhaoDao(Conexao.getConnection());
        List<String> vinculados = mhDao.listarMotoristasPorCaminhao(null); // não usado
        List<MotoristaHasCaminhao> todosVinculos = mhDao.listarTodos();
        for (MotoristaHasCaminhao v : todosVinculos) {
            if (v.getMotoristaCpf().equals(cpf)) {
                caminhaoAtual = v.getCaminhaoPlaca();
                break;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <title>Editar Motorista</title>
  <link rel="stylesheet" href="../css/cadastro_style.css" />
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
</head>
<body>
<%
  String sucessoEdicao = request.getParameter("sucesso");
  String vinculo = request.getParameter("vinculo");

  if ("true".equals(sucessoEdicao)) {
    if ("true".equals(vinculo)) {
%>
<script>
  alert("Motorista atualizado com sucesso!\nO vínculo com o caminhão anterior foi substituído automaticamente.");
</script>
<%
    } else if ("false".equals(vinculo)) {
%>
<script>
  alert("Motorista atualizado com sucesso!\nO vínculo com o caminhão não foi alterado pois já foi utilizado em um serviço.");
</script>
<%
    }
  }
%>


  <header>
    <h1>Editar Motorista</h1>
  </header>

  <main class="cadastro-container">
    <section class="cadastro-esquerda">
		<form method="post" action="<%= request.getContextPath() %>/motorista">
        <input type="hidden" name="acao" value="atualizar">
        <input type="hidden" name="cpf" value="<%= motorista.getCpf() %>">
        
        <input type="text" name="nome" placeholder="Nome" value="<%= motorista.getNomeMotorista() %>" required>
        <input type="text" name="cnh" placeholder="CNH" value="<%= motorista.getCnh() %>" required>
        <input type="text" name="telefone" id="telefoneMotorista" placeholder="Telefone" value="<%= motorista.getTelefone() %>" required>

        <label for="caminhaoPlaca">Caminhão Vinculado:</label>
        <select name="caminhaoPlaca" id="caminhaoPlaca" required>
          <option value="">Selecione</option>
          <% for (Caminhao c : caminhoes) { %>
            <option value="<%= c.getPlaca() %>" <%= c.getPlaca().equals(caminhaoAtual) ? "selected" : "" %>>
              <%= c.getPlaca() %> - <%= c.getModelo() %>
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

  <script>
    $('#telefoneMotorista').mask('(00) 00000-0000');
  </script>
</body>
</html>
