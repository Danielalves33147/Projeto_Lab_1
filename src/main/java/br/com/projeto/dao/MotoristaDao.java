package br.com.projeto.dao;

import br.com.projeto.model.Motorista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotoristaDao {
    private Connection conn;

    public MotoristaDao(Connection conn) {
        this.conn = conn;
    }

    // Verifica se o CPF do motorista já está cadastrado
    public boolean existeCpf(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM motorista WHERE CPF = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // Insere um novo motorista com o vínculo ao transportador através do CPF
    public void inserir(Motorista motorista) throws SQLException {
        if (existeCpf(motorista.getCpf())) {
            throw new SQLException("Já existe um motorista com este CPF cadastrado.");
        }

        String sql = "INSERT INTO motorista (CPF, NomeMotorista, CNH, Telefone, Transportador_CPF) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, motorista.getCpf()); // Usando CPF como chave primária
            stmt.setString(2, motorista.getNomeMotorista());
            stmt.setString(3, motorista.getCnh());
            stmt.setString(4, motorista.getTelefone());
            stmt.setString(5, motorista.getTransportadorCpf());  // Chave estrangeira para o CPF do transportador
            stmt.executeUpdate();
        }
    }

    // Lista todos os motoristas
    public List<Motorista> listarTodos() throws SQLException {
        List<Motorista> lista = new ArrayList<>();
        String sql = "SELECT * FROM motorista";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Motorista m = new Motorista();
                m.setCpf(rs.getString("CPF"));
                m.setNomeMotorista(rs.getString("NomeMotorista"));
                m.setCnh(rs.getString("CNH"));
                m.setTelefone(rs.getString("Telefone"));
                m.setTransportadorCpf(rs.getString("Transportador_CPF"));  // Agora usamos CPF do Transportador
                lista.add(m);
            }
        }
        return lista;
    }

    // Busca um motorista pelo CPF
    public Motorista buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM motorista WHERE CPF = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Motorista m = new Motorista();
                m.setCpf(rs.getString("CPF"));
                m.setNomeMotorista(rs.getString("NomeMotorista"));
                m.setCnh(rs.getString("CNH"));
                m.setTelefone(rs.getString("Telefone"));
                m.setTransportadorCpf(rs.getString("Transportador_CPF"));  // Agora usamos CPF do Transportador
                return m;
            }
        }
        return null;
    }

    // Alterar o método para aceitar um int
    public List<Motorista> listarPorTransportador(int idTransportador) throws SQLException {
        List<Motorista> lista = new ArrayList<>();
        String sql = "SELECT * FROM motorista WHERE Transportador_idTransportador = ?"; // Ajuste o nome do campo, se necessário
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTransportador);  // Usando o tipo correto
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Motorista m = new Motorista();
                m.setCpf(rs.getString("CPF"));
                m.setNomeMotorista(rs.getString("NomeMotorista"));
                m.setCnh(rs.getString("CNH"));
                m.setTelefone(rs.getString("Telefone"));
                m.setTransportadorCpf(rs.getString("Transportador_CPF"));
                lista.add(m);
            }
        }
        return lista;
    }

}
