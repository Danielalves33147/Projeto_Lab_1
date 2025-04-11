package br.com.projeto.dao;

import br.com.projeto.model.Caminhao;

import java.sql.*;
import java.util.*;
import java.sql.Date;

public class CaminhaoDao {
    private Connection conn;

    public CaminhaoDao(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Caminhao caminhao) throws SQLException {
        String sql = "INSERT INTO caminhao (Placa, Modelo, Tipo, AnoFabricacao, CapacidadeMax, Transportador_CPF) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, caminhao.getPlaca());
            stmt.setString(2, caminhao.getModelo());
            stmt.setString(3, caminhao.getTipo());
            stmt.setDate(4, Date.valueOf(caminhao.getAnoFabricacao())); 
            stmt.setDouble(5, caminhao.getCapacidadeMax());
            stmt.setString(6, caminhao.getTransportadorCpf());  
            stmt.executeUpdate();
        }
    }


    public List<Caminhao> listarTodos() throws SQLException {
        List<Caminhao> lista = new ArrayList<>();
        String sql = "SELECT * FROM caminhao";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Caminhao c = new Caminhao();
                c.setPlaca(rs.getString("Placa"));
                c.setModelo(rs.getString("Modelo"));
                c.setTipo(rs.getString("Tipo"));
                c.setAnoFabricacao(rs.getDate("AnoFabricacao").toLocalDate());
                c.setCapacidadeMax(rs.getDouble("CapacidadeMax"));
                lista.add(c);
            }
        }
        return lista;
    }

    public Caminhao buscarPorPlaca(String placa) throws SQLException {
        String sql = "SELECT * FROM caminhao WHERE Placa = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Caminhao(
                    rs.getString("Placa"),
                    rs.getString("Modelo"),
                    rs.getString("Tipo"),
                    rs.getDate("AnoFabricacao").toLocalDate(),
                    rs.getDouble("CapacidadeMax")
                );
            }
        }
        return null;
    }

    public void deletar(String placa) throws SQLException {
        String sql = "DELETE FROM caminhao WHERE Placa = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            stmt.executeUpdate();
        }
    }

    public List<Caminhao> listarPorTransportador(int idTransportador) throws SQLException {
        List<Caminhao> lista = new ArrayList<>();
        String sql = "SELECT * FROM caminhao WHERE Transportador_idTransportador = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTransportador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Caminhao c = new Caminhao(
                    rs.getString("Placa"),
                    rs.getString("Modelo"),
                    rs.getString("Tipo"),
                    rs.getDate("AnoFabricacao").toLocalDate(),
                    rs.getDouble("CapacidadeMax")
                );
                lista.add(c);
            }
        }
        return lista;
    }

    public List<Map<String, Object>> listarUltimoUsoComMotorista() throws SQLException {
        List<Map<String, Object>> resultado = new ArrayList<>();
        String sql = """
            SELECT 
                c.Placa, c.Modelo, c.AnoFabricacao, c.CapacidadeMax,
                m.NomeMotorista, s.DataExecucao
            FROM 
                caminhao c
            LEFT JOIN servico_has_motorista shm ON c.Placa = shm.Caminhao_Placa
            LEFT JOIN servico s ON shm.Servico_idServico = s.idServico
            LEFT JOIN motorista m ON shm.Motorista_CPF = m.CPF
            WHERE s.DataExecucao = (
                SELECT MAX(s2.DataExecucao)
                FROM servico_has_motorista shm2
                INNER JOIN servico s2 ON shm2.Servico_idServico = s2.idServico
                WHERE shm2.Caminhao_Placa = c.Placa
            )
            ORDER BY c.Placa;
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> linha = new HashMap<>();
                linha.put("placa", rs.getString("Placa"));
                linha.put("modelo", rs.getString("Modelo"));
                linha.put("anoFabricacao", rs.getDate("AnoFabricacao"));
                linha.put("capacidadeMax", rs.getDouble("CapacidadeMax"));
                linha.put("motorista", rs.getString("NomeMotorista") != null ? rs.getString("NomeMotorista") : "Nunca usado");
                linha.put("dataExecucao", rs.getDate("DataExecucao") != null ? rs.getDate("DataExecucao").toString() : "-");
                resultado.add(linha);
            }
        }
        return resultado;
    }
}
