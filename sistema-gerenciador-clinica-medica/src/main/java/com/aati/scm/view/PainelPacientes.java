package com.aati.scm.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import com.aati.scm.model.dao.PacienteDAO;
import com.aati.scm.model.entity.Paciente;

public class PainelPacientes extends JPanel {

    private JTextField txtNome, txtCpf, txtTelefone, txtEndereco;
    private JTextArea txtHistorico;

    private JTable tabela;
    private DefaultTableModel modelo;
    private final PacienteDAO pacienteDAO;

    public PainelPacientes() {
        pacienteDAO = new PacienteDAO();
        initComponents();
        atualizarTabela();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);

        // ---------- PAINEL DE FORMULÁRIO ----------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Pacientes"));
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Labels mais largos para alinhar
        Dimension labelSize = new Dimension(120, 25);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setPreferredSize(labelSize);

        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setPreferredSize(labelSize);

        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setPreferredSize(labelSize);

        JLabel lblEndereco = new JLabel("Endereço:");
        lblEndereco.setPreferredSize(labelSize);

        JLabel lblHistorico = new JLabel("Histórico:");
        lblHistorico.setPreferredSize(labelSize);

        // Campos bem maiores
        txtNome = new JTextField(28);
        txtCpf = new JTextField(20);
        txtTelefone = new JTextField(20);
        txtEndereco = new JTextField(28);

        txtHistorico = new JTextArea(5, 28);
        txtHistorico.setLineWrap(true);
        txtHistorico.setWrapStyleWord(true);
        JScrollPane scrollHistorico = new JScrollPane(txtHistorico);

        // ------- LINHAS DO FORMULÁRIO --------

        // Nome
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        formPanel.add(lblNome, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(txtNome, gbc);

        // CPF
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
        formPanel.add(lblCpf, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(txtCpf, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.1;
        formPanel.add(lblTelefone, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(txtTelefone, gbc);

        // Endereço
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.1;
        formPanel.add(lblEndereco, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(txtEndereco, gbc);

        // Histórico — ocupa mais espaço vertical
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.NORTH;
        formPanel.add(lblHistorico, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(scrollHistorico, gbc);

        // ---------- BOTÕES ----------
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnLimpar);

        gbc.gridx = 0; gbc.gridy = 5; 
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(botoesPanel, gbc);

        // ---------- TABELA ----------
        modelo = new DefaultTableModel(new Object[]{
            "ID", "Nome", "CPF", "Telefone", "Endereço", "Histórico"
        }, 0);

        tabela = new JTable(modelo);
        tabela.setRowHeight(22);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBorder(BorderFactory.createTitledBorder("Lista de Pacientes"));

        // ---------- EVENTOS ----------
        btnSalvar.addActionListener(e -> salvarPaciente());
        btnLimpar.addActionListener(e -> limparCampos());

        add(formPanel, BorderLayout.NORTH);
        add(scrollTabela, BorderLayout.CENTER);
    }

    private void salvarPaciente() {
        Paciente p = new Paciente(
            txtNome.getText(),
            txtCpf.getText(),
            txtTelefone.getText(),
            txtEndereco.getText(),
            txtHistorico.getText()
        );

        if (p.getNome().isEmpty() || p.getCpf().length() != 11) {
            JOptionPane.showMessageDialog(this, "Preencha nome e CPF (11 dígitos).");
            return;
        }

        pacienteDAO.inserir(p);
        JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!");
        limparCampos();
        atualizarTabela();
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtHistorico.setText("");
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);

        for (Paciente p : pacienteDAO.listar()) {
            modelo.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getCpf(),
                p.getTelefone(),
                p.getEndereco(),
                p.getHistorico()
            });
        }
    }
}
