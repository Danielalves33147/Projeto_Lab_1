package br.com.projeto.dao;

import br.com.projeto.model.ServicoHasMotorista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoHasMotoristaDao {
    private final Connection connection;

    public ServicoHasMotoristaDao(Connection connection) {
        this.connection = connection;
    }

    // Inserção de vínculo entre Serviço, Motorista e Caminhão
    public void inserir(ServicoHasMotorista s) throws SQLException {
        String sql = "INSERT INTO servico_has_motorista (idServico, motorista_cpf, caminhao_placa) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, s.getIdServico());
            stmt.setString(2, s.getMotoristaCpf());
            stmt.setString(3, s.getCaminhaoPlaca());
            stmt.executeUpdate();
        }
    }

    // Listagem completa de todos os vínculos
    public List<ServicoHasMotorista> listarTodos() throws SQLException {
        List<ServicoHasMotorista> lista = new ArrayList<>();
        String sql = "SELECT * FROM servico_has_motorista";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ServicoHasMotorista s = new ServicoHasMotorista();
                s.setIdServico(rs.getInt("idServico"));
                s.setMotoristaCpf(rs.getString("motorista_cpf"));
                s.setCaminhaoPlaca(rs.getString("caminhao_placa"));
                lista.add(s);
            }
        }
        return lista;
    }

    // Deletar vínculo específico
    public void deletar(int idServico, String motoristaCpf, String caminhaoPlaca) throws SQLException {
        String sql = "DELETE FROM servico_has_motorista WHERE idServico=? AND motorista_cpf=? AND caminhao_placa=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idServico);
            stmt.setString(2, motoristaCpf);
            stmt.setString(3, caminhaoPlaca);
            stmt.executeUpdate();
        }
    }

    // Buscar todos os motoristas e caminhões vinculados a um serviço específico
    public List<ServicoHasMotorista> buscarPorServico(int idServico) throws SQLException {
        List<ServicoHasMotorista> lista = new ArrayList<>();
        String sql = "SELECT * FROM servico_has_motorista WHERE idServico=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idServico);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ServicoHasMotorista s = new ServicoHasMotorista();
                    s.setIdServico(rs.getInt("idServico"));
                    s.setMotoristaCpf(rs.getString("motorista_cpf"));
                    s.setCaminhaoPlaca(rs.getString("caminhao_placa"));
                    lista.add(s);
                }
            }
        }
        return lista;
    }
    
    public void deletarPorMotorista(String cpf) throws SQLException {
        String sql = "DELETE FROM servico_has_motorista WHERE motorista_cpf = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cpf);
        stmt.executeUpdate();
        stmt.close();
    }

    
    public boolean existeVinculoUsado(String motoristaCpf, String caminhaoPlaca) throws SQLException {
        String sql = "SELECT COUNT(*) FROM servico_has_motorista WHERE motorista_cpf = ? AND caminhao_placa = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, motoristaCpf);
        stmt.setString(2, caminhaoPlaca);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
    
    


}
