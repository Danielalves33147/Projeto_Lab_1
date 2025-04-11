package br.com.projeto.controller;

import br.com.projeto.dao.CaminhaoDao;
import br.com.projeto.dao.TransportadorDao;
import br.com.projeto.model.Caminhao;
import br.com.projeto.model.Transportador;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;

@WebServlet("/caminhao")
public class CaminhaoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Recebendo dados do formulário
            String placa = request.getParameter("placa");
            String modelo = request.getParameter("modelo");
            String tipo = request.getParameter("tipo");
            LocalDate anoFabricacao = LocalDate.parse(request.getParameter("anoFabricacao")); // Verifique o formato no JSP
            double capacidadeMax = Double.parseDouble(request.getParameter("capacidadeMax"));
            String cpfTransportador = request.getParameter("Transportador_CPF");

            // Verifica se o transportador existe pelo CPF
            Connection conn = Conexao.getConnection();
            TransportadorDao transportadorDao = new TransportadorDao(conn);
            Transportador transportador = transportadorDao.buscarPorCpf(cpfTransportador);

            if (transportador == null) {
                response.sendRedirect("pages/cadastroCaminhao.jsp?erro=Transportador não encontrado");
                return;
            }

            // Criando o caminhão e associando o transportador
            Caminhao caminhao = new Caminhao();
            caminhao.setPlaca(placa);
            caminhao.setModelo(modelo);
            caminhao.setTipo(tipo);
            caminhao.setAnoFabricacao(anoFabricacao);  // Onde 'anoFabricacao' é LocalDate
            caminhao.setCapacidadeMax(capacidadeMax);
            caminhao.setTransportadorCpf(transportador.getCpfCnpj()); // Vinculando ao CPF do Transportador

            // Persistindo o caminhão no banco
            CaminhaoDao caminhaoDao = new CaminhaoDao(conn);
            caminhaoDao.inserir(caminhao);

            // Redirecionando para a página com sucesso
            response.sendRedirect("pages/cadastroCaminhao.jsp?sucesso=true");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/cadastroCaminhao.jsp?erro=" + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}
