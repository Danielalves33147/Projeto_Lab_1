<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.projeto.dao.TransportadorDao" %>
<%@ page import="br.com.projeto.model.Transportador" %>
<%@ page import="utils.Conexao" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    String cpfCnpj = request.getParameter("cpfCnpj");
    Transportador transportador = null;

    if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
        TransportadorDao dao = new TransportadorDao(Conexao.getConnection());
        transportador = dao.buscarPorId(cpfCnpj);
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Editar Transportador</title>
    <link rel="stylesheet" href="../css/cadastro_style.css">
</head>
<body>
    <header>
        <h1>✏️ Editar Transportador</h1>
    </header>

    <main class="cadastro-container">
        <% if (transportador != null) { %>
            <form class="formulario" method="post" action="<%= request.getContextPath() %>/transportador">
                <input type="hidden" name="acao" value="atualizar">
                <input type="hidden" name="cpfCnpj" value="<%= transportador.getCpfCnpj() %>">

                <label>CPF/CNPJ (não editável):</label>
				<input type="text" value="<%= transportador.getCpfCnpj() %>" readonly style="background-color:#f2f2f2;">

                <label>Nome:</label>
                <input type="text" name="nome" value="<%= transportador.getNomeTransportador() %>" required>

                <label>Telefone:</label>
                <input type="text" name="telefone" value="<%= transportador.getTelefone() %>" required>

                <label>Endereço:</label>
                <input type="text" name="endereco" value="<%= transportador.getEndereco() %>" required>

                <button type="submit">Salvar Alterações</button>
            </form>
        <% } else { %>
            <p>Transportador não encontrado.</p>
        <% } %>
    </main>

    <footer>
        <p>&copy; 2025 - Sistema de Frota</p>
    </footer>
</body>
</html>
