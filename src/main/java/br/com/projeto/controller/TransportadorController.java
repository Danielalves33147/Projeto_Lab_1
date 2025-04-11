package br.com.projeto.controller;

import br.com.projeto.dao.TransportadorDao;
import br.com.projeto.model.Transportador;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/transportador")
public class TransportadorController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nome = request.getParameter("nome");
            String cpfCnpj = request.getParameter("cpfCnpj");
            String telefone = request.getParameter("telefone");
            String endereco = request.getParameter("endereco");
            String dataRegistroStr = request.getParameter("dataRegistro");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataRegistro = LocalDate.parse(dataRegistroStr, formatter);

            Transportador transportador = new Transportador();
            transportador.setNomeTransportador(nome);
            transportador.setCpfCnpj(cpfCnpj);
            transportador.setTelefone(telefone);
            transportador.setEndereco(endereco);
            transportador.setDataRegistro(dataRegistro);

            Connection conn = Conexao.getConnection();
            TransportadorDao dao = new TransportadorDao(conn);

            if (dao.existeCpfCnpj(cpfCnpj)) {
                response.sendRedirect("pages/cadastroUsuario.jsp?erroTransportador=CPF/CNPJ já cadastrado");
                return;
            }

            dao.inserir(transportador);
            response.sendRedirect("pages/cadastroUsuario.jsp?sucessoTransportador=true");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/cadastroUsuario.jsp?erroTransportador=Erro ao cadastrar transportador");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}
