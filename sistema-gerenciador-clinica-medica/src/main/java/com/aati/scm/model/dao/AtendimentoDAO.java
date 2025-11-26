package com.aati.scm.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aati.scm.config.ConnectionFactory;
import com.aati.scm.model.entity.Atendimento;
import com.aati.scm.model.entity.Medico;
import com.aati.scm.model.entity.Paciente;

public class AtendimentoDAO {

    public void inserir(Atendimento a) {
        String sql = "INSERT INTO T_SCM_ATENDIMENTO " +
                "(ds_diagnostico, ds_sintomas, ds_observacoes, id_paciente, id_medico) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Preenchendo todos os parâmetros
            stmt.setString(1, a.getDiagnostico());
            stmt.setString(2, a.getSintomas());
            stmt.setString(3, a.getObservacoes());
            stmt.setInt(4, a.getPaciente().getId());
            stmt.setInt(5, a.getMedico().getId());

            stmt.executeUpdate();

            System.out.println("Atendimento cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir atendimento: " + e.getMessage());
        }
    }

    public List<Atendimento> listar() {
        List<Atendimento> lista = new ArrayList<>();

        String sql = "SELECT " +
                "  a.ds_diagnostico, " +
                "  a.ds_sintomas, " +
                "  a.ds_observacoes, " +

                "  m.id AS medico_id, " +
                "  m.nome AS nome_medico, " +
                "  m.crm, " +
                "  m.especialidade, " +
                "  m.telefone AS telefone_medico, " +
                "  m.email AS email_medico, " +

                "  p.id AS paciente_id, " +
                "  p.nome AS nome_paciente, " +
                "  p.cpf, " +
                "  p.telefone AS telefone_paciente, " +
                "  p.endereco AS endereco_paciente, " +
                "  p.historico " +

                "FROM T_SCM_ATENDIMENTO a " +
                "INNER JOIN T_SCM_MEDICOS m ON m.id = a.id_medico " +
                "INNER JOIN T_SCM_PACIENTES p ON p.id = a.id_paciente";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                // ---- Criar o Atendimento ----
                Atendimento at = new Atendimento();
                at.setDiagnostico(rs.getString("ds_diagnostico"));
                at.setSintomas(rs.getString("ds_sintomas"));
                at.setObservacoes(rs.getString("ds_observacoes"));

                // ---- Criar o Médico ----
                Medico m = new Medico();
                m.setId(rs.getInt("medico_id"));
                m.setNome(rs.getString("nome_medico"));
                m.setCrm(rs.getString("crm"));
                m.setEspecialidade(rs.getString("especialidade"));
                m.setTelefone(rs.getString("telefone_medico"));
                m.setEmail(rs.getString("email_medico"));

                // ---- Criar o Paciente ----
                Paciente p = new Paciente();
                p.setId(rs.getInt("paciente_id"));
                p.setNome(rs.getString("nome_paciente"));
                p.setCpf(rs.getString("cpf"));
                p.setTelefone(rs.getString("telefone_paciente"));
                p.setEndereco(rs.getString("endereco_paciente"));
                p.setHistorico(rs.getString("historico"));

                // Associar no Atendimento
                at.setMedico(m);
                at.setPaciente(p);

                lista.add(at);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar atendimentos: " + e.getMessage());
        }

        return lista;
    }

}
