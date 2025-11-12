package com.aati.scm.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.aati.scm.config.ConnectionFactory;
import com.aati.scm.model.entity.Paciente;


public class PacienteDAO {

    // Inserir novo paciente
    public void inserir(Paciente p) {
        String sql = "INSERT INTO T_SCM_PACIENTES (nome, cpf, telefone, endereco, historico) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getTelefone());
            stmt.setString(4, p.getEndereco());
            stmt.setString(5, p.getHistorico());

            stmt.executeUpdate();

            System.out.println("Paciente cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir paciente: " + e.getMessage());
        }
    }

    // Listar pacientes
    public List<Paciente> listar() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_SCM_PACIENTES ORDER BY nome";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setTelefone(rs.getString("telefone"));
                p.setEndereco(rs.getString("endereco"));
                p.setHistorico(rs.getString("historico"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pacientes: " + e.getMessage());
        }

        return lista;
    }
}