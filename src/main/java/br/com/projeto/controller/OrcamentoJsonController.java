package br.com.projeto.controller;

import br.com.projeto.dao.OrcamentoDao;
import br.com.projeto.model.Orcamento;
import utils.LocalDateAdapter;
import utils.Conexao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/orcamentoJson")
public class OrcamentoJsonController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do orçamento é obrigatório");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            Connection conn = Conexao.getConnection();
            OrcamentoDao orcamentoDao = new OrcamentoDao(conn);
            Orcamento orcamento = orcamentoDao.buscarPorId(id);
            if (orcamento == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orçamento não encontrado");
                return;
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Registra o adapter para LocalDate
            Gson gson = new GsonBuilder()
                          .registerTypeAdapter(java.time.LocalDate.class, new LocalDateAdapter())
                          .create();
            String json = gson.toJson(orcamento);
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar o orçamento");
        }
    }
}
