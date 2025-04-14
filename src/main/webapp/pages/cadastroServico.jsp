<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="utils.Conexao" %>
<%@ page import="br.com.projeto.dao.MotoristaDao, br.com.projeto.dao.CaminhaoDao, br.com.projeto.dao.OrcamentoDao, br.com.projeto.dao.TransportadorDao" %>
<%@ page import="br.com.projeto.model.Motorista, br.com.projeto.model.Caminhao, br.com.projeto.model.Orcamento, br.com.projeto.model.Transportador" %>
<%@ page import="java.util.List" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<%
    MotoristaDao motoristaDao = new MotoristaDao(Conexao.getConnection());
    CaminhaoDao caminhaoDao = new CaminhaoDao(Conexao.getConnection());
    OrcamentoDao orcamentoDao = new OrcamentoDao(Conexao.getConnection());
    TransportadorDao transportadorDao = new TransportadorDao(Conexao.getConnection());

    List<Motorista> motoristas = motoristaDao.listarTodos();
    List<Caminhao> caminhoes = caminhaoDao.listarTodos();
    List<Orcamento> listaOrcamentos = orcamentoDao.listarTodos();
    List<Transportador> transportadores = transportadorDao.listarTodos(); // Pegando transportadores
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Cadastro de Serviço - Sistema de Frota</title>
  <link rel="stylesheet" href="../css/servico_style.css">

  <!-- Adicionando Select2 CSS -->
  <link href="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/css/select2.min.css" rel="stylesheet" />
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/select2.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.js"></script>
</head>
<body>
  <header>
    <h1>Cadastro de Serviço</h1>
    <nav><ul></ul></nav>
  </header>

  <main class="cadastro-container">
    <form action="../servico" method="post" class="formulario-servico">

      <!-- Campo Tipo de Serviço -->
      <div class="form-grupo">
        <label for="tipoServico">Tipo de Serviço</label>
        <select name="tipoServico" id="tipoServico" required>
          <option value="">Selecione o tipo de serviço</option>
          <option value="Transporte">Transporte</option>
          <option value="Entrega">Entrega</option>
          <option value="Manutenção">Manutenção</option>
          <option value="Montagem">Montagem</option>
          <option value="Diaria Interna">Diaria Interna</option>
        </select>
      </div>

      <!-- Iteração dos Transportadores -->
      <div class="form-grupo">
        <label for="idTransportador">Transportador</label>
        <select name="idTransportador" id="idTransportador" required>
            <option value="">Selecione um Transportador</option>
            <% for (Transportador t : transportadores) { %>
                <option value="<%= t.getCpfCnpj() %>"><%= t.getNomeTransportador() %></option>
            <% } %>
        </select>
      </div>

      <div class="linha">
        <div class="form-grupo">
            <label for="idMotorista">Motorista</label>
            <select name="idMotorista" id="idMotorista" required>
                <option value="">Selecione um Motorista</option>
                <% for (Motorista m : motoristas) { %>
                    <option value="<%= m.getCpf() %>"><%= m.getNomeMotorista() %></option>
                <% } %>
            </select>
        </div>

        <div class="form-grupo">
          <label for="idCaminhao">Caminhão</label>
          <select name="idCaminhao" id="idCaminhao" required>
            <option value="">Selecione um Caminhão</option>
            <% for (Caminhao c : caminhoes) { %>
              <option value="<%= c.getPlaca() %>"><%= c.getPlaca() %> - <%= c.getModelo() %></option>
            <% } %>
          </select>
        </div>
      </div>

      <!-- Campo opcional para selecionar um Orçamento -->
      <div class="form-grupo">
		    <label for="idOrcamento">Vincular Orçamento (opcional):</label>
		    <select name="idOrcamento" id="idOrcamento">
		        <option value="">-- Não Vincular --</option>
		        <% for (Orcamento o : listaOrcamentos) { %>
		            <option value="<%= o.getIdOrcamento() %>">
		                #<%= o.getIdOrcamento() %> - <%= o.getDescricao() %> (R$ <%= o.getValorTotal() %>)
		            </option>
		        <% } %>
		    </select>
		</div>

      <div class="form-grupo">
        <label for="dataInicio">Data de Início</label>
        <input type="date" name="dataInicio" id="dataInicio" required>
      </div>

      <div class="form-grupo">
        <label for="dataTermino">Data de Término</label>
        <input type="date" name="dataTermino" id="dataTermino" required>
      </div>

      <!-- Campo Valor Total com Máscara -->
      <div class="form-grupo">
        <label for="valorTotal">Valor Total:</label>
        <input type="text" id="valorTotal" name="valorTotal" required />
      </div>

      <div class="form-grupo">
        <label for="descricaoServico">Descrição do Serviço</label>
        <textarea name="descricaoServico" id="descricaoServico" rows="6" placeholder="Descreva os detalhes do serviço..." required></textarea>
      </div>

      <button type="submit">Cadastrar Serviço</button>

    </form>
  </main>

  <footer>
    <p>&copy; 2025 - Sistema de Frota</p>
  </footer>
<script>
    // Inicializa os Select2, conforme já estava
    $(document).ready(function() {
      $('#idMotorista').select2();
      $('#idCaminhao').select2();
      $('#idOrcamento').select2();
      $('#tipoServico').select2();
      $('#idTransportador').select2();
    });

    // Aplica a máscara no campo de valor (já existente)
    $('#valorTotal').mask('R$ #.##0,00', { reverse: true });
    
    // Evento onChange para o dropdown de orçamento
    $('#idOrcamento').change(function(){
        var idOrcamento = $(this).val();
        if(idOrcamento){ // Se algum orçamento for selecionado
            $.ajax({
                url: '../orcamentoJson',
                type: 'GET',
                dataType: 'json',
                data: { id: idOrcamento },
                success: function(data){
                    // Preenche os campos com os dados retornados
                    // Formata o valor total para o padrão "R$ X.XXX,XX"
                    var valorTotal = parseFloat(data.valorTotal);
                    // Converter para string com duas casas decimais, trocar ponto por vírgula
                    var valorFormatado = "R$ " + valorTotal.toFixed(2).replace('.', ',');
                    $('#valorTotal').val(valorFormatado);
                    $('#descricaoServico').val(data.descricao);
                },
                error: function(xhr, status, error){
                    console.error("Erro ao buscar orçamento: " + error);
                    // Caso haja erro, os campos podem ser limpos ou permanecem como estavam
                }
            });
        } else {
            // Se nenhuma opção for selecionada, limpa os campos (opcional)
            $('#valorTotal').val('');
            $('#descricaoServico').val('');
        }
    });
</script>

  <script>
    // Aplica a máscara no campo de valor
    $('#valorTotal').mask('R$ #.##0,00', { reverse: true });
  </script>
</body>
</html>
