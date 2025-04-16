package br.com.projeto.controller;

import br.com.projeto.dao.ServicoHasMotoristaDao;
import br.com.projeto.dao.MotoristaHasCaminhaoDao;
import br.com.projeto.model.ServicoHasMotorista;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/servicoVinculo")
public class ServicoHasMotoristasController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conn = Conexao.getConnection()) {
            ServicoHasMotoristaDao dao = new ServicoHasMotoristaDao(conn);
            MotoristaHasCaminhaoDao autorizacaoDao = new MotoristaHasCaminhaoDao(conn);

            int idServico = Integer.parseInt(request.getParameter("idServico"));
            String motoristaCpf = request.getParameter("motoristaCpf");
            String caminhaoPlaca = request.getParameter("caminhaoPlaca");

            boolean autorizado = autorizacaoDao.existeVinculo(motoristaCpf, caminhaoPlaca);

            if (!autorizado) {
                response.sendRedirect("pages/listarServicos.jsp?erro=Motorista não autorizado para este caminhão");
                return;
            }

            if ("cadastrar".equals(acao)) {
                ServicoHasMotorista s = new ServicoHasMotorista();
                s.setIdServico(idServico);
                s.setMotoristaCpf(motoristaCpf);
                s.setCaminhaoPlaca(caminhaoPlaca);

                dao.inserir(s);
                response.sendRedirect("pages/listarServicos.jsp?sucessoVinculo=true");

            } else if ("deletar".equals(acao)) {
                dao.deletar(idServico, motoristaCpf, caminhaoPlaca);
                response.sendRedirect("pages/listarServicos.jsp?sucessoExclusaoVinculo=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/erro.jsp?erro=Falha no vínculo entre motorista e serviço");
        }
    }
}
