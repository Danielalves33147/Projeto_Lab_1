package br.com.projeto.dao;

import br.com.projeto.model.Servico;
import br.com.projeto.model.Motorista;
import br.com.projeto.model.Caminhao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoHasMotoristaDao {
    private Connection conn;

    public ServicoHasMotoristaDao(Connection conn) {
        this.conn = conn;
    }

    // Insere a associação entre serviço, motorista (CPF) e caminhão (Placa)
    public void inserir(Servico servico, Motorista motorista, Caminhao caminhao) throws SQLException {
        // Verificar se a associação já existe (para evitar duplicidade)
        String checkSql = "SELECT COUNT(*) FROM servico_has_motorista WHERE Servico_idServico = ? AND Motorista_CPF = ? AND Caminhao_Placa = ?";
        try (PreparedStatement stmtCheck = conn.prepareStatement(checkSql)) {
            stmtCheck.setInt(1, servico.getIdServico());
            stmtCheck.setString(2, motorista.getCpf());
            stmtCheck.setString(3, caminhao.getPlaca());
            ResultSet rsCheck = stmtCheck.executeQuery();
            if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                throw new SQLException("Associação entre serviço, motorista e caminhão já existe.");
            }
        }

        // Inserir a nova associação
        String sql = "INSERT INTO servico_has_motorista (Servico_idServico, Motorista_CPF, Caminhao_Placa) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, servico.getIdServico());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, caminhao.getPlaca());
            stmt.executeUpdate();
        }
    }

    // Remove associação entre serviço e motorista (por CPF)
    public void deletar(int idServico, String cpfMotorista) throws SQLException {
        String sql = "DELETE FROM servico_has_motorista WHERE Servico_idServico = ? AND Motorista_CPF = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idServico);
            stmt.setString(2, cpfMotorista);
            stmt.executeUpdate();
        }
    }

    // Lista motoristas associados a um serviço
    public List<Motorista> listarMotoristasPorServico(int idServico) throws SQLException {
        List<Motorista> motoristas = new ArrayList<>();
        String sql = """
            SELECT m.* FROM motorista m
            INNER JOIN servico_has_motorista shm ON m.CPF = shm.Motorista_CPF
            WHERE shm.Servico_idServico = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idServico);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Motorista m = new Motorista();
                m.setCpf(rs.getString("CPF"));
                m.setNomeMotorista(rs.getString("NomeMotorista"));
                m.setCnh(rs.getString("CNH"));
                m.setTelefone(rs.getString("Telefone"));
                motoristas.add(m);
            }
        }
        return motoristas;
    }

 // Lista serviços associados a um motorista (por CPF)
    public List<Servico> listarServicosPorMotorista(String cpf) throws SQLException {
        List<Servico> servicos = new ArrayList<>();
        String sql = """
            SELECT s.* FROM servico s
            INNER JOIN servico_has_motorista shm ON s.idServico = shm.Servico_idServico
            WHERE shm.Motorista_CPF = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf); // Alterado para cpf
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Servico s = new Servico();
                s.setIdServico(rs.getInt("idServico"));
                s.setTipoServico(rs.getString("TipoServico"));
                s.setLocalOrigem(rs.getString("LocalOrigem"));
                s.setLocalDestino(rs.getString("LocalDestino"));
                s.setDistancia(rs.getDouble("Distancia"));
                s.setCaminhaoUtilizado(rs.getString("CaminhaoUtilizado"));
                s.setDataExecucao(rs.getDate("DataExecucao").toLocalDate());
                servicos.add(s);
            }
        }
        return servicos;
    }
}
