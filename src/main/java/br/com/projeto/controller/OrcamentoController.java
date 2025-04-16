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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conn = Conexao.getConnection()) {
            OrcamentoDao dao = new OrcamentoDao(conn);

            if ("cadastrar".equals(acao)) {
                Orcamento o = new Orcamento();
                o.setDescricao(request.getParameter("descricao"));
                o.setTipoServico(request.getParameter("tipoServico"));
                o.setDetalhes(request.getParameter("detalhes"));
                o.setValorDiaria(Double.parseDouble(request.getParameter("valorDiaria").replace(",", ".")));
                o.setValorTotal(Double.parseDouble(request.getParameter("valorTotal").replace(",", ".")));
                o.setDataEmissao(LocalDate.parse(request.getParameter("dataEmissao")));

                dao.inserir(o);
                response.sendRedirect("pages/listarOrcamentos.jsp?sucesso=true");

            } else if ("atualizar".equals(acao)) {
                Orcamento o = new Orcamento();
                o.setIdOrcamento(Integer.parseInt(request.getParameter("idOrcamento")));
                o.setDescricao(request.getParameter("descricao"));
                o.setTipoServico(request.getParameter("tipoServico"));
                o.setDetalhes(request.getParameter("detalhes"));
                o.setValorDiaria(Double.parseDouble(request.getParameter("valorDiaria").replace(",", ".")));
                o.setValorTotal(Double.parseDouble(request.getParameter("valorTotal").replace(",", ".")));
                o.setDataEmissao(LocalDate.parse(request.getParameter("dataEmissao")));

                dao.atualizar(o);
                response.sendRedirect("pages/listarOrcamentos.jsp?sucesso=true");

            } else if ("deletar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("idOrcamento"));
                dao.deletar(id);
                response.sendRedirect("pages/listarOrcamentos.jsp?sucesso=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/erro.jsp?erro=Falha no processamento do orçamento");
        }
    }
}
