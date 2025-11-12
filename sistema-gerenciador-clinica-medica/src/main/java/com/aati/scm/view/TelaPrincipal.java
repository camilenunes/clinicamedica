package com.aati.scm.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("🏥 Sistema de Clínica Médica");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Criar o componente de abas
        JTabbedPane abas = new JTabbedPane();

        // Adicionar os módulos (cada um é um JPanel)
        abas.addTab("Pacientes", new PainelPacientes());
        abas.addTab("Médicos", new PainelMedicos());
       // abas.addTab("Consultas", new PainelConsultas());
       // abas.addTab("Prontuário", new PainelProntuario());
        //abas.addTab("Relatórios / Login", new PainelRelatoriosLogin());

        add(abas);
    }
}