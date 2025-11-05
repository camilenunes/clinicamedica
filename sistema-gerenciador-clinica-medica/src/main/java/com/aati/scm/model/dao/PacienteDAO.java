package com.aati.scm.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.aati.scm.config.ConnectionFactory;
import com.aati.scm.model.entity.Paciente;

public class PacienteDAO {

    public void criarPaciente(Paciente paciente) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO T_SCM_PACIENTE  NM_PACIENTE, CPF_PACIENTE, TEL_PACIENTE, END_PACIENTE, HIST_PACIENTE  VALUES  (?,?,?,?,?)";

            PreparedStatement pr = conn.prepareStatement(sql);

            pr.setString(1, paciente.getNome());
            pr.setString(2, paciente.getCpf());
            pr.setString(3, paciente.getTelefone());
            pr.setString(4, paciente.getEndereco());
            pr.setString(5, paciente.getHistorico());
            

            pr.executeUpdate();

            System.out.println("Produto inserido!");
        } catch (SQLException e) {

        }

    }

}
