package com.aati.scm.view;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() throws SQLException {
        setTitle("🏥 Sistema de Clínica Médica");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Criar o componente de abas
        JTabbedPane abas = new JTabbedPane();

        // Adicionar os módulos (cada um é um JPanel)
        abas.addTab("Cadastro usuário", new PainelCadastro());
        abas.addTab("Login", new PainelLogin());
        abas.addTab("Pacientes", new PainelPacientes());
        abas.addTab("Médicos", new PainelMedicos());
        abas.addTab("Consultas", new PainelAgendamentos());
        abas.addTab("Prontuário", new PainelAtendimentos());
        add(abas);
    }
}