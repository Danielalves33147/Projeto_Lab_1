package br.com.projeto.controller;

import br.com.projeto.dao.CaminhaoDao;
import br.com.projeto.dao.MotoristaDao;
import br.com.projeto.model.Caminhao;
import br.com.projeto.model.Motorista;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet("/disponibilidade")
public class DisponibilidadeController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idTransportador = Integer.parseInt(request.getParameter("idTransportador"));

        try {
            Connection conn = Conexao.getConnection();
            MotoristaDao motoristaDao = new MotoristaDao(conn);
            CaminhaoDao caminhaoDao = new CaminhaoDao(conn);

            List<Motorista> motoristas = motoristaDao.listarPorTransportador(idTransportador);
            List<Caminhao> caminhoes = caminhaoDao.listarPorTransportador(idTransportador);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            out.print("{\"motoristas\":[");
            for (int i = 0; i < motoristas.size(); i++) {
                Motorista m = motoristas.get(i);
                out.print(String.format("{\"cpf\":\"%s\",\"nome\":\"%s\"}", m.getCpf(), m.getNomeMotorista()));
                if (i < motoristas.size() - 1) out.print(",");
            }
            out.print("],");

            out.print("\"caminhoes\":[");
            for (int i = 0; i < caminhoes.size(); i++) {
                Caminhao c = caminhoes.get(i);
                out.print(String.format("{\"placa\":\"%s\",\"modelo\":\"%s\"}", c.getPlaca(), c.getModelo()));
                if (i < caminhoes.size() - 1) out.print(",");
            }
            out.print("]}");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar disponibilidade.");
        }
    }
}
