package br.com.projeto.dao;

import br.com.projeto.model.MotoristaHasCaminhao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotoristaHasCaminhaoDao {
    private final Connection connection;

    public MotoristaHasCaminhaoDao(Connection connection) {
        this.connection = connection;
    }

    public void inserir(MotoristaHasCaminhao m) throws SQLException {
        String sql = "INSERT INTO motorista_has_caminhao (motorista_cpf, caminhao_placa) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, m.getMotoristaCpf());
        stmt.setString(2, m.getCaminhaoPlaca());
        stmt.executeUpdate();
        stmt.close();
    }

    public List<MotoristaHasCaminhao> listarTodos() throws SQLException {
        List<MotoristaHasCaminhao> lista = new ArrayList<>();
        String sql = "SELECT * FROM motorista_has_caminhao";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            MotoristaHasCaminhao m = new MotoristaHasCaminhao();
            m.setMotoristaCpf(rs.getString("motorista_cpf"));
            m.setCaminhaoPlaca(rs.getString("caminhao_placa"));
            lista.add(m);
        }

        rs.close();
        stmt.close();
        return lista;
    }

    public void deletar(String motoristaCpf, String caminhaoPlaca) throws SQLException {
        String sql = "DELETE FROM motorista_has_caminhao WHERE motorista_cpf=? AND caminhao_placa=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, motoristaCpf);
        stmt.setString(2, caminhaoPlaca);
        stmt.executeUpdate();
        stmt.close();
    }

    public List<String> listarMotoristasPorCaminhao(String placa) throws SQLException {
        List<String> motoristas = new ArrayList<>();
        String sql = "SELECT motorista_cpf FROM motorista_has_caminhao WHERE caminhao_placa=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, placa);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            motoristas.add(rs.getString("motorista_cpf"));
        }

        rs.close();
        stmt.close();
        return motoristas;
    }
    
    public void vincularMotoristaCaminhao(String motoristaCpf, String caminhaoPlaca) throws SQLException {
        String sql = "INSERT INTO motorista_has_caminhao (motorista_cpf, caminhao_placa) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, motoristaCpf);
            stmt.setString(2, caminhaoPlaca);
            stmt.executeUpdate();
        }
    }


    public boolean existeVinculo(String motoristaCpf, String caminhaoPlaca) throws SQLException {
        String sql = "SELECT 1 FROM motorista_has_caminhao WHERE motorista_cpf=? AND caminhao_placa=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, motoristaCpf);
        stmt.setString(2, caminhaoPlaca);
        ResultSet rs = stmt.executeQuery();
        boolean existe = rs.next();
        rs.close();
        stmt.close();
        return existe;
    }
    
    public void deletarPorMotorista(String cpf) throws SQLException {
        String sql = "DELETE FROM motorista_has_caminhao WHERE motorista_cpf = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cpf);
        stmt.executeUpdate();
        stmt.close();
    }

    
    public String buscarPlacaPorMotorista(String motoristaCpf) throws SQLException {
        String sql = "SELECT caminhao_placa FROM motorista_has_caminhao WHERE motorista_cpf = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, motoristaCpf);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getString("caminhao_placa");
        }
        return null; // Retorna null se não encontrar nenhum vínculo
    }


}
