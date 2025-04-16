package br.com.projeto.controller;

import br.com.projeto.dao.CaminhaoDao;
import br.com.projeto.dao.MotoristaDao;
import br.com.projeto.model.Caminhao;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;

@WebServlet("/caminhao")
public class CaminhaoController extends HttpServlet {

    private double parseValor(String valor) {
        if (valor == null || valor.isEmpty()) return 0.0;
        return Double.parseDouble(valor.replace(".", "").replace(",", "."));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conn = Conexao.getConnection()) {
            CaminhaoDao dao = new CaminhaoDao(conn);

            if ("cadastrar".equals(acao)) {
                Caminhao c = new Caminhao();
                c.setPlaca(request.getParameter("placa"));
                c.setModelo(request.getParameter("modelo"));
                c.setTipo(request.getParameter("tipo"));
                c.setAnoFabricacao(LocalDate.parse(request.getParameter("anoFabricacao")));
                c.setCapacidadeMax(parseValor(request.getParameter("capacidadeMax")));
                c.setCpfCnpj(request.getParameter("cpfCnpj"));

                dao.inserir(c);
                response.sendRedirect("pages/cadastroCaminhao.jsp?sucesso=true");

            } else if ("atualizar".equals(acao)) {
                Caminhao c = new Caminhao();
                c.setPlaca(request.getParameter("placa"));
                c.setModelo(request.getParameter("modelo"));
                c.setTipo(request.getParameter("tipo"));
                c.setAnoFabricacao(LocalDate.parse(request.getParameter("anoFabricacao")));
                c.setCapacidadeMax(parseValor(request.getParameter("capacidadeMax")));
                c.setCpfCnpj(request.getParameter("cpfCnpj"));

                dao.atualizar(c);
                response.sendRedirect("pages/cadastroCaminhao.jsp?sucesso=true");

            } else if ("deletar".equals(acao)) {
                String placa = request.getParameter("placa");
                dao.deletar(placa);
                response.sendRedirect("pages/cadastroCaminhao.jsp?sucesso=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/cadastroCaminhao.jsp?erro=Erro ao cadastrar caminhão");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conn = Conexao.getConnection()) {
            MotoristaDao dao = new MotoristaDao(conn);

            if ("deletar".equals(acao)) {
                String cpf = request.getParameter("cpf");
                dao.deletar(cpf);
                response.sendRedirect("pages/listas.jsp?sucessoDelecaoMotorista=true");
            } else {
                response.sendRedirect("pages/listas.jsp?erroAcaoInvalida=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/listas.jsp?erroMotorista=Erro ao deletar motorista");
        }
    }

}
