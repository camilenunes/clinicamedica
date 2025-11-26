package com.aati.scm.view;

import java.awt.*;
import javax.swing.*;

import com.aati.scm.model.dao.AgendamentoDAO;
import com.aati.scm.model.dao.AtendimentoDAO;
import com.aati.scm.model.dao.MedicoDAO;
import com.aati.scm.model.entity.*;

public class PainelRelatorio extends JFrame {

    private JComboBox<String> caixaBusca;
    private JButton botaoPesquisar;
    private JTextArea areaResultados;

    private AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    private AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
    private MedicoDAO medicoDAO = new MedicoDAO();

    public PainelRelatorio() {

        setTitle("📊 Tela de Relatórios");
        setSize(700, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ---------------------------
        // TEMA GERAL
        // ---------------------------
        Color fundo = new Color(240, 244, 255);
        Color card = new Color(255, 255, 255);
        Color azul = new Color(52, 120, 235);

        getContentPane().setBackground(fundo);
        setLayout(new GridBagLayout()); // deixa tudo centralizado

        // ---------------------------
        // CARD CENTRAL
        // ---------------------------
        JPanel cardPanel = new JPanel();
        cardPanel.setPreferredSize(new Dimension(620, 430));
        cardPanel.setBackground(card);
        cardPanel.setLayout(new BorderLayout(15, 15));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 245), 1, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        // ---------------------------
        // TÍTULO
        // ---------------------------
        JLabel titulo = new JLabel("Relatórios do Sistema", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(40, 80, 180));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        cardPanel.add(titulo, BorderLayout.NORTH);

        // ---------------------------
        // PAINEL SUPERIOR (opções)
        // ---------------------------
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelSuperior.setBackground(card);

        JLabel labelBusca = new JLabel("Buscar por:");
        labelBusca.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelBusca.setForeground(new Color(60, 70, 100));

        String[] opcoes = { "Pacientes", "Consultas", "Médicos", "Exames" };
        caixaBusca = new JComboBox<>(opcoes);
        caixaBusca.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        caixaBusca.setPreferredSize(new Dimension(150, 30));

        botaoPesquisar = new JButton("🔍 Pesquisar");
        botaoPesquisar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botaoPesquisar.setForeground(Color.WHITE);
        botaoPesquisar.setBackground(azul);
        botaoPesquisar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        botaoPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        painelSuperior.add(labelBusca);
        painelSuperior.add(caixaBusca);
        painelSuperior.add(botaoPesquisar);

        cardPanel.add(painelSuperior, BorderLayout.PAGE_START);

        // ---------------------------
        // RESULTADO DOS RELATÓRIOS
        // ---------------------------
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        areaResultados.setMargin(new Insets(10, 10, 10, 10));
        areaResultados.setBackground(new Color(250, 250, 255));
        areaResultados.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 245), 1, true));

        JScrollPane scroll = new JScrollPane(areaResultados);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        cardPanel.add(scroll, BorderLayout.CENTER);

        // ---------------------------
        // ADICIONA CARD AO FRAME
        // ---------------------------
        add(cardPanel);

        // ---------------------------
        // AÇÃO DO BOTÃO
        // ---------------------------
        botaoPesquisar.addActionListener(e -> pesquisar());

        setVisible(true);
    }

    // ===========================================
    //              FUNÇÃO DE PESQUISA
    // ===========================================
    private void pesquisar() {
        String opcao = (String) caixaBusca.getSelectedItem();
        areaResultados.setText("");

        switch (opcao) {

            case "Pacientes":
                areaResultados.append("📌 Lista de Pacientes:\n\n");
                for (Atendimento a : atendimentoDAO.listar()) {
                    Paciente p = a.getPaciente();
                    areaResultados.append(
                            "ID: " + p.getId() + " | "
                                    + p.getNome() + " | Telefone: " + p.getTelefone() + "\n"
                    );
                }
                break;

            case "Consultas":
                areaResultados.append("📅 Lista de Consultas:\n\n");
                for (Agendamento ag : agendamentoDAO.listar()) {
                    areaResultados.append(
                            "ID: " + ag.getId()
                                    + "\nPaciente: " + ag.getPaciente().getNome()
                                    + "\nMédico: " + ag.getMedico().getNome()
                                    + "\nData/Hora: " + ag.getDataHora()
                                    + "\nStatus: " + ag.getStatus()
                                    + "\n-----------------------------\n"
                    );
                }
                break;

            case "Médicos":
                areaResultados.append("👨‍⚕️ Lista de Médicos:\n\n");
                medicoDAO.listar().forEach(m -> {
                    areaResultados.append(
                            "ID: " + m.getId()
                                    + " | " + m.getNome()
                                    + " | CRM: " + m.getCrm()
                                    + " | Especialidade: " + m.getEspecialidade()
                                    + "\n"
                    );
                });
                break;

            case "Exames":
                areaResultados.append("🧪 Exames encontrados:\n\n");
                for (Atendimento at : atendimentoDAO.listar()) {
                    areaResultados.append(
                            "Paciente: " + at.getPaciente().getNome()
                                    + "\nDiagnóstico: " + at.getDiagnostico()
                                    + "\nSintomas: " + at.getSintomas()
                                    + "\nObservações: " + at.getObservacoes()
                                    + "\n---------------------------\n"
                    );
                }
                break;
        }
    }
}

