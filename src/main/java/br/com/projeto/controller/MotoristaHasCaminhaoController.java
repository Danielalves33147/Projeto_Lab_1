package br.com.projeto.controller;

import br.com.projeto.dao.MotoristaHasCaminhaoDao;
import br.com.projeto.model.MotoristaHasCaminhao;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/vinculoMotorista")
public class MotoristaHasCaminhaoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conn = Conexao.getConnection()) {
            MotoristaHasCaminhaoDao dao = new MotoristaHasCaminhaoDao(conn);

            String motoristaCpf = request.getParameter("motoristaCpf");
            String caminhaoPlaca = request.getParameter("caminhaoPlaca");

            if ("cadastrar".equals(acao)) {
                MotoristaHasCaminhao vinculo = new MotoristaHasCaminhao();
                vinculo.setMotoristaCpf(motoristaCpf);
                vinculo.setCaminhaoPlaca(caminhaoPlaca);

                dao.inserir(vinculo);
                response.sendRedirect("pages/vinculoMotorista.jsp?sucesso=true");

            } else if ("deletar".equals(acao)) {
                dao.deletar(motoristaCpf, caminhaoPlaca);
                response.sendRedirect("pages/vinculoMotorista.jsp?sucessoExclusao=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/erro.jsp?erro=Falha no vínculo entre motorista e caminhão");
        }
    }
}
