package br.com.projeto.controller;

import br.com.projeto.dao.CaminhaoDao;
import br.com.projeto.dao.MotoristaDao;
import br.com.projeto.dao.MotoristaHasCaminhaoDao;
import br.com.projeto.dao.ServicoHasMotoristaDao;
import br.com.projeto.dao.TransportadorDao;
import br.com.projeto.model.Motorista;
import br.com.projeto.model.Transportador;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/transportador")
public class TransportadorController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conn = Conexao.getConnection()) {
            TransportadorDao dao = new TransportadorDao(conn);

            if ("cadastrar".equals(acao)) {
                Transportador t = new Transportador();
                t.setCpfCnpj(request.getParameter("cpfCnpj"));
                t.setNomeTransportador(request.getParameter("nome"));
                t.setTelefone(request.getParameter("telefone"));
                t.setEndereco(request.getParameter("endereco"));
                t.setDataRegistro(LocalDateTime.now());

                dao.inserir(t);
                response.sendRedirect("pages/cadastroUsuario.jsp?erroTransportador=Erro ao cadastrar transportador");

            } else if ("atualizar".equals(acao)) {
                String cpfCnpj = request.getParameter("cpfCnpj");
                String nome = request.getParameter("nome");
                String telefone = request.getParameter("telefone");
                String endereco = request.getParameter("endereco");

                Transportador t = new Transportador();
                t.setCpfCnpj(cpfCnpj);
                t.setNomeTransportador(nome);
                t.setTelefone(telefone);
                t.setEndereco(endereco);

                // Proteção: busca data de registro anterior ou usa a atual
                Transportador atual = dao.buscarPorId(cpfCnpj);
                if (atual != null && atual.getDataRegistro() != null) {
                    t.setDataRegistro(atual.getDataRegistro());
                } else {
                    System.out.println("⚠️ Transportador não encontrado ou sem data de registro. Usando data atual.");
                    t.setDataRegistro(LocalDateTime.now());
                }

                dao.atualizar(t);
                response.sendRedirect("pages/listas.jsp?sucesso=atualizado");
            } else if ("deletar".equals(acao)) {
                String cpfCnpj = request.getParameter("cpfCnpj");
                dao.deletar(cpfCnpj);
                response.sendRedirect("pages/listas.jsp?erro=Falha ao atualizar transportador");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/listas.jsp?erro=Falha ao atualizar transportador");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conn = Conexao.getConnection()) {
            TransportadorDao dao = new TransportadorDao(conn);

            if ("editar".equals(acao)) {
                String cpfCnpj = request.getParameter("cpfCnpj");
                Transportador transportador = dao.buscarPorId(cpfCnpj);

                if (transportador != null) {
                    request.setAttribute("transportadorEditando", transportador);
                    request.getRequestDispatcher("pages/edits/editarTransportador.jsp").forward(request, response);
                    return;
                }
            }

            if ("deletar".equals(acao)) {
                String cpfCnpj = request.getParameter("cpfCnpj");

                // DAOs
                MotoristaDao motoristaDao = new MotoristaDao(conn);
                CaminhaoDao caminhaoDao = new CaminhaoDao(conn);
                MotoristaHasCaminhaoDao vinculoDao = new MotoristaHasCaminhaoDao(conn);
                ServicoHasMotoristaDao servicoDao = new ServicoHasMotoristaDao(conn);

                // 1. Lista todos os motoristas desse transportador
                List<Motorista> motoristas = motoristaDao.buscarPorTransportador(cpfCnpj);
                for (Motorista m : motoristas) {
                    // 1.1 Deleta os serviços relacionados
                    servicoDao.deletarPorMotorista(m.getCpf());

                    // 1.2 Remove vínculos com caminhão
                    vinculoDao.deletarPorMotorista(m.getCpf());

                    // 1.3 Remove o motorista
                    motoristaDao.deletar(m.getCpf());
                }

                // 2. Deleta todos os caminhões do transportador
                caminhaoDao.deletarPorTransportador(cpfCnpj);

                // 3. Deleta o transportador
                TransportadorDao transportadorDao = new TransportadorDao(conn);
                transportadorDao.deletar(cpfCnpj);

                response.sendRedirect("pages/listas.jsp?sucesso=true");
            }


        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/listas.jsp?erro=Falha ao processar transportador");
        }
    }

}
