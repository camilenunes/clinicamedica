package com.aati.scm.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aati.scm.config.ConnectionFactory;
import com.aati.scm.model.entity.Agendamento;
import com.aati.scm.model.entity.Medico;
import com.aati.scm.model.entity.Paciente;

public class AgendamentoDAO {

    public AgendamentoDAO() {}

    // ============================================================
    // INSERIR NOVA CONSULTA
    // ============================================================
    public boolean inserir(Agendamento agendamento) {
        String sql = """
            INSERT INTO T_SCM_CONSULTAS 
            (paciente_id, medico_id, data_hora, status, sintomas, diagnostico, observacoes)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, agendamento.getPaciente().getId());
            stmt.setInt(2, agendamento.getMedico().getId());
            stmt.setTimestamp(3, agendamento.getDataHora());
            stmt.setString(4, agendamento.getStatus());
            stmt.setString(5, agendamento.getSintomas());
            stmt.setString(6, agendamento.getDiagnostico());
            stmt.setString(7, agendamento.getObservacoes());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir agendamento: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // LISTAR TODAS AS CONSULTAS
    // ============================================================
    public List<Agendamento> listar() {
        List<Agendamento> lista = new ArrayList<>();

        String sql = """
            SELECT c.*, p.nome AS nome_paciente, m.nome AS nome_medico
            FROM T_SCM_CONSULTAS c
            JOIN T_SCM_PACIENTES p ON c.paciente_id = p.id
            JOIN T_SCM_MEDICOS m ON c.medico_id = m.id
            ORDER BY c.data_hora ASC
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Agendamento a = new Agendamento();

                a.setId(rs.getInt("id"));
                a.setDataHora(rs.getTimestamp("data_hora"));
                a.setStatus(rs.getString("status"));
                a.setSintomas(rs.getString("sintomas"));
                a.setDiagnostico(rs.getString("diagnostico"));
                a.setObservacoes(rs.getString("observacoes"));
                a.setCreatedAt(rs.getTimestamp("created_at"));

                // Paciente
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("paciente_id"));
                paciente.setNome(rs.getString("nome_paciente"));
                a.setPaciente(paciente);

                // Médico
                Medico medico = new Medico();
                medico.setId(rs.getInt("medico_id"));
                medico.setNome(rs.getString("nome_medico"));
                a.setMedico(medico);

                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar agendamentos: " + e.getMessage());
        }

        return lista;
    }

    // ============================================================
    // BUSCAR CONSULTA POR ID
    // ============================================================
    public Agendamento buscarPorId(int id) {
        String sql = """
            SELECT c.*, p.nome AS nome_paciente, m.nome AS nome_medico
            FROM T_SCM_CONSULTAS c
            JOIN T_SCM_PACIENTES p ON c.paciente_id = p.id
            JOIN T_SCM_MEDICOS m ON c.medico_id = m.id
            WHERE c.id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Agendamento a = new Agendamento();
                a.setId(rs.getInt("id"));
                a.setDataHora(rs.getTimestamp("data_hora"));
                a.setStatus(rs.getString("status"));
                a.setSintomas(rs.getString("sintomas"));
                a.setDiagnostico(rs.getString("diagnostico"));
                a.setObservacoes(rs.getString("observacoes"));
                a.setCreatedAt(rs.getTimestamp("created_at"));

                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("paciente_id"));
                paciente.setNome(rs.getString("nome_paciente"));
                a.setPaciente(paciente);

                Medico medico = new Medico();
                medico.setId(rs.getInt("medico_id"));
                medico.setNome(rs.getString("nome_medico"));
                a.setMedico(medico);

                return a;
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar agendamento por ID: " + e.getMessage());
        }

        return null;
    }

    // ============================================================
    // ATUALIZAR CONSULTA
    // ============================================================
    public boolean atualizar(Agendamento agendamento) {
        String sql = """
            UPDATE T_SCM_CONSULTAS
            SET paciente_id = ?, medico_id = ?, data_hora = ?, status = ?, 
                sintomas = ?, diagnostico = ?, observacoes = ?
            WHERE id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, agendamento.getPaciente().getId());
            stmt.setInt(2, agendamento.getMedico().getId());
            stmt.setTimestamp(3, agendamento.getDataHora());
            stmt.setString(4, agendamento.getStatus());
            stmt.setString(5, agendamento.getSintomas());
            stmt.setString(6, agendamento.getDiagnostico());
            stmt.setString(7, agendamento.getObservacoes());
            stmt.setInt(8, agendamento.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar agendamento: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // DELETAR CONSULTA
    // ============================================================
    public boolean deletar(int id) {
        String sql = "DELETE FROM T_SCM_CONSULTAS WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Erro ao deletar agendamento: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // LISTAR AGENDAMENTOS DE UM MÉDICO ESPECÍFICO
    // ============================================================
    public List<Agendamento> listarPorMedico(int medicoId) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = """
            SELECT c.*, p.nome AS nome_paciente, m.nome AS nome_medico
            FROM T_SCM_CONSULTAS c
            JOIN T_SCM_PACIENTES p ON c.paciente_id = p.id
            JOIN T_SCM_MEDICOS m ON c.medico_id = m.id
            WHERE c.medico_id = ?
            ORDER BY c.data_hora ASC
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, medicoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Agendamento a = new Agendamento();

                a.setId(rs.getInt("id"));
                a.setDataHora(rs.getTimestamp("data_hora"));
                a.setStatus(rs.getString("status"));
                a.setSintomas(rs.getString("sintomas"));
                a.setDiagnostico(rs.getString("diagnostico"));
                a.setObservacoes(rs.getString("observacoes"));
                a.setCreatedAt(rs.getTimestamp("created_at"));

                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("paciente_id"));
                paciente.setNome(rs.getString("nome_paciente"));
                a.setPaciente(paciente);

                Medico medico = new Medico();
                medico.setId(rs.getInt("medico_id"));
                medico.setNome(rs.getString("nome_medico"));
                a.setMedico(medico);

                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar agendamentos do médico: " + e.getMessage());
        }

        return lista;
    }
}
