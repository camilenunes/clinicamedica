package com.aati.scm.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.aati.scm.model.dao.MedicoDAO;
import com.aati.scm.model.entity.Medico;

public class PainelMedicos extends JPanel {

    private JTextField txtNome;
    private JTextField txtCRM;
    private JTextField txtEspecialidade;
    private JTextField txtTelefone;
    private JTextField txtEmail;


    private JTable tabela;
    private DefaultTableModel modelo;
    private final MedicoDAO medicoDAO;

    public PainelMedicos() {
        medicoDAO = new MedicoDAO();
        initComponents();
        atualizarTabela();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // ---------- PAINEL DE FORMULÁRIO ----------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Médicos"));
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCRM = new JLabel("CRM:");
        JLabel lblEspecialidade = new JLabel("Especialidade:");
        JLabel lblTelefone = new JLabel("Telefone:");
        JLabel lblEmail = new JLabel("E-mail:");

        txtNome = new JTextField(20);
        txtCRM = new JTextField(10);
        txtEspecialidade = new JTextField(15);
        txtTelefone = new JTextField(12);
        txtEmail = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblNome, gbc);
        gbc.gridx = 1; formPanel.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblCRM, gbc);
        gbc.gridx = 1; formPanel.add(txtCRM, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblEspecialidade, gbc);
        gbc.gridx = 1; formPanel.add(txtEspecialidade, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblTelefone, gbc);
        gbc.gridx = 1; formPanel.add(txtTelefone, gbc);

        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(lblEmail, gbc);
        gbc.gridx = 1; formPanel.add(txtEmail, gbc);



        // ---------- BOTÕES ----------
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnLimpar);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        formPanel.add(botoesPanel, gbc);

        // ---------- TABELA ----------
        modelo = new DefaultTableModel(new Object[]{
            "ID", "Nome", "CRM", "Especialidade", "Telefone", "E-mail"
        }, 0);
        tabela = new JTable(modelo);
        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBorder(BorderFactory.createTitledBorder("Lista de Médicos"));

        // ---------- EVENTOS ----------
        btnSalvar.addActionListener(this::salvarMedico);
        btnLimpar.addActionListener(e -> limparCampos());

        // ---------- ADICIONAR AO PAINEL PRINCIPAL ----------
        add(formPanel, BorderLayout.NORTH);
        add(scrollTabela, BorderLayout.CENTER);
    }

    // Salvar médico
    private void salvarMedico(ActionEvent e) {
        try {
            Medico m = new Medico();
            m.setNome(txtNome.getText());
            m.setCrm(txtCRM.getText());
            m.setEspecialidade(txtEspecialidade.getText());
            m.setTelefone(txtTelefone.getText());
            m.setEmail(txtEmail.getText());
            medicoDAO.inserir(m);
            JOptionPane.showMessageDialog(this, "Médico cadastrado com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar médico: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCRM.setText("");
        txtEspecialidade.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");

    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        for (Medico m : medicoDAO.listar()) {
            modelo.addRow(new Object[]{
                m.getId(), m.getNome(), m.getCrm(),
                m.getEspecialidade(), m.getTelefone(), m.getEmail()
            });
        }
    }
}