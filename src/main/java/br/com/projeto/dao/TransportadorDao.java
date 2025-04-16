// TransportadorDao.java
package br.com.projeto.dao;

import br.com.projeto.model.Transportador;
import java.sql.*;
import java.util.*;

public class TransportadorDao {
    private final Connection connection;

    public TransportadorDao(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Transportador t) throws SQLException {
        String sql = "INSERT INTO transportador (cpfCnpj, nomeTransportador, telefone, endereco, dataRegistro) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, t.getCpfCnpj());
        stmt.setString(2, t.getNomeTransportador());
        stmt.setString(3, t.getTelefone());
        stmt.setString(4, t.getEndereco());
        stmt.setTimestamp(5, Timestamp.valueOf(t.getDataRegistro()));
        stmt.executeUpdate();
        stmt.close();
    }

    public List<Transportador> listarTodos() throws SQLException {
        List<Transportador> lista = new ArrayList<>();
        String sql = "SELECT * FROM transportador";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Transportador t = new Transportador();
            t.setCpfCnpj(rs.getString("cpfCnpj"));
            t.setNomeTransportador(rs.getString("nomeTransportador"));
            t.setTelefone(rs.getString("telefone"));
            t.setEndereco(rs.getString("endereco"));
            t.setDataRegistro(rs.getTimestamp("dataRegistro").toLocalDateTime());
            lista.add(t);
        }

        rs.close();
        stmt.close();
        return lista;
    }

    public void atualizar(Transportador t) throws SQLException {
        String sql = "UPDATE transportador SET nomeTransportador=?, telefone=?, endereco=?, dataRegistro=? WHERE cpfCnpj=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, t.getNomeTransportador());
        stmt.setString(2, t.getTelefone());
        stmt.setString(3, t.getEndereco());

        if (t.getDataRegistro() != null) {
            stmt.setTimestamp(4, Timestamp.valueOf(t.getDataRegistro()));
        } else {
            stmt.setTimestamp(4, null);
        }

        stmt.setString(5, t.getCpfCnpj());
        stmt.executeUpdate();
        stmt.close();
    }


    public void deletar(String cpfCnpj) throws SQLException {
        String sql = "DELETE FROM transportador WHERE cpfCnpj=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cpfCnpj);
        stmt.executeUpdate();
        stmt.close();
    }

    public Transportador buscarPorId(String cpfCnpj) throws SQLException {
        String sql = "SELECT * FROM transportador WHERE cpfCnpj=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cpfCnpj);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Transportador t = new Transportador();
            t.setCpfCnpj(rs.getString("cpfCnpj"));
            t.setNomeTransportador(rs.getString("nomeTransportador"));
            t.setTelefone(rs.getString("telefone"));
            t.setEndereco(rs.getString("endereco"));
            t.setDataRegistro(rs.getTimestamp("dataRegistro").toLocalDateTime());
            rs.close();
            stmt.close();
            return t;
        }

        rs.close();
        stmt.close();
        return null;
    }

    
}
