package br.com.projeto.controller;

import br.com.projeto.dao.OrcamentoDao;
import br.com.projeto.model.Orcamento;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;

@WebServlet("/orcamento")
public class OrcamentoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Não estamos mais lendo valorCombustivel, somente os campos restantes.
            double valorDiaria = Double.parseDouble(request.getParameter("valorDiaria"));
            double valorTotal = Double.parseDouble(request.getParameter("valorTotal"));
            LocalDate dataEmissao = LocalDate.parse(request.getParameter("dataEmissao"));
            
            // Captura opcional do idServico
            String idServicoStr = request.getParameter("idServico");
            int idServico = 0; // Valor default (ou pode ser tratado como não vinculado)
            if (idServicoStr != null && !idServicoStr.trim().isEmpty()) {
                idServico = Integer.parseInt(idServicoStr);
            }
            
            // Outros parâmetros
            String descricao = request.getParameter("descricao");
            String tipoServico = request.getParameter("tipoServico");
            String detalhes = request.getParameter("detalhes");

            // Criar objeto Orcamento e preencher com os dados
            Orcamento orcamento = new Orcamento();
            orcamento.setValorDiaria(valorDiaria);
            orcamento.setValorTotal(valorTotal);
            orcamento.setDataEmissao(dataEmissao);
            orcamento.setIdServico(idServico);
            orcamento.setDescricao(descricao);
            orcamento.setTipoServico(tipoServico);
            orcamento.setDetalhes(detalhes);

            // Obter conexão e instanciar o DAO
            Connection conn = Conexao.getConnection();
            OrcamentoDao dao = new OrcamentoDao(conn);
            dao.inserir(orcamento);

            response.sendRedirect("pages/cadastroOrcamento.jsp?sucessoOrcamento=true");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/cadastroOrcamento.jsp?erroOrcamento=" + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}
