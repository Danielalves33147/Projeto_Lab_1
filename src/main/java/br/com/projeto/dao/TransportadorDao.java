package br.com.projeto.dao;

import br.com.projeto.model.Transportador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportadorDao {
    private Connection conn;

    public TransportadorDao(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Transportador transportador) throws SQLException {
        if (existeCpfCnpj(transportador.getCpfCnpj())) {
            throw new SQLException("Já existe um transportador com este CPF/CNPJ cadastrado.");
        }

        String sql = "INSERT INTO transportador (NomeTransportador, CPFCNPJ, Telefone, Endereco, DataRegistro) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transportador.getNomeTransportador());
            stmt.setString(2, transportador.getCpfCnpj());
            stmt.setString(3, transportador.getTelefone());
            stmt.setString(4, transportador.getEndereco());
            stmt.setDate(5, Date.valueOf(transportador.getDataRegistro())); // Convertendo LocalDate para java.sql.Date
            stmt.executeUpdate();
        }
    }

    public boolean existeCpfCnpj(String cpfCnpj) throws SQLException {
        String sql = "SELECT COUNT(*) FROM transportador WHERE CPFCNPJ = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfCnpj);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public List<Transportador> listarTodos() throws SQLException {
        List<Transportador> transportadores = new ArrayList<>();
        String sql = "SELECT * FROM transportador";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Transportador t = new Transportador();
                t.setCpfCnpj(rs.getString("CPFCNPJ"));
                t.setNomeTransportador(rs.getString("NomeTransportador"));
                transportadores.add(t);
            }
        }
        return transportadores;
    }


    public Transportador buscarPorCpfCnpj(String cpfCnpj) throws SQLException {
        String sql = "SELECT * FROM transportador WHERE CPFCNPJ = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfCnpj);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Transportador(
                    rs.getString("CPFCNPJ"),
                    rs.getString("NomeTransportador"),
                    rs.getString("Telefone"),
                    rs.getString("Endereco"),
                    rs.getDate("DataRegistro").toLocalDate() // Corrigindo a conversão para LocalDate
                );
            }
        }
        return null;
    }

    public Transportador buscarPorCpf(String cpfCnpj) throws SQLException {
        String sql = "SELECT * FROM transportador WHERE CPFCNPJ = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfCnpj);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Transportador(
                    rs.getString("CPFCNPJ"),
                    rs.getString("NomeTransportador"),
                    rs.getString("Telefone"),
                    rs.getString("Endereco"),
                    rs.getDate("DataRegistro").toLocalDate() // Corrigindo a conversão para LocalDate
                );
            }
        }
        return null;
    }
}
