package com.aati.scm.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aati.scm.config.ConnectionFactory;
import com.aati.scm.model.entity.Atendimento;
import com.aati.scm.model.entity.Paciente;

public class AtendimentoDAO {

    // Inserir novo paciente
    public void inserir(Atendimento a) {
        String sql = "INSERT INTO T_SCM_PACIENTES (nome, cpf, telefone, endereco, historico) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();

            System.out.println("Paciente cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir paciente: " + e.getMessage());
        }
    }

    // Listar pacientes
    public List<Atendimento> listar() {
        List<Atendimento> lista = new ArrayList<>();
        String sql = "SELECT a.*, m.* FROM T_SCM_ATENDIMENTO a inner join T_SCM_MEDICOS m on m.id = a.id_medico inner join T_SCM_PACIENTES p on p.id = a.id_paciente";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Atendimento a = new Atendimento();
                
                //a.set 

                //lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pacientes: " + e.getMessage());
        }

        return lista;
    }
}
