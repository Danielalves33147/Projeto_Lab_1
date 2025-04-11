<%@ page import="br.com.projeto.dao.ServicoDao, br.com.projeto.model.Servico" %>
<%@ page import="utils.Conexao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    String idServicoStr = request.getParameter("idServico");
    Servico servico = null;

    if (idServicoStr != null && !idServicoStr.trim().isEmpty()) {
        try {
            int idServico = Integer.parseInt(idServicoStr);
            ServicoDao sdao = new ServicoDao(Conexao.getConnection());
            servico = sdao.buscarPorId(idServico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<link rel="stylesheet" href="../css/relatorioServico_style.css">

    <meta charset="UTF-8">
    <title>Relatório de Serviço</title>
</head>

<body>
    <div class="container">
        <header>
            <h1>Relatório de Serviço</h1>
            <p>Sistema de Frota - Documento Oficial</p>
        </header>

        <% if (servico == null) { %>
            <p style="color: red;">Serviço não encontrado ou ID inválido.</p>
            <a href="gerarRelatorio.jsp" class="back-link">&larr; Voltar</a>
        <% } else { %>
            <div class="section">
                <h2>Informações Gerais</h2>
                <div class="details">
                    <div class="field">
                        <label>ID do Serviço</label>
                        <p><%= servico.getIdServico() %></p>
                    </div>
                    <div class="field">
                        <label>Tipo de Serviço</label>
                        <p><%= servico.getTipoServico() %></p>
                    </div>
                    <div class="field">
                        <label>Motorista (CPF)</label>
                        <p><%= servico.getMotoristaCpf() %></p>
                    </div>
                    <div class="field">
                        <label>Caminhão Utilizado</label>
                        <p><%= servico.getCaminhaoUtilizado() %></p>
                    </div>
                </div>
            </div>

            <div class="section">
                <h2>Período</h2>
                <div class="details">
                    <div class="field">
                        <label>Data de Início</label>
                        <p><%= servico.getDataInicio() %></p>
                    </div>
                    <div class="field">
                        <label>Data de Término</label>
                        <p><%= servico.getDataTermino() %></p>
                    </div>
                </div>
            </div>

            <div class="section">
                <h2>Financeiro</h2>
                <div class="details">
                    <div class="field">
                        <label>Valor Total</label>
                        <p>R$ <%= servico.getValorTotal() %></p>
                    </div>
                </div>
            </div>

            <div class="section">
                <h2>Descrição</h2>
                <div class="details">
                    <div class="field" style="flex: 1 1 100%;">
                        <p><%= servico.getDescricaoServico() %></p>
                    </div>
                </div>
            </div>

            <a href="gerarRelatorio.jsp" class="back-link">&larr; Voltar</a>
        <% } %>

        <footer>
            <p>&copy; 2025 - Sistema de Frota</p>
        </footer>
    </div>
</body>
</html>
