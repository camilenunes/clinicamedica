package com.aati.scm.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.aati.scm.config.ConnectionFactory;

import com.aati.scm.model.entity.Medico;



public class MedicoDAO {

    public MedicoDAO() {
        
    }

    

    // Inserir médico
    public boolean inserir(Medico medico) {
        String sql = "INSERT INTO T_SCM_MEDICOS(nome, crm, especialidade, telefone, email) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.getTelefone());
            stmt.setString(5, medico.getEmail());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir médico: " + e.getMessage());
            return false;
        }
    }

    // Listar médicos
    public List<Medico> listar() {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_SCM_MEDICOS";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setCrm(rs.getString("crm"));
                m.setEspecialidade(rs.getString("especialidade"));
                m.setTelefone(rs.getString("telefone"));
                m.setEmail(rs.getString("email"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar médicos: " + e.getMessage());
        }

        return lista;
    }
}