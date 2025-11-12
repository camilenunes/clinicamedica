package com.aati.scm.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.aati.scm.config.ConnectionFactory;

import com.aati.scm.model.entity.Medico;



public class MedicoDAO {

    public MedicoDAO() {
        criarTabela();
    }

    // Cria a tabela se não existir
    private void criarTabela() {
        String sql = """
            CREATE TABLE IF NOT EXISTS medicos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                crm TEXT NOT NULL UNIQUE,
                especialidade TEXT,
                telefone TEXT,
                email TEXT,
                endereco TEXT,
                observacoes TEXT
            )
        """;
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    // Inserir médico
    public boolean inserir(Medico medico) {
        String sql = "INSERT INTO medicos(nome, crm, especialidade, telefone, email, endereco, observacoes) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.getTelefone());
            stmt.setString(5, medico.getEmail());
            stmt.setString(6, medico.getEndereco());
            stmt.setString(7, medico.getObservacoes());

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
        String sql = "SELECT * FROM medicos";

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
                m.setEndereco(rs.getString("endereco"));
                m.setObservacoes(rs.getString("observacoes"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar médicos: " + e.getMessage());
        }

        return lista;
    }
}