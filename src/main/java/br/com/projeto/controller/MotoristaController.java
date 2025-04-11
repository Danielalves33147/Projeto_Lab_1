package br.com.projeto.controller;

import br.com.projeto.dao.MotoristaDao;
import br.com.projeto.model.Motorista;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/motorista")
public class MotoristaController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nome = request.getParameter("nome");
            String cpf = request.getParameter("cpf");
            String cnh = request.getParameter("cnh");
            String telefone = request.getParameter("telefone");
            String transportadorCpf = request.getParameter("idTransportador");

            Motorista motorista = new Motorista();
            motorista.setCpf(cpf);
            motorista.setNomeMotorista(nome);
            motorista.setCnh(cnh);
            motorista.setTelefone(telefone);
            motorista.setTransportadorCpf(transportadorCpf);  // Nova referência para o transportador

            Connection conn = Conexao.getConnection();
            MotoristaDao dao = new MotoristaDao(conn);

            if (dao.existeCpf(cpf)) {
                response.sendRedirect("pages/cadastroUsuario.jsp?erroMotorista=CPF já cadastrado");
                return;
            }

            dao.inserir(motorista);
            response.sendRedirect("pages/cadastroUsuario.jsp?sucessoMotorista=true");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/cadastroUsuario.jsp?erroMotorista=Erro ao cadastrar motorista");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}
