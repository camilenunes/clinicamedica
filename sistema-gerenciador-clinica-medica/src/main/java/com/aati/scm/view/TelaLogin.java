package com.aati.scm.view;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class TelaLogin extends JFrame {

    public TelaLogin() throws SQLException {
        setTitle("🏥 Sistema de Clínica Médica");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Criar o componente de abas
        JTabbedPane abas = new JTabbedPane();

        // Adicionar os módulos (cada um é um JPanel)
        abas.addTab("Login", new PainelLogin());
        abas.addTab("Cadastro usuário", new PainelCadastro());
        
        add(abas);
    }

}
