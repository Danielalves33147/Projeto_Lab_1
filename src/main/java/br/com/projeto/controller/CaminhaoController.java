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
        HttpSession session = request.getSession();

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
                
                session.setAttribute("mensagemSucesso", "Caminhão cadastrado com sucesso.");
                response.sendRedirect("pages/listas.jsp");


            } else if ("atualizar".equals(acao)) {
                Caminhao c = new Caminhao();
                c.setPlaca(request.getParameter("placa"));
                c.setModelo(request.getParameter("modelo"));
                c.setTipo(request.getParameter("tipo"));
                c.setAnoFabricacao(LocalDate.parse(request.getParameter("anoFabricacao")));
                c.setCapacidadeMax(parseValor(request.getParameter("capacidadeMax")));
                c.setCpfCnpj(request.getParameter("cpfCnpj"));

                dao.atualizar(c);
                
                session.setAttribute("mensagemSucesso", "Caminhão atualizado com sucesso.");
                response.sendRedirect("pages/listas.jsp");


            } else if ("deletar".equals(acao)) {
                String placa = request.getParameter("placa");
                dao.deletar(placa);
                session.setAttribute("mensagemSucesso", "Caminhão deletado com sucesso.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("mensagemErro", "Erro ao processar ação do caminhão.");
            response.sendRedirect("pages/listas.jsp");

        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        HttpSession session = request.getSession();
        try (Connection conn = Conexao.getConnection()) {
            MotoristaDao dao = new MotoristaDao(conn);

            if ("deletar".equals(acao)) {
                String cpf = request.getParameter("cpf");
                dao.deletar(cpf);
                response.sendRedirect("pages/listas.jsp?sucessoDelecaoMotorista=true");
                response.sendRedirect("pages/listas.jsp");
            } else {
                response.sendRedirect("pages/listas.jsp?erroAcaoInvalida=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("mensagemErro", "Erro ao processar ação do caminhão.");
            response.sendRedirect("pages/listas.jsp");
        }
    }

}
