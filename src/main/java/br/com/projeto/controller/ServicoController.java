package br.com.projeto.controller;

import br.com.projeto.dao.OrcamentoDao;
import br.com.projeto.dao.ServicoDao;
import br.com.projeto.dao.TransportadorDao;
import br.com.projeto.model.Orcamento;
import br.com.projeto.model.Servico;
import br.com.projeto.model.Transportador;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;

@WebServlet("/servico")
public class ServicoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("Iniciando o cadastro de serviço");

            // Parâmetros obrigatórios do serviço
            String tipoServico = request.getParameter("tipoServico");
            String placaCaminhao = request.getParameter("idCaminhao");
            String cpfMotorista = request.getParameter("idMotorista");
            LocalDate dataInicio = LocalDate.parse(request.getParameter("dataInicio"));
            LocalDate dataTermino = LocalDate.parse(request.getParameter("dataTermino"));
            String cpfCnpj = request.getParameter("idTransportador");
            
            // Parâmetros dos campos manuais
            String descricaoManual = request.getParameter("descricaoServico");
            String valorTotalString = request.getParameter("valorTotal");
            // Remover formatação: ex. "R$ 1.000,00" -> "1000.00"
            valorTotalString = valorTotalString.replace("R$ ", "").replace(".", "").replace(",", ".");
            double valorTotalManual = Double.parseDouble(valorTotalString);

            // Captura opcional do orçamento vinculado
            String idOrcamentoStr = request.getParameter("idOrcamento");

            // Conecta com o banco
            Connection conn = Conexao.getConnection();
            ServicoDao servicoDao = new ServicoDao(conn);
            TransportadorDao transportadorDao = new TransportadorDao(conn);

            // Verifica disponibilidade do motorista e caminhão
            boolean disponivel = servicoDao.verificarDisponibilidade(cpfMotorista, placaCaminhao, dataInicio, dataTermino);
            if (!disponivel) {
                response.sendRedirect("pages/cadastroServico.jsp?erro=Motorista ou Caminhão não disponível durante esse período.");
                return;
            }

            // Verifica se o transportador existe
            Transportador transportador = transportadorDao.buscarPorCpf(cpfCnpj);
            if (transportador == null) {
                response.sendRedirect("pages/cadastroServico.jsp?erro=Transportador não encontrado.");
                return;
            }

            // Cria o objeto Servico e seta os campos comuns
            Servico servico = new Servico();
            servico.setTipoServico(tipoServico);
            servico.setCaminhaoUtilizado(placaCaminhao);
            servico.setMotoristaCpf(cpfMotorista);
            servico.setDataInicio(dataInicio);
            servico.setDataTermino(dataTermino);
            servico.setTransportadorCpf(cpfCnpj);

            // Se for vinculado a um orçamento, usar os dados do orçamento
            if (idOrcamentoStr != null && !idOrcamentoStr.trim().isEmpty()) {
                int idOrcamento = Integer.parseInt(idOrcamentoStr);
                OrcamentoDao orcamentoDao = new OrcamentoDao(conn);
                Orcamento orcamento = orcamentoDao.buscarPorId(idOrcamento);
                if (orcamento != null) {
                    servico.setValorTotal(orcamento.getValorTotal());
                    servico.setDescricaoServico(orcamento.getDescricao());
                    servico.setIdOrcamento(idOrcamento);  // Vincula o orçamento ao serviço
                } else {
                    // Caso não encontre o orçamento, usa os valores manuais
                    servico.setValorTotal(valorTotalManual);
                    servico.setDescricaoServico(descricaoManual);
                }
            } else {
                // Sem orçamento vinculado, utiliza valores informados manualmente
                servico.setValorTotal(valorTotalManual);
                servico.setDescricaoServico(descricaoManual);
            }

            // Insere o serviço no banco
            servicoDao.inserir(servico);
            System.out.println("Serviço inserido com sucesso!");
            response.sendRedirect("pages/cadastroServico.jsp?sucesso=true");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/cadastroServico.jsp?erro=Erro ao cadastrar o serviço.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}
