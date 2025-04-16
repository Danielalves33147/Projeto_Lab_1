package br.com.projeto.dao;

import br.com.projeto.model.Caminhao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CaminhaoDao {
    private final Connection connection;

    public CaminhaoDao(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Caminhao c) throws SQLException {
        String sql = "INSERT INTO caminhao (placa, modelo, tipo, anoFabricacao, capacidadeMax, cpfCnpj) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, c.getPlaca());
        stmt.setString(2, c.getModelo());
        stmt.setString(3, c.getTipo());
        stmt.setDate(4, Date.valueOf(c.getAnoFabricacao()));
        stmt.setDouble(5, c.getCapacidadeMax());
        stmt.setString(6, c.getCpfCnpj());
        stmt.executeUpdate();
        stmt.close();
    }

    public List<Caminhao> listarTodos() throws SQLException {
        List<Caminhao> lista = new ArrayList<>();
        String sql = "SELECT * FROM caminhao";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Caminhao c = new Caminhao();
            c.setPlaca(rs.getString("placa"));
            c.setModelo(rs.getString("modelo"));
            c.setTipo(rs.getString("tipo"));
            c.setAnoFabricacao(rs.getDate("anoFabricacao").toLocalDate());
            c.setCapacidadeMax(rs.getDouble("capacidadeMax"));
            c.setCpfCnpj(rs.getString("cpfCnpj"));
            lista.add(c);
        }

        rs.close();
        stmt.close();
        return lista;
    }

    public void atualizar(Caminhao c) throws SQLException {
        String sql = "UPDATE caminhao SET modelo=?, tipo=?, anoFabricacao=?, capacidadeMax=?, cpfCnpj=? WHERE placa=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, c.getModelo());
        stmt.setString(2, c.getTipo());
        stmt.setDate(3, Date.valueOf(c.getAnoFabricacao()));
        stmt.setDouble(4, c.getCapacidadeMax());
        stmt.setString(5, c.getCpfCnpj());
        stmt.setString(6, c.getPlaca());
        stmt.executeUpdate();
        stmt.close();
    }

    public void deletar(String placa) throws SQLException {
        String sql = "DELETE FROM caminhao WHERE placa=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, placa);
        stmt.executeUpdate();
        stmt.close();
    }
    
    public Caminhao buscarPorPlaca(String placa) throws SQLException {
        return buscarPorId(placa);
    }


    public Caminhao buscarPorId(String placa) throws SQLException {
        String sql = "SELECT * FROM caminhao WHERE placa=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, placa);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Caminhao c = new Caminhao();
            c.setPlaca(rs.getString("placa"));
            c.setModelo(rs.getString("modelo"));
            c.setTipo(rs.getString("tipo"));
            c.setAnoFabricacao(rs.getDate("anoFabricacao").toLocalDate());
            c.setCapacidadeMax(rs.getDouble("capacidadeMax"));
            c.setCpfCnpj(rs.getString("cpfCnpj"));
            rs.close();
            stmt.close();
            return c;
        }

        rs.close();
        stmt.close();
        return null;
    }
    
    public void deletarPorTransportador(String cpfCnpj) throws SQLException {
    	String sql = "DELETE FROM caminhao WHERE cpfCnpj = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cpfCnpj);
        stmt.executeUpdate();
        stmt.close();
    }

}
