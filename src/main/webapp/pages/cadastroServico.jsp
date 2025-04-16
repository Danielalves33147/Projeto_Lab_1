<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="utils.Conexao" %>
<%@ page import="br.com.projeto.dao.TransportadorDao, br.com.projeto.dao.CaminhaoDao, br.com.projeto.dao.OrcamentoDao, br.com.projeto.dao.MotoristaDao, br.com.projeto.dao.MotoristaHasCaminhaoDao" %>
<%@ page import="br.com.projeto.model.Transportador, br.com.projeto.model.Caminhao, br.com.projeto.model.Orcamento, br.com.projeto.model.Motorista" %>
<%@ page import="java.util.*" %>

<%
    TransportadorDao transportadorDao = new TransportadorDao(Conexao.getConnection());
    CaminhaoDao caminhaoDao = new CaminhaoDao(Conexao.getConnection());
    OrcamentoDao orcamentoDao = new OrcamentoDao(Conexao.getConnection());
    MotoristaDao motoristaDao = new MotoristaDao(Conexao.getConnection());
    MotoristaHasCaminhaoDao vinculoDao = new MotoristaHasCaminhaoDao(Conexao.getConnection());

    List<Transportador> transportadores = transportadorDao.listarTodos();
    List<Caminhao> caminhoes = caminhaoDao.listarTodos();
    List<Orcamento> orcamentos = orcamentoDao.listarTodos();
    List<Motorista> todosMotoristas = motoristaDao.listarTodos();

    Map<String, List<Motorista>> mapaMotoristasPorCaminhao = new HashMap<>();
    for (Caminhao c : caminhoes) {
        List<String> cpfsAutorizados = vinculoDao.listarMotoristasPorCaminhao(c.getPlaca());
        List<Motorista> autorizados = new ArrayList<>();
        for (Motorista m : todosMotoristas) {
            if (cpfsAutorizados.contains(m.getCpf())) {
                autorizados.add(m);
            }
        }
        mapaMotoristasPorCaminhao.put(c.getPlaca(), autorizados);
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cadastro de Serviço - Sistema de Frota</title>
  <link rel="stylesheet" href="../css/servico_style.css">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.js"></script>
</head>
<body>
  <header>
    <h1>Cadastro de Serviço</h1>
  </header>

  <main class="cadastro-container">
  <form action="../servico" method="post" class="formulario-servico">
    <input type="hidden" name="acao" value="cadastrar">

    <div class="form-grupo">
      <label for="tipoServico">Tipo de Serviço</label>
      <select name="tipoServico" id="tipoServico" required>
        <option value="">Selecione</option>
        <option value="Transporte">Transporte</option>
        <option value="Entrega">Entrega</option>
        <option value="Montagem">Montagem</option>
        <option value="Diaria Interna">Diária Interna</option>
      </select>
    </div>

    <div class="form-grupo">
      <label for="idTransportador">Transportador</label>
      <select name="idTransportador" id="idTransportador" required>
        <option value="">Selecione</option>
        <% for (Transportador t : transportadores) { %>
          <option value="<%= t.getCpfCnpj() %>"><%= t.getNomeTransportador() %> - <%= t.getCpfCnpj() %></option>
        <% } %>
      </select>
    </div>

    <div class="form-grupo">
      <label for="caminhaoSelect">Caminhão</label>
      <select id="caminhaoSelect" name="caminhaoPlaca" required>
        <option value="">Selecione</option>
        <% for (Caminhao c : caminhoes) { %>
          <option value="<%= c.getPlaca() %>"><%= c.getPlaca() %> - <%= c.getModelo() %></option>
        <% } %>
      </select>
    </div>

    <div class="form-grupo">
      <label for="motoristaSelect">Motorista</label>
      <select id="motoristaSelect" name="motoristaCpf" required>
        <option value="">Selecione um caminhão primeiro</option>
      </select>
    </div>

    <div class="form-grupo">
      <label for="idOrcamento">Orçamento (Opcional)</label>
      <select name="idOrcamento" id="idOrcamento">
        <option value="">-- Não Vincular --</option>
        <% for (Orcamento o : orcamentos) { %>
          <option value="<%= o.getIdOrcamento() %>">#<%= o.getIdOrcamento() %> - <%= o.getDescricao() %> (R$ <%= o.getValorTotal() %>)</option>
        <% } %>
      </select>
    </div>

    <div class="linha">
      <div class="form-grupo">
        <label for="dataInicio">Data de Início</label>
        <input type="date" name="dataInicio" id="dataInicio" required>
      </div>

      <div class="form-grupo">
        <label for="dataTermino">Data de Término</label>
        <input type="date" name="dataTermino" id="dataTermino" required>
      </div>
    </div>

    <div class="form-grupo">
      <label for="valorTotal">Valor Total</label>
      <input type="text" name="valorTotal" id="valorTotal" required>
    </div>

    <div class="form-grupo">
      <label for="descricaoServico">Descrição do Serviço</label>
      <textarea name="descricaoServico" id="descricaoServico" rows="5" required></textarea>
    </div>

    <button type="submit">Cadastrar Serviço</button>
  </form>
</main>


  <footer>
    <p>&copy; 2025 - Sistema de Frota</p>
  </footer>

  <script>
    const mapaMotoristas = {
      <% 
        Iterator<Map.Entry<String, List<Motorista>>> it = mapaMotoristasPorCaminhao.entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry<String, List<Motorista>> entry = it.next();
          String placa = entry.getKey();
          List<Motorista> motoristas = entry.getValue();
      %>
        "<%= placa %>": [
          <% for (int i = 0; i < motoristas.size(); i++) {
               Motorista m = motoristas.get(i);
          %>
            { cpf: "<%= m.getCpf() %>", nomeMotorista: "<%= m.getNomeMotorista() %>" }<%= (i < motoristas.size() - 1) ? "," : "" %>
          <% } %>
        ]<%= it.hasNext() ? "," : "" %>
      <% } %>
    };

    $('#valorTotal').mask('000.000,00', { reverse: true });

    $('#caminhaoSelect').change(function () {
      const placa = $(this).val();
      const selectMotorista = $('#motoristaSelect');
      selectMotorista.empty();
      selectMotorista.append('<option value="">Selecione</option>');

      if (placa && mapaMotoristas[placa]) {
        mapaMotoristas[placa].forEach(function (motorista) {
          selectMotorista.append('<option value="' + motorista.cpf + '">' + motorista.nomeMotorista + ' - ' + motorista.cpf + '</option>');
        });
      }
    });
  </script>
</body>
</html>
