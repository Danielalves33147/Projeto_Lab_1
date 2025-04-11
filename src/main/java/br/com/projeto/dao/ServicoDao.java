package br.com.projeto.dao;

import br.com.projeto.model.Servico;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicoDao {
    private Connection conn;

    public ServicoDao(Connection conn) {
        this.conn = conn;
    }

    // Verificar a disponibilidade do motorista e caminhão
    public boolean verificarDisponibilidade(String cpfMotorista, String placaCaminhao, LocalDate dataInicio, LocalDate dataTermino) throws SQLException {
        String sql = "SELECT COUNT(*) FROM servico WHERE (Motorista_CPF = ? OR CaminhaoUtilizado = ?) AND (" +
                     "(DataInicio BETWEEN ? AND ?) OR " +
                     "(DataTermino BETWEEN ? AND ?) OR " +
                     "(? BETWEEN DataInicio AND DataTermino) OR " +
                     "(? BETWEEN DataInicio AND DataTermino))";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfMotorista);
            stmt.setString(2, placaCaminhao);
            stmt.setDate(3, Date.valueOf(dataInicio));
            stmt.setDate(4, Date.valueOf(dataTermino));
            stmt.setDate(5, Date.valueOf(dataInicio));
            stmt.setDate(6, Date.valueOf(dataTermino));
            stmt.setDate(7, Date.valueOf(dataInicio));
            stmt.setDate(8, Date.valueOf(dataTermino));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Se não houver sobreposição de datas, retorna true
            }
        }
        return false; // Caso contrário, retorna false
    }

    // Inserir o serviço no banco
    public void inserir(Servico servico) throws SQLException {
        // Atualize a query para incluir idOrcamento na listagem de colunas.
        String sql = "INSERT INTO servico (TipoServico, CaminhaoUtilizado, Motorista_CPF, DataInicio, DataTermino, ValorTotal, Transportador_CPF, descricaoServico, idOrcamento) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servico.getTipoServico());
            stmt.setString(2, servico.getCaminhaoUtilizado());
            stmt.setString(3, servico.getMotoristaCpf());
            stmt.setDate(4, Date.valueOf(servico.getDataInicio()));
            stmt.setDate(5, Date.valueOf(servico.getDataTermino()));
            stmt.setDouble(6, servico.getValorTotal());
            stmt.setString(7, servico.getTransportadorCpf());
            stmt.setString(8, servico.getDescricaoServico());
            
            // Se o idOrcamento for 0, tratamos como null (nenhum orçamento vinculado)
            if(servico.getIdOrcamento() == 0){
                stmt.setNull(9, Types.INTEGER);
            } else {
                stmt.setInt(9, servico.getIdOrcamento());
            }
            
            stmt.executeUpdate();
        }
    }



    
    // Método para listar todos os serviços
    public List<Servico> listarTodos() throws SQLException {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM servico";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Servico s = new Servico();
                s.setIdServico(rs.getInt("idServico"));
                s.setTipoServico(rs.getString("TipoServico"));
                s.setCaminhaoUtilizado(rs.getString("CaminhaoUtilizado"));
                s.setMotoristaCpf(rs.getString("Motorista_CPF"));
                s.setDataInicio(rs.getDate("DataInicio").toLocalDate());
                s.setDataTermino(rs.getDate("DataTermino").toLocalDate());
                s.setValorTotal(rs.getDouble("ValorTotal"));
                s.setTransportadorCpf(rs.getString("Transportador_CPF"));
                s.setDescricaoServico(rs.getString("descricaoServico")); // Adicionando o campo descrição
                lista.add(s);
            }
        }
        return lista;
    }
    
    public Servico buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM servico WHERE idServico = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Servico s = new Servico();
                    s.setIdServico(rs.getInt("idServico"));
                    s.setTipoServico(rs.getString("tipoServico"));
                    s.setCaminhaoUtilizado(rs.getString("CaminhaoUtilizado"));
                    s.setMotoristaCpf(rs.getString("Motorista_CPF"));
                    s.setDataInicio(rs.getDate("DataInicio").toLocalDate());
                    s.setDataTermino(rs.getDate("DataTermino").toLocalDate());
                    s.setValorTotal(rs.getDouble("ValorTotal"));
                    s.setTransportadorCpf(rs.getString("Transportador_CPF"));
                    s.setDescricaoServico(rs.getString("descricaoServico"));
                    s.setIdOrcamento(rs.getInt("idOrcamento")); // se existir o campo
                    return s;
                }
            }
        }
        return null;
    }


}

