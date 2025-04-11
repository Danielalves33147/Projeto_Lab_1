package br.com.projeto.dao;

import br.com.projeto.model.Orcamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDao {
    private Connection conn;

    public OrcamentoDao(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Orcamento orcamento) throws SQLException {
        // Atualize a query para 6 parâmetros, se as colunas 'distancia' e 'Servico_idServico' foram removidas
        String sql = "INSERT INTO orcamento (descricao, tipoServico, detalhes, valorDiaria, valorTotal, dataEmissao) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orcamento.getDescricao());
            stmt.setString(2, orcamento.getTipoServico());
            stmt.setString(3, orcamento.getDetalhes());
            stmt.setDouble(4, orcamento.getValorDiaria());
            stmt.setDouble(5, orcamento.getValorTotal());
            stmt.setDate(6, Date.valueOf(orcamento.getDataEmissao()));
            stmt.executeUpdate();
        }
    }

    public List<Orcamento> listarTodos() throws SQLException {
        List<Orcamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM orcamento";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Orcamento o = new Orcamento();
                o.setIdOrcamento(rs.getInt("idOrcamento"));
                o.setDescricao(rs.getString("descricao"));
                o.setTipoServico(rs.getString("tipoServico"));
                o.setDetalhes(rs.getString("detalhes"));
                o.setValorDiaria(rs.getDouble("valorDiaria"));
                o.setValorTotal(rs.getDouble("valorTotal"));
                o.setDataEmissao(rs.getDate("dataEmissao").toLocalDate());
                lista.add(o);
            }
        }
        return lista;
    }

    public Orcamento buscarPorId(int idOrcamento) throws SQLException {
        String sql = "SELECT * FROM orcamento WHERE idOrcamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idOrcamento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Orcamento o = new Orcamento();
                    o.setIdOrcamento(rs.getInt("idOrcamento"));
                    o.setValorDiaria(rs.getDouble("valorDiaria"));
                    o.setValorTotal(rs.getDouble("valorTotal"));
                    o.setDataEmissao(rs.getDate("dataEmissao").toLocalDate());
                    o.setDescricao(rs.getString("descricao"));
                    o.setTipoServico(rs.getString("tipoServico"));
                    o.setDetalhes(rs.getString("detalhes"));
                    return o;
                }
            }
        }
        return null;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM orcamento WHERE idOrcamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
