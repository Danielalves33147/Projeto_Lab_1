<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, java.util.HashMap" %>
<%@ page import="br.com.projeto.model.*" %>
<%@ page import="br.com.projeto.dao.*" %>
<%@ page import="utils.Conexao" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    MotoristaDao motoristaDao = new MotoristaDao(Conexao.getConnection());
    CaminhaoDao caminhaoDao = new CaminhaoDao(Conexao.getConnection());
    TransportadorDao transportadorDao = new TransportadorDao(Conexao.getConnection());
    ServicoDao servicoDao = new ServicoDao(Conexao.getConnection());
    OrcamentoDao orcamentoDao = new OrcamentoDao(Conexao.getConnection());

    List<Motorista> motoristas = motoristaDao.listarTodos();
    List<Caminhao> caminhoes = caminhaoDao.listarTodos();
    List<Transportador> transportadores = transportadorDao.listarTodos();
    List<Servico> servicos = servicoDao.listarTodos();
    List<Orcamento> orcamentos = orcamentoDao.listarTodos();

    Map<Integer, Orcamento> orcMap = new HashMap<>();
    for (Orcamento o : orcamentos) {
        orcMap.put(o.getIdOrcamento(), o);
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <title>Listagens - Sistema de Frota</title>
    <link rel="stylesheet" href="../css/listas_style.css" />
    
    
    <script>
        function alternarTabela() {
            const tipo = document.getElementById("tipoLista").value;
            document.querySelectorAll(".tabela-dados, .tabela-titulo").forEach(el => el.style.display = "none");
            if (tipo === "todos" || tipo === "") {
                document.querySelectorAll(".tabela-dados, .tabela-titulo").forEach(el => el.style.display = "");
            } else {
                document.getElementById("tabela-" + tipo).style.display = "table";
                document.getElementById("titulo-" + tipo).style.display = "block";
            }
        }

        function filtrarTabela() {
            const termo = document.getElementById("campoBusca").value.toLowerCase();
            const tipo = document.getElementById("tipoLista").value;
            const criterio = document.getElementById("criterioBusca").value;

            const aplicarFiltro = tabela => {
                const headers = tabela.querySelectorAll("thead th");
                let colIndex = -1;
                headers.forEach((th, i) => {
                    if ((th.dataset.field || "").toLowerCase() === criterio.toLowerCase()) colIndex = i;
                });

                const linhas = tabela.querySelectorAll("tbody tr");
                linhas.forEach(linha => {
                    const celulas = linha.querySelectorAll("td");
                    const texto = colIndex >= 0 ? celulas[colIndex]?.innerText.toLowerCase() : linha.innerText.toLowerCase();
                    linha.style.display = texto.includes(termo) ? "" : "none";
                });
            };

            if (tipo === "todos" || tipo === "") {
                document.querySelectorAll(".tabela-dados").forEach(aplicarFiltro);
            } else {
                const tabela = document.getElementById("tabela-" + tipo);
                if (tabela) aplicarFiltro(tabela);
            }
        }

        function confirmarDelecao(entidade, mensagemExtra) {
            return confirm(`⚠️ Se você deletar este ${entidade}, ${mensagemExtra}\nDeseja continuar?`);
        }
    </script>
</head>
<body>
<header><h1>Listagens do Sistema</h1></header>
<main class="container">
<section class="formulario">
    <h2>Consultar Dados</h2>
    <div class="linha-filtros">
        <div class="filtro-box">
            <label for="tipoLista">Filtrar por Grupo:</label>
            <select id="tipoLista" onchange="alternarTabela()">
                <option value="">Selecione</option>
                <option value="todos">Todos</option>
                <option value="caminhao">Caminhão</option>
                <option value="motorista">Motorista</option>
                <option value="transportador">Transportador</option>
                <option value="servico">Serviço</option>
                <option value="orcamento">Orçamento</option>
            </select>
        </div>
        <div class="filtro-box">
            <label for="criterioBusca">Buscar por:</label>
            <select id="criterioBusca">
                <option value="id">ID</option>
                <option value="nome">Nome</option>
                <option value="placa">Placa</option>
                <option value="data">Data</option>
                <option value="valor">Valor</option>
                <option value="modelo">Modelo</option>
                <option value="cnh">CNH</option>
                <option value="telefone">Telefone</option>
                <option value="tipo">Tipo</option>
                <option value="capacidade">Capacidade</option>
                <option value="descricao">Descrição</option>
            </select>
        </div>
    </div>
    <div class="campo-busca">
        <input id="campoBusca" type="text" placeholder="Digite o valor para busca..." />
        <button type="button" onclick="filtrarTabela()">🔍</button>
    </div>
</section>

<!-- Tabelas -->
<section class="resultados">

<!-- Transportador -->
<h3 class="tabela-titulo" id="titulo-transportador">Transportador</h3>
<table id="tabela-transportador" class="tabela-dados">
<thead><tr>
    <th>CPF/CNPJ</th><th>Nome</th><th>Telefone</th><th>Ações</th>
</tr></thead>
<tbody>
<% for (Transportador t : transportadores) { %>
<tr>
    <td><%= t.getCpfCnpj() %></td>
    <td><%= t.getNomeTransportador() %></td>
    <td><%= t.getTelefone() %></td>
    <td>
        <a href="${pageContext.request.contextPath}/transportador?acao=editar&cpfCnpj=<%= t.getCpfCnpj() %>">Editar</a> |
        <a href="${pageContext.request.contextPath}/transportador?acao=deletar&cpfCnpj=<%= t.getCpfCnpj() %>"
           onclick="return confirm('⚠️ Se você deletar este **transportador**, TODOS os caminhões, motoristas e serviços relacionados a ele também serão deletados. Deseja continuar?')"
>Deletar</a>
    </td>
</tr>
<% } %>
</tbody>
</table>

<!-- Motorista -->
<h3 class="tabela-titulo" id="titulo-motorista">Motorista</h3>
<table id="tabela-motorista" class="tabela-dados">
<thead><tr>
    <th>CPF</th><th>Nome</th><th>CNH</th><th>Ações</th>
</tr></thead>
<tbody>
<% for (Motorista m : motoristas) { %>
<tr>
    <td><%= m.getCpf() %></td>
    <td><%= m.getNomeMotorista() %></td>
    <td><%= m.getCnh() %></td>
    <td>
		<a href="${pageContext.request.contextPath}/pages/edits/editarMotorista.jsp?cpf=<%= m.getCpf() %>">Editar</a>
        <a href="${pageContext.request.contextPath}/motorista?acao=deletar&cpf=<%= m.getCpf() %>"
           onclick="return confirm('⚠️ Se você deletar este **motorista**, os vínculos com caminhões e os registros antigos de serviços relacionados a ele também serão removidos. Deseja continuar?')"
>Deletar</a>
    </td>
</tr>
<% } %>

<script>
  window.onload = function () {
    const params = new URLSearchParams(window.location.search);
    
    if (params.get("motoristaAtualizado") === "true") {
      const vinculoAlterado = params.get("vinculoAlterado") === "true";

      if (vinculoAlterado) {
        alert("Motorista atualizado com sucesso!\nO vínculo com o caminhão foi alterado.");
      } else {
        alert("Motorista atualizado com sucesso!\nO vínculo anterior já estava vinculado a serviços e não pôde ser alterado.");
      }

      // Limpar os parâmetros da URL para evitar re-alerta
      window.history.replaceState({}, document.title, window.location.pathname);
    }
  };
</script>

</tbody>
</table>

<!-- Caminhão -->
<h3 class="tabela-titulo" id="titulo-caminhao">Caminhão</h3>
<table id="tabela-caminhao" class="tabela-dados">
<thead><tr>
    <th>Placa</th><th>Modelo</th><th>Tipo</th><th>Capacidade</th><th>Ações</th>
</tr></thead>
<tbody>
<% for (Caminhao c : caminhoes) { %>
<tr>
    <td><%= c.getPlaca() %></td>
    <td><%= c.getModelo() %></td>
    <td><%= c.getTipo() %></td>
    <td><%= c.getCapacidadeMax() %> kg</td>
    <td>
        <a href="${pageContext.request.contextPath}/caminhao?acao=editar&placa=<%= c.getPlaca() %>">Editar</a> |
        <a href="${pageContext.request.contextPath}/caminhao?acao=deletar&placa=<%= c.getPlaca() %>"
           onclick="return confirm('⚠️ Se você deletar este **caminhão**, os vínculos com motoristas e registros de serviços anteriores também serão apagados. Deseja continuar?')"
>Deletar</a>
    </td>
</tr>
<% } %>
</tbody>
</table>

<!-- Serviço -->
<h3 class="tabela-titulo" id="titulo-servico">Serviço</h3>
<table id="tabela-servico" class="tabela-dados">
<thead><tr>
    <th>ID</th><th>Data Início</th><th>Data Término</th><th>Valor</th><th>Descrição</th><th>Ações</th>
</tr></thead>
<tbody>
<% for (Servico s : servicos) {
     String valor = (s.getIdOrcamento() != null && orcMap.containsKey(s.getIdOrcamento()))
                    ? "R$ " + orcMap.get(s.getIdOrcamento()).getValorTotal()
                    : "R$ " + s.getValorTotal();
%>
<tr>
    <td><%= s.getIdServico() %></td>
    <td><%= s.getDataInicio() %></td>
    <td><%= s.getDataTermino() %></td>
    <td><%= valor %></td>
    <td><%= s.getDescricaoServico() %></td>
    <td>
        <a href="${pageContext.request.contextPath}/servico?acao=editar&id=<%= s.getIdServico() %>">Editar</a> |
        <a href="${pageContext.request.contextPath}/servico?acao=deletar&id=<%= s.getIdServico() %>"
           onclick="return confirm('⚠️ Se você deletar este **serviço**, todos os dados vinculados a ele também serão excluídos. Deseja continuar?')"
>Deletar</a>
    </td>
</tr>
<% } %>
</tbody>
</table>

<!-- Orçamento -->
<h3 class="tabela-titulo" id="titulo-orcamento">Orçamento</h3>
<table id="tabela-orcamento" class="tabela-dados">
<thead><tr>
    <th>ID</th><th>Data</th><th>Tipo</th><th>Valor</th><th>Ações</th>
</tr></thead>
<tbody>
<% for (Orcamento o : orcamentos) { %>
<tr>
    <td><%= o.getIdOrcamento() %></td>
    <td><%= o.getDataEmissao() %></td>
    <td><%= o.getTipoServico() %></td>
    <td>R$ <%= o.getValorTotal() %></td>
    <td>
        <a href="${pageContext.request.contextPath}/orcamento?acao=editar&id=<%= o.getIdOrcamento() %>">Editar</a> |
        <a href="${pageContext.request.contextPath}/orcamento?acao=deletar&id=<%= o.getIdOrcamento() %>"
           onclick="return confirm('⚠️ Se você deletar este **orçamento**, e ele estiver vinculado a um serviço, isso pode gerar inconsistências. Deseja continuar?')"
>Deletar</a>
    </td>
</tr>
<% } %>
</tbody>
</table>

</section>
</main>
<footer><p>&copy; 2025 - Sistema de Frota</p></footer>
</body>
</html>
