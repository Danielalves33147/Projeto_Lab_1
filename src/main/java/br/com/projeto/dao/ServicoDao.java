package br.com.projeto.dao;

import br.com.projeto.model.Servico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDao {
    private final Connection connection;

    public ServicoDao(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Servico s) throws SQLException {
        String sql = "INSERT INTO servico (tipoServico, descricaoServico, dataInicio, dataTermino, valorTotal, idOrcamento, idTransportador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, s.getTipoServico());
        stmt.setString(2, s.getDescricaoServico());
        stmt.setDate(3, Date.valueOf(s.getDataInicio()));
        stmt.setDate(4, Date.valueOf(s.getDataTermino()));
        stmt.setDouble(5, s.getValorTotal());

        if (s.getIdOrcamento() != null) {
            stmt.setInt(6, s.getIdOrcamento());
        } else {
            stmt.setNull(6, Types.INTEGER);
        }

        stmt.setString(7, s.getIdTransportador());

        stmt.executeUpdate();
        stmt.close();
    }
    
    public int inserirRetornandoId(Servico s) throws SQLException {
        String sql = "INSERT INTO servico (tipoServico, descricaoServico, dataInicio, dataTermino, valorTotal, idOrcamento, idTransportador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, s.getTipoServico());
            stmt.setString(2, s.getDescricaoServico());
            stmt.setDate(3, Date.valueOf(s.getDataInicio()));
            stmt.setDate(4, Date.valueOf(s.getDataTermino()));
            stmt.setDouble(5, s.getValorTotal());

            if (s.getIdOrcamento() != null) {
                stmt.setInt(6, s.getIdOrcamento());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setString(7, s.getIdTransportador());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Erro ao obter ID gerado do serviço.");
                }
            }
        }
    }


    public List<Servico> listarTodos() throws SQLException {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM servico";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Servico s = new Servico();
            s.setIdServico(rs.getInt("idServico"));
            s.setTipoServico(rs.getString("tipoServico"));
            s.setDescricaoServico(rs.getString("descricaoServico"));
            s.setDataInicio(rs.getDate("dataInicio").toLocalDate());
            s.setDataTermino(rs.getDate("dataTermino").toLocalDate());
            s.setValorTotal(rs.getDouble("valorTotal"));
            int idOrcamento = rs.getInt("idOrcamento");
            s.setIdOrcamento(rs.wasNull() ? null : idOrcamento);
            s.setIdTransportador(rs.getString("idTransportador"));
            lista.add(s);
        }

        rs.close();
        stmt.close();
        return lista;
    }

    public void atualizar(Servico s) throws SQLException {
        String sql = "UPDATE servico SET tipoServico=?, descricaoServico=?, dataInicio=?, dataTermino=?, valorTotal=?, idOrcamento=?, idTransportador=? WHERE idServico=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, s.getTipoServico());
        stmt.setString(2, s.getDescricaoServico());
        stmt.setDate(3, Date.valueOf(s.getDataInicio()));
        stmt.setDate(4, Date.valueOf(s.getDataTermino()));
        stmt.setDouble(5, s.getValorTotal());

        if (s.getIdOrcamento() != null) {
            stmt.setInt(6, s.getIdOrcamento());
        } else {
            stmt.setNull(6, Types.INTEGER);
        }

        stmt.setString(7, s.getIdTransportador());
        stmt.setInt(8, s.getIdServico());

        stmt.executeUpdate();
        stmt.close();
    }

    public void deletar(int idServico) throws SQLException {
        String sql = "DELETE FROM servico WHERE idServico=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idServico);
        stmt.executeUpdate();
        stmt.close();
    }

    public Servico buscarPorId(int idServico) throws SQLException {
        String sql = "SELECT * FROM servico WHERE idServico=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idServico);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Servico s = new Servico();
            s.setIdServico(rs.getInt("idServico"));
            s.setTipoServico(rs.getString("tipoServico"));
            s.setDescricaoServico(rs.getString("descricaoServico"));
            s.setDataInicio(rs.getDate("dataInicio").toLocalDate());
            s.setDataTermino(rs.getDate("dataTermino").toLocalDate());
            s.setValorTotal(rs.getDouble("valorTotal"));
            int idOrcamento = rs.getInt("idOrcamento");
            s.setIdOrcamento(rs.wasNull() ? null : idOrcamento);
            s.setIdTransportador(rs.getString("idTransportador"));
            rs.close();
            stmt.close();
            return s;
        }

        rs.close();
        stmt.close();
        return null;
    }
}
