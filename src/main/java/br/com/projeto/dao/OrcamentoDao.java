package br.com.projeto.dao;

import br.com.projeto.model.Orcamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDao {
    private final Connection connection;

    public OrcamentoDao(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Orcamento o) throws SQLException {
        String sql = "INSERT INTO orcamento (descricao, tipoServico, detalhes, valorDiaria, valorTotal, dataEmissao) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, o.getDescricao());
        stmt.setString(2, o.getTipoServico());
        stmt.setString(3, o.getDetalhes());
        stmt.setDouble(4, o.getValorDiaria());
        stmt.setDouble(5, o.getValorTotal());
        stmt.setDate(6, Date.valueOf(o.getDataEmissao()));
        stmt.executeUpdate();
        stmt.close();
    }

    public List<Orcamento> listarTodos() throws SQLException {
        List<Orcamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM orcamento";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

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

        rs.close();
        stmt.close();
        return lista;
    }

    public void atualizar(Orcamento o) throws SQLException {
        String sql = "UPDATE orcamento SET descricao=?, tipoServico=?, detalhes=?, valorDiaria=?, valorTotal=?, dataEmissao=? WHERE idOrcamento=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, o.getDescricao());
        stmt.setString(2, o.getTipoServico());
        stmt.setString(3, o.getDetalhes());
        stmt.setDouble(4, o.getValorDiaria());
        stmt.setDouble(5, o.getValorTotal());
        stmt.setDate(6, Date.valueOf(o.getDataEmissao()));
        stmt.setInt(7, o.getIdOrcamento());
        stmt.executeUpdate();
        stmt.close();
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM orcamento WHERE idOrcamento=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
    }

    public Orcamento buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM orcamento WHERE idOrcamento=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Orcamento o = new Orcamento();
            o.setIdOrcamento(rs.getInt("idOrcamento"));
            o.setDescricao(rs.getString("descricao"));
            o.setTipoServico(rs.getString("tipoServico"));
            o.setDetalhes(rs.getString("detalhes"));
            o.setValorDiaria(rs.getDouble("valorDiaria"));
            o.setValorTotal(rs.getDouble("valorTotal"));
            o.setDataEmissao(rs.getDate("dataEmissao").toLocalDate());
            rs.close();
            stmt.close();
            return o;
        }

        rs.close();
        stmt.close();
        return null;
    }
}
