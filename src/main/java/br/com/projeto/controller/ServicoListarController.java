package br.com.projeto.controller;

import br.com.projeto.dao.ServicoDao;
import br.com.projeto.model.Servico;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/listaServico")
public class ServicoListarController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection conn = Conexao.getConnection();
            ServicoDao dao = new ServicoDao(conn);

            List<Servico> servicos = dao.listarTodos();
            request.setAttribute("servicos", servicos);

            request.getRequestDispatcher("pages/listaServico.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao listar serviços: " + e.getMessage());
        }
    }
}
