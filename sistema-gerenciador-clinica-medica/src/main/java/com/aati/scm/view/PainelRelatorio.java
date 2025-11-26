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

    // Instâncias DAO
    private AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    private AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
    private MedicoDAO medicoDAO = new MedicoDAO();

    public PainelRelatorio() {
        setTitle("Tela de Relatórios");
        setSize(650, 470);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(245, 248, 255));

        JLabel titulo = new JLabel("Relatórios do Sistema", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(40, 80, 180));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel painelSuperior = new JPanel(new FlowLayout());
        painelSuperior.setBackground(Color.WHITE);

        JLabel labelBusca = new JLabel("Buscar por:");
        labelBusca.setFont(new Font("Segoe UI", Font.BOLD, 14));

        String[] opcoes = { "Pacientes", "Consultas", "Médicos", "Exames" };
        caixaBusca = new JComboBox<>(opcoes);

        botaoPesquisar = criarBotao("🔍 Pesquisar", new Color(52, 120, 235));
        botaoPesquisar.addActionListener(e -> pesquisar());

        painelSuperior.add(labelBusca);
        painelSuperior.add(caixaBusca);
        painelSuperior.add(botaoPesquisar);

        add(painelSuperior, BorderLayout.NORTH);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(areaResultados);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

    // ============================
    //    FUNÇÃO DE PESQUISA
    // ============================
    private void pesquisar() {
        String opcao = (String) caixaBusca.getSelectedItem();
        areaResultados.setText("");

        switch (opcao) {

            // ============================
            //          PACIENTES
            // ============================
            case "Pacientes":
                areaResultados.append("📌 Lista de Pacientes:\n\n");
                for (Atendimento a : atendimentoDAO.listar()) {
                    Paciente p = a.getPaciente();
                    areaResultados.append(
                        "ID: " + p.getId() + " | " +
                        p.getNome() + " | Telefone: " + p.getTelefone() +
                        "\n"
                    );
                }
                break;

            // ============================
            //        CONSULTAS
            // ============================
            case "Consultas":
                areaResultados.append("📅 Lista de Consultas:\n\n");
                for (Agendamento ag : agendamentoDAO.listar()) {
                    areaResultados.append(
                        "ID: " + ag.getId() +
                        "\nPaciente: " + ag.getPaciente().getNome() +
                        "\nMédico: " + ag.getMedico().getNome() +
                        "\nData/Hora: " + ag.getDataHora() +
                        "\nStatus: " + ag.getStatus() +
                        "\n-----------------------------\n"
                    );
                }
                break;

            // ============================
            //          MÉDICOS
            // ============================
            case "Médicos":
                areaResultados.append("👨‍⚕️ Lista de Médicos:\n\n");
                medicoDAO.listar().forEach(m -> {
                    areaResultados.append(
                        "ID: " + m.getId() +
                        " | " + m.getNome() +
                        " | CRM: " + m.getCrm() +
                        " | Especialidade: " + m.getEspecialidade() +
                        "\n"
                    );
                });
                break;

            // ============================
            //          EXAMES
            // Obs: nenhum DAO de exames foi fornecido,
            // vou buscar nos atendimentos.
            // ============================
            case "Exames":
                areaResultados.append("🧪 Exames encontrados:\n\n");
                for (Atendimento at : atendimentoDAO.listar()) {
                    areaResultados.append(
                        "Paciente: " + at.getPaciente().getNome() +
                        "\nDiagnóstico: " + at.getDiagnostico() +
                        "\nSintomas: " + at.getSintomas() +
                        "\nObservações: " + at.getObservacoes() +
                        "\n---------------------------\n"
                    );
                }
                break;
        }
    }
}
