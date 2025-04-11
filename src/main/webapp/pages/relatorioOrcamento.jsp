<%@ page import="br.com.projeto.dao.OrcamentoDao, br.com.projeto.model.Orcamento" %>
<%@ page import="utils.Conexao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    String idOrcamentoStr = request.getParameter("idOrcamento");
    Orcamento orcamento = null;

    if (idOrcamentoStr != null && !idOrcamentoStr.trim().isEmpty()) {
        try {
            int idOrcamento = Integer.parseInt(idOrcamentoStr);
            OrcamentoDao odao = new OrcamentoDao(Conexao.getConnection());
            orcamento = odao.buscarPorId(idOrcamento);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Relatório de Orçamento</title>
    <link rel="stylesheet" href="../css/relatorioServico_style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Relatório de Orçamento</h1>
            <p>Sistema de Frota - Documento Oficial</p>
        </header>

        <% if (orcamento == null) { %>
            <p style="color: red;">Orçamento não encontrado ou ID inválido.</p>
            <a href="gerarRelatorio.jsp" class="back-link">&larr; Voltar</a>
        <% } else { %>
            <div class="section">
                <h2>Informações Gerais</h2>
                <div class="details">
                    <div class="field">
                        <label>ID do Orçamento</label>
                        <p><%= orcamento.getIdOrcamento() %></p>
                    </div>
                    <div class="field">
                        <label>Descrição</label>
                        <p><%= orcamento.getDescricao() %></p>
                    </div>
                    <div class="field">
                        <label>Tipo de Serviço</label>
                        <p><%= orcamento.getTipoServico() %></p>
                    </div>
                    <div class="field">
                        <label>Data de Emissão</label>
                        <p><%= orcamento.getDataEmissao() %></p>
                    </div>
                </div>
            </div>

            <div class="section">
                <h2>Financeiro</h2>
                <div class="details">
                    <div class="field">
                        <label>Valor Diária</label>
                        <p>R$ <%= orcamento.getValorDiaria() %></p>
                    </div>
                    <div class="field">
                        <label>Valor Total</label>
                        <p>R$ <%= orcamento.getValorTotal() %></p>
                    </div>
                </div>
            </div>

            <div class="section">
                <h2>Detalhes</h2>
                <div class="details">
                    <div class="field" style="flex: 1 1 100%;">
                        <p><%= orcamento.getDetalhes() %></p>
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
