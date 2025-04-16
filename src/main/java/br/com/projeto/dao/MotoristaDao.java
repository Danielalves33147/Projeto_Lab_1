package br.com.projeto.dao;

import br.com.projeto.model.Motorista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotoristaDao {
    private final Connection connection;

    public MotoristaDao(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Motorista m) throws SQLException {
        String sql = "INSERT INTO motorista (cpf, nomeMotorista, cnh, telefone) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, m.getCpf());
        stmt.setString(2, m.getNomeMotorista());
        stmt.setString(3, m.getCnh());
        stmt.setString(4, m.getTelefone());
        stmt.executeUpdate();
        stmt.close();
    }

    public List<Motorista> listarTodos() throws SQLException {
        List<Motorista> lista = new ArrayList<>();
        String sql = "SELECT * FROM motorista";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Motorista m = new Motorista();
            m.setCpf(rs.getString("cpf"));
            m.setNomeMotorista(rs.getString("nomeMotorista"));
            m.setCnh(rs.getString("cnh"));
            m.setTelefone(rs.getString("telefone"));
            lista.add(m);
        }

        rs.close();
        stmt.close();
        return lista;
    }

    public void atualizar(Motorista m) throws SQLException {
        String sql = "UPDATE motorista SET nomeMotorista=?, cnh=?, telefone=? WHERE cpf=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, m.getNomeMotorista());
        stmt.setString(2, m.getCnh());
        stmt.setString(3, m.getTelefone());
        stmt.setString(4, m.getCpf());
        stmt.executeUpdate();
        stmt.close();
    }

    public void deletar(String cpf) throws SQLException {
        String sql = "DELETE FROM motorista WHERE cpf=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cpf);
        stmt.executeUpdate();
        stmt.close();
    }
    
    public Motorista buscarPorCpf(String cpf) throws SQLException {
        return buscarPorId(cpf);
    }

    public Motorista buscarPorId(String cpf) throws SQLException {
        String sql = "SELECT * FROM motorista WHERE cpf=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Motorista m = new Motorista();
            m.setCpf(rs.getString("cpf"));
            m.setNomeMotorista(rs.getString("nomeMotorista"));
            m.setCnh(rs.getString("cnh"));
            m.setTelefone(rs.getString("telefone"));
            rs.close();
            stmt.close();
            return m;
        }

        rs.close();
        stmt.close();
        return null;
    }

    public List<Motorista> buscarPorTransportador(String cpfCnpjTransportador) throws SQLException {
        String sql = "SELECT DISTINCT m.* " +
                     "FROM motorista m " +
                     "JOIN motorista_has_caminhao mhc ON m.cpf = mhc.motorista_cpf " +
                     "JOIN caminhao c ON mhc.caminhao_placa = c.placa " +
                     "WHERE c.cpfCnpj = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cpfCnpjTransportador);
        ResultSet rs = stmt.executeQuery();

        List<Motorista> lista = new ArrayList<>();
        while (rs.next()) {
            Motorista m = new Motorista();
            m.setCpf(rs.getString("cpf"));
            m.setNomeMotorista(rs.getString("nomeMotorista"));
            m.setCnh(rs.getString("cnh"));
            m.setTelefone(rs.getString("telefone"));
            lista.add(m);
        }

        rs.close();
        stmt.close();
        return lista;
    }



}
