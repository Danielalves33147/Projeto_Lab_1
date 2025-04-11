package br.com.projeto.controller;

import br.com.projeto.dao.OrcamentoDao;
import br.com.projeto.model.Orcamento;
import utils.Conexao;
import utils.RelatorioOrcamentoPDF;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;

@WebServlet("/relatorioOrcamento")
public class RelatorioOrcamentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("idOrcamento"));
            Connection conn = Conexao.getConnection();
            OrcamentoDao dao = new OrcamentoDao(conn);
            Orcamento orcamento = dao.buscarPorId(id);

            if (orcamento == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orçamento não encontrado.");
                return;
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=relatorio_orcamento_" + id + ".pdf");

            OutputStream out = response.getOutputStream();
            RelatorioOrcamentoPDF.gerarPDF(orcamento, out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF.");
        }
    }
}
