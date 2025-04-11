<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.projeto.model.Motorista, br.com.projeto.model.Caminhao, br.com.projeto.model.Servico, br.com.projeto.model.Transportador" %>
<%@ page import="br.com.projeto.dao.MotoristaDao, br.com.projeto.dao.CaminhaoDao, br.com.projeto.dao.ServicoDao, br.com.projeto.dao.TransportadorDao" %>
<%@ page import="utils.Conexao" %>
<%@ page import="java.time.LocalDate" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<%
    // Conectar e obter os registros
    MotoristaDao motoristaDao = new MotoristaDao(Conexao.getConnection());
    CaminhaoDao caminhaoDao = new CaminhaoDao(Conexao.getConnection());
    ServicoDao servicoDao = new ServicoDao(Conexao.getConnection());
    TransportadorDao transportadorDao = new TransportadorDao(Conexao.getConnection());

    List<Motorista> motoristas = motoristaDao.listarTodos();
    List<Caminhao> caminhoes = caminhaoDao.listarTodos();
    List<Servico> servicos = servicoDao.listarTodos();
    // transportadores não são usados na visão geral, mas podem ser úteis

    // Cálculo das quantidades:
    int qtdMotoristas = (motoristas != null) ? motoristas.size() : 0;
    int qtdCaminhoes = (caminhoes != null) ? caminhoes.size() : 0;

    // Definição de "Serviços em Aberto"
    // Para essa lógica, consideraremos "serviço aberto" aquele cuja DataTermino é maior ou igual à data atual.
    LocalDate hoje = LocalDate.now();
    int servicosAbertos = 0;
    if(servicos != null) {
        for (Servico s : servicos) {
            if(s.getDataTermino() != null &&
               (s.getDataTermino().isAfter(hoje) || s.getDataTermino().isEqual(hoje))) {
                servicosAbertos++;
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Sistema de Frota - Início</title>
  <link rel="stylesheet" href="css/index_style.css">
</head>
<body>
  <header>
    <h1>Sistema de Frota</h1>
    <nav><ul></ul></nav>
  </header>

  <main class="container">
    <h2 class="titulo">Painel de Acesso</h2>
    <p class="subtitulo">Gerencie com eficiência sua frota de caminhões e serviços.</p>

    <section class="container-flex">
      <!-- DASHBOARD - VISÃO GERAL -->
      <div class="dashboard">
        <h2 class="titulo-dashboard">Visão Geral</h2>
        <div class="dashboard-cards">
          <div class="card-dado">
            <h3>Serviços em Aberto</h3>
            <p class="valor-dado" id="servicosAbertos"><%= servicosAbertos %></p>
          </div>
          <div class="card-dado">
            <h3>Motoristas</h3>
            <p class="valor-dado" id="qtdMotoristas"><%= qtdMotoristas %></p>
          </div>
          <div class="card-dado">
            <h3>Caminhões</h3>
            <p class="valor-dado" id="qtdCaminhoes"><%= qtdCaminhoes %></p>
          </div>
        </div>
      </div>

      <!-- OPÇÕES -->
      <div class="grid-opcoes">
        <a href="pages/cadastroUsuario.jsp" class="opcao">
          <div class="icon">🧾</div>
          <h3>Cadastro</h3>
          <p>Motoristas e Transportadores</p>
        </a>
        <a href="pages/cadastroCaminhao.jsp" class="opcao">
          <div class="icone">🚛</div>
          <h3>Caminhão</h3>
          <p>Cadastro de veículos</p>
        </a>
        <a href="pages/cadastroServico.jsp" class="opcao">
          <div class="icone">🛠️</div>
          <h3>Serviço</h3>
          <p>Montagem, entrega e afins</p>
        </a>
        <a href="pages/cadastroOrcamento.jsp" class="opcao">
          <div class="icone">💰</div>
          <h3>Orçamento</h3>
          <p>Gerar e registrar estimativas</p>
        </a>
        <a href="pages/gerarRelatorio.jsp" class="opcao">
          <div class="icone">📊</div>
          <h3>Relatório</h3>
          <p>Consultar dados e históricos</p>
        </a>
        <a href="pages/listas.jsp" class="opcao">
          <div class="icone">📋</div>
          <h3>Listagens</h3>
          <p>Caminhões, usuários e serviços</p>
        </a>
      </div>
    </section>

    <div class="painel-mensagem">
      <p><strong>📌 Dica do Dia:</strong> mantenha os dados de motoristas e caminhões sempre atualizados para evitar falhas nos serviços!</p>
    </div>
  </main>

  <footer>
    <p>&copy; 2025 - Sistema de Frota</p>
  </footer>
</body>
</html>
