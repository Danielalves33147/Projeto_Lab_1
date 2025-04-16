package br.com.projeto.controller;

import br.com.projeto.dao.ServicoDao;
import br.com.projeto.dao.ServicoHasMotoristaDao;
import br.com.projeto.model.Servico;
import br.com.projeto.model.ServicoHasMotorista;
import utils.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;

@WebServlet("/servico")
public class ServicoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conn = Conexao.getConnection()) {
            ServicoDao servicoDao = new ServicoDao(conn);
            ServicoHasMotoristaDao vinculoDao = new ServicoHasMotoristaDao(conn);

            if ("cadastrar".equals(acao)) {
                Servico s = new Servico();
                s.setTipoServico(request.getParameter("tipoServico"));
                s.setDescricaoServico(request.getParameter("descricaoServico"));
                s.setDataInicio(LocalDate.parse(request.getParameter("dataInicio")));
                s.setDataTermino(LocalDate.parse(request.getParameter("dataTermino")));
                s.setValorTotal(Double.parseDouble(request.getParameter("valorTotal").replace(".", "").replace(",", "."))); // Corrigido

                String idOrcamentoStr = request.getParameter("idOrcamento");
                if (idOrcamentoStr != null && !idOrcamentoStr.isEmpty()) {
                    s.setIdOrcamento(Integer.parseInt(idOrcamentoStr));
                } else {
                    s.setIdOrcamento(null);
                }

                s.setIdTransportador(request.getParameter("idTransportador"));

                // Inserir serviço e obter o ID gerado
                int idGerado = servicoDao.inserirRetornandoId(s);

                // Vincular motorista + caminhão ao serviço
                String motoristaCpf = request.getParameter("motoristaCpf");
                String caminhaoPlaca = request.getParameter("caminhaoPlaca");

                ServicoHasMotorista vinculo = new ServicoHasMotorista();
                vinculo.setIdServico(idGerado);
                vinculo.setMotoristaCpf(motoristaCpf);
                vinculo.setCaminhaoPlaca(caminhaoPlaca);

                vinculoDao.inserir(vinculo);

                response.sendRedirect("pages/listas.jsp?sucesso=true");

            } else if ("deletar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("idServico"));
                servicoDao.deletar(id);
                response.sendRedirect("pages/listas.jsp?sucesso=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/cadastroServico.jsp?erro=Erro ao cadastrar serviço");
        }
    }
}
