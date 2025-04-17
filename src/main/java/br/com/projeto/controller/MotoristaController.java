package br.com.projeto.controller;

import br.com.projeto.dao.MotoristaDao;
import br.com.projeto.dao.MotoristaHasCaminhaoDao;
import br.com.projeto.dao.ServicoHasMotoristaDao;
import br.com.projeto.model.Motorista;
import br.com.projeto.model.MotoristaHasCaminhao;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/motorista")
public class MotoristaController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        HttpSession session = request.getSession();
        
        try (Connection conn = Conexao.getConnection()) {
            MotoristaDao motoristaDao = new MotoristaDao(conn);
            MotoristaHasCaminhaoDao vinculoDao = new MotoristaHasCaminhaoDao(conn);

            if ("cadastrar".equals(acao)) {
                Motorista m = new Motorista();
                m.setCpf(request.getParameter("cpf"));
                m.setNomeMotorista(request.getParameter("nome"));
                m.setCnh(request.getParameter("cnh"));
                m.setTelefone(request.getParameter("telefone"));
                String caminhaoPlaca = request.getParameter("caminhaoPlaca");

                motoristaDao.inserir(m);
                vinculoDao.vincularMotoristaCaminhao(m.getCpf(), caminhaoPlaca);

                request.getSession().setAttribute("mensagemSucesso", "Motorista deletado com sucesso.");
                response.sendRedirect("pages/listas.jsp");


            } else if ("atualizar".equals(acao)) {
                String cpf = request.getParameter("cpf");
                String nome = request.getParameter("nome");
                String cnh = request.getParameter("cnh");
                String telefone = request.getParameter("telefone");
                String caminhaoPlaca = request.getParameter("caminhaoPlaca");

                Motorista m = new Motorista();
                m.setCpf(cpf);
                m.setNomeMotorista(nome);
                m.setCnh(cnh);
                m.setTelefone(telefone);

                motoristaDao.atualizar(m);

                ServicoHasMotoristaDao servicoDao = new ServicoHasMotoristaDao(conn);
                String placaAtual = vinculoDao.buscarPlacaPorMotorista(cpf);
                boolean usado = servicoDao.existeVinculoUsado(cpf, placaAtual);

                boolean vinculoAlterado = false;
                if (!usado && !placaAtual.equals(caminhaoPlaca)) {
                    vinculoDao.deletarPorMotorista(cpf);
                    vinculoDao.inserir(new MotoristaHasCaminhao(caminhaoPlaca, cpf));
                    vinculoAlterado = true;
                }

                session.setAttribute("mensagemSucesso", vinculoAlterado
                	    ? "Motorista e vínculo atualizados com sucesso."
                	    : "Motorista atualizado. Vínculo mantido pois já foi utilizado.");
                	response.sendRedirect("pages/listas.jsp");
            }

            else if ("deletar".equals(acao)) {
                String cpf = request.getParameter("cpf");
                motoristaDao.deletar(cpf);
                request.getSession().setAttribute("mensagemSucesso", "Motorista deletado com sucesso.");
                response.sendRedirect("pages/listas.jsp");

            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/listas.jsp?erro=Falha ao processar a ação do motorista");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conn = Conexao.getConnection()) {
            MotoristaDao dao = new MotoristaDao(conn);

            if ("deletar".equals(acao)) {
                String cpf = request.getParameter("cpf");

                ServicoHasMotoristaDao servicoDao = new ServicoHasMotoristaDao(conn);
                servicoDao.deletarPorMotorista(cpf);

                MotoristaHasCaminhaoDao vinculoDao = new MotoristaHasCaminhaoDao(conn);
                vinculoDao.deletarPorMotorista(cpf);

                dao.deletar(cpf);
                request.getSession().setAttribute("mensagemSucesso", "Motorista deletado com sucesso.");
                response.sendRedirect("pages/listas.jsp");


            } else if ("editar".equals(acao)) {
                String cpf = request.getParameter("cpf");
                Motorista motorista = dao.buscarPorId(cpf);

                if (motorista != null) {
                    request.setAttribute("motoristaEditando", motorista);
                    request.getRequestDispatcher("pages/edits/editarMotorista.jsp").forward(request, response);
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagemErro", "Algo deu errado!");
            response.sendRedirect("pages/listas.jsp");

        }
    }
}
