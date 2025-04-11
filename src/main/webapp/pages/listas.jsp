<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, java.util.HashMap" %>
<%@ page import="br.com.projeto.model.*" %>
<%@ page import="br.com.projeto.dao.*" %>
<%@ page import="utils.Conexao" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<%
    // Recupera os dados via DAO
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
    
    // Cria um mapa para associar idOrcamento -> Orcamento, para busca rápida na listagem de serviço.
    Map<Integer, Orcamento> orcMap = new HashMap<>();
    for(Orcamento o : orcamentos) {
        orcMap.put(o.getIdOrcamento(), o);
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Listagens - Sistema de Frota</title>
  <link rel="stylesheet" href="../css/listas_style.css">
<script>
  function alternarTabela() {
    const tipo = document.getElementById("tipoLista").value;
    // Se "todos" ou vazio, exibe todas as tabelas e seus títulos
    if (tipo === "todos" || tipo === "") {
      document.querySelectorAll(".tabela-dados").forEach(t => t.style.display = "table");
      document.querySelectorAll(".tabela-titulo").forEach(titulo => titulo.style.display = "block");
    } else {
      // Esconde todas as tabelas e títulos
      document.querySelectorAll(".tabela-dados").forEach(t => t.style.display = "none");
      document.querySelectorAll(".tabela-titulo").forEach(titulo => titulo.style.display = "none");
      // Exibe a tabela e o título do grupo selecionado
      const tabelaSelecionada = document.getElementById("tabela-" + tipo);
      const tituloSelecionado = document.getElementById("titulo-" + tipo);
      if (tabelaSelecionada) tabelaSelecionada.style.display = "table";
      if (tituloSelecionado) tituloSelecionado.style.display = "block";
    }
  }

  function filtrarTabela() {
    const termo = document.getElementById("campoBusca").value.toLowerCase();
    const tipo = document.getElementById("tipoLista").value;
    const criterio = document.getElementById("criterioBusca").value; // critério escolhido, ex.: "id", "nome", "placa", etc.
    
    console.log("Filtrando com termo: " + termo + " e critério: " + criterio);
    
    if (tipo === "todos" || tipo === "") {
      // Se não há grupo específico, filtra todas as tabelas
      document.querySelectorAll(".tabela-dados").forEach(function(tabela) {
        filtrarTabelaPorTabela(tabela, termo, criterio);
      });
    } else {
      const tabela = document.getElementById("tabela-" + tipo);
      filtrarTabelaPorTabela(tabela, termo, criterio);
    }
  }
    
  function filtrarTabelaPorTabela(tabela, termo, criterio) {
    if (!tabela) return;
    const headers = tabela.querySelectorAll("thead th");
    let colIndex = -1;
    headers.forEach(function(th, index) {
      // Compara o data-field do cabeçalho com o critério (usando lowercase para evitar diferenciação de maiúsculas/minúsculas)
      if (th.getAttribute("data-field") && th.getAttribute("data-field").toLowerCase() === criterio.toLowerCase()) {
        colIndex = index;
      }
    });
    
    if(colIndex === -1){
      // Se nenhum cabeçalho corresponde, opcionalmente pode-se aplicar o filtro a toda a linha
      console.log("Critério '" + criterio + "' não encontrado nos cabeçalhos. Aplicando filtro global na linha.");
    }
    
    const linhas = tabela.querySelectorAll("tbody tr");
    linhas.forEach(function(linha) {
      if (colIndex >= 0) {
        const celulas = linha.querySelectorAll("td");
        if (celulas.length > colIndex) {
          const texto = celulas[colIndex].innerText.toLowerCase();
          linha.style.display = texto.includes(termo) ? "" : "none";
        }
      } else {
        // Se o critério não foi encontrado em nenhum cabeçalho, filtra por toda a linha
        const textoLinha = linha.innerText.toLowerCase();
        linha.style.display = textoLinha.includes(termo) ? "" : "none";
      }
    });
  }
</script>

</head>
<body>
  <header>
    <h1>Listagens do Sistema</h1>
    <nav><ul></ul></nav>
  </header>

  <main class="container">
    <!-- Seção de Filtros -->
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
    
    <!-- Título Global Dinâmico (para quando filtrando todos, opcional) -->
    <!--<h3 id="titulo-global" style="display: none;"></h3>-->
    
    <!-- Seção de Resultados -->
    <section class="resultados">
      <!-- Cada tabela terá seu título fixo -->
      
      <!-- Tabela de Caminhão -->
      <h3 class="tabela-titulo" id="titulo-caminhao" style="display: none;">Caminhão</h3>
      <table id="tabela-caminhao" class="tabela-dados" style="display: none;">
        <thead>
          <tr>
            <th data-field="id">ID</th>
            <th data-field="modelo">Modelo</th>
            <th data-field="placa">Placa</th>
            <th data-field="ano">Ano</th>
            <th data-field="capacidade">Capacidade</th>
          </tr>
        </thead>
        <tbody>
          <% for (Caminhao c : caminhoes) { %>
            <tr>
              <td><%= c.getPlaca() %></td>
              <td><%= c.getModelo() %></td>
              <td><%= c.getPlaca() %></td>
              <td><%= c.getAnoFabricacao() %></td>
              <td><%= c.getCapacidadeMax() %></td>
            </tr>
          <% } %>
        </tbody>
      </table>

      <!-- Tabela de Motorista -->
      <h3 class="tabela-titulo" id="titulo-motorista" style="display: none;">Motorista</h3>
      <table id="tabela-motorista" class="tabela-dados" style="display: none;">
        <thead>
          <tr>
            <th data-field="id">ID</th>
            <th data-field="nome">Nome</th>
            <th data-field="cpf">CPF</th>
            <th data-field="cnh">CNH</th>
          </tr>
        </thead>
        <tbody>
          <% for (Motorista m : motoristas) { %>
            <tr>
              <td><%= m.getCpf() %></td>
              <td><%= m.getNomeMotorista() %></td>
              <td><%= m.getCpf() %></td>
              <td><%= m.getCnh() %></td>
            </tr>
          <% } %>
        </tbody>
      </table>

      <!-- Tabela de Transportador -->
      <h3 class="tabela-titulo" id="titulo-transportador" style="display: none;">Transportador</h3>
      <table id="tabela-transportador" class="tabela-dados" style="display: none;">
        <thead>
          <tr>
            <th data-field="id">ID</th>
            <th data-field="nome">Nome</th>
            <th data-field="cpf">CPF/CNPJ</th>
            <th data-field="telefone">Telefone</th>
          </tr>
        </thead>
        <tbody>
          <% for (Transportador t : transportadores) { %>
            <tr>
              <td><%= t.getCpfCnpj() %></td>
              <td><%= t.getNomeTransportador() %></td>
              <td><%= t.getCpfCnpj() %></td>
              <td><%= t.getTelefone() %></td>
            </tr>
          <% } %>
        </tbody>
      </table>

      <!-- Tabela de Serviço -->
      <h3 class="tabela-titulo" id="titulo-servico" style="display: none;">Serviço</h3>
      <table id="tabela-servico" class="tabela-dados" style="display: none;">
        <thead>
          <tr>
            <th data-field="id">ID</th>
            <th data-field="data">Data</th>
            <th data-field="orcamento">Orçamento</th>
            <th data-field="descricao">Descrição</th>
          </tr>
        </thead>
        <tbody>
          <% for (Servico s : servicos) { 
               // Se dataExecucao estiver nula, usamos dataInicio para exibição
               String dataExibida = (s.getDataExecucao() != null ? s.getDataExecucao().toString() : s.getDataInicio().toString());
               // Se houver orçamento vinculado, buscamos no mapa; caso contrário, "N/A"
               String orcDescricao = "N/A";
               if(s.getIdOrcamento() != 0) {
                 Orcamento o = orcMap.get(s.getIdOrcamento());
                 if(o != null) {
                   orcDescricao = o.getDescricao(); // ou outro campo que deseje exibir
                 }
               }
          %>
            <tr>
              <td><%= s.getIdServico() %></td>
              <td><%= dataExibida %></td>
              <td><%= orcDescricao %></td>
              <td><%= s.getDescricaoServico() %></td>
            </tr>
          <% } %>
        </tbody>
      </table>

      <!-- Tabela de Orçamento -->
      <h3 class="tabela-titulo" id="titulo-orcamento" style="display: none;">Orçamento</h3>
      <table id="tabela-orcamento" class="tabela-dados" style="display: none;">
        <thead>
          <tr>
            <th data-field="id">ID</th>
            <th data-field="data">Data</th>
            <th data-field="valor">Valor</th>
            <th data-field="tipo">Tipo</th>
          </tr>
        </thead>
        <tbody>
          <% for (Orcamento o : orcamentos) { %>
            <tr>
              <td><%= o.getIdOrcamento() %></td>
              <td><%= o.getDataEmissao() %></td>
              <td><%= o.getValorTotal() %></td>
              <td><%= o.getTipoServico() %></td>
            </tr>
          <% } %>
        </tbody>
      </table>
    </section>
  </main>

  <footer>
    <p>&copy; 2025 - Sistema de Frota</p>
  </footer>
</body>
</html>
