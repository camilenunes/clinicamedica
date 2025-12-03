package com.aati.scm.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.List;

import com.aati.scm.model.dao.AgendamentoDAO;
import com.aati.scm.model.dao.MedicoDAO;
import com.aati.scm.model.dao.PacienteDAO;
import com.aati.scm.model.entity.Agendamento;
import com.aati.scm.model.entity.Medico;
import com.aati.scm.model.entity.Paciente;

public class PainelAgendamento extends JPanel {

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JTextField txtId, txtDataHora, txtStatus, txtSintomas, txtDiagnostico, txtObservacoes;

    private JComboBox<Paciente> comboBoxPacientes = new JComboBox<>();
    private JComboBox<Medico> comboBoxMedicos = new JComboBox<>();

    private JButton btnAdicionar, btnAtualizar, btnExcluir, btnRecarregar, btnLimpar;

    private AgendamentoDAO dao = new AgendamentoDAO();
    private PacienteDAO pacienteDAO = new PacienteDAO();
    private MedicoDAO medicoDAO = new MedicoDAO();

    public PainelAgendamento() {

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // ======================================================
        // TABELA ESQUERDA
        // ======================================================

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Paciente", "Médico", "Data/Hora", "Status", "Sintomas", "Diagnóstico"},
                0
        );

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(28);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(450, 600));

        add(scroll, BorderLayout.WEST);

        // ======================================================
        // PAINEL DIREITO (FORMULÁRIO)
        // ======================================================

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBorder(BorderFactory.createTitledBorder("Dados do Agendamento"));
        painelForm.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 14);

        txtId = new JTextField();
        txtId.setEnabled(false);
        txtId.setFont(fonteCampo);

        txtDataHora = new JTextField();
        txtDataHora.setFont(fonteCampo);

        txtStatus = new JTextField("agendada");
        txtStatus.setFont(fonteCampo);

        txtSintomas = new JTextField();
        txtSintomas.setFont(fonteCampo);

        txtDiagnostico = new JTextField();
        txtDiagnostico.setFont(fonteCampo);

        txtObservacoes = new JTextField();
        txtObservacoes.setFont(fonteCampo);

        pacienteDAO.listar().forEach(comboBoxPacientes::addItem);
        medicoDAO.listar().forEach(comboBoxMedicos::addItem);

        comboBoxPacientes.setFont(fonteCampo);
        comboBoxMedicos.setFont(fonteCampo);

        // Função para adicionar linha
        int y = 0;
        addLinha(painelForm, gbc, y++, "ID:", txtId);
        addLinha(painelForm, gbc, y++, "ID Paciente:", comboBoxPacientes);
        addLinha(painelForm, gbc, y++, "ID Médico:", comboBoxMedicos);
        addLinha(painelForm, gbc, y++, "Data/Hora (AAAA-MM-DD HH:MM:SS):", txtDataHora);
        addLinha(painelForm, gbc, y++, "Status:", txtStatus);
        addLinha(painelForm, gbc, y++, "Sintomas:", txtSintomas);
        addLinha(painelForm, gbc, y++, "Diagnóstico:", txtDiagnostico);
        addLinha(painelForm, gbc, y++, "Observações:", txtObservacoes);

        add(painelForm, BorderLayout.CENTER);

        // ======================================================
        // BOTÕES INFERIORES
        // ======================================================

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setBackground(Color.WHITE);

        btnAdicionar = new JButton("Adicionar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnRecarregar = new JButton("Recarregar");
        btnLimpar = new JButton("Limpar Campos");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnRecarregar);
        painelBotoes.add(btnLimpar);

        add(painelBotoes, BorderLayout.SOUTH);

        // ======================================================
        // EVENTOS
        // ======================================================

        carregarAgendamentos();

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tabela.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(modeloTabela.getValueAt(row, 0).toString());
                    comboBoxPacientes.setSelectedItem(buscaPacienteTabela(row));
                    comboBoxMedicos.setSelectedItem(buscaMedicoTabela(row));
                    txtDataHora.setText(modeloTabela.getValueAt(row, 3).toString());
                    txtStatus.setText(modeloTabela.getValueAt(row, 4).toString());
                    txtSintomas.setText(modeloTabela.getValueAt(row, 5).toString());
                    txtDiagnostico.setText(modeloTabela.getValueAt(row, 6).toString());
                }
            }
        });

        btnAdicionar.addActionListener(e -> adicionarAgendamento());
        btnAtualizar.addActionListener(e -> atualizarAgendamento());
        btnExcluir.addActionListener(e -> excluirAgendamento());
        btnRecarregar.addActionListener(e -> carregarAgendamentos());
        btnLimpar.addActionListener(e -> limparCampos());
    }

    // ------------------------------------------------------
    // Método auxiliar para montar formulário
    // ------------------------------------------------------
    private void addLinha(JPanel p, GridBagConstraints gbc, int y, String label, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0.2;
        p.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        p.add(campo, gbc);
    }

    // ------------------------------------------------------
    // LISTAR AGENDAMENTOS
    // ------------------------------------------------------
    private void carregarAgendamentos() {
        modeloTabela.setRowCount(0);
        List<Agendamento> lista = dao.listar();
        for (Agendamento a : lista) {
            modeloTabela.addRow(new Object[]{
                    a.getId(),
                    a.getPaciente(),
                    a.getMedico(),
                    a.getDataHora(),
                    a.getStatus(),
                    a.getSintomas(),
                    a.getDiagnostico()
            });
        }
    }

    // ------------------------------------------------------
    // ADICIONAR
    // ------------------------------------------------------
    private void adicionarAgendamento() {
        try {
            Agendamento a = new Agendamento();
            a.setPaciente((Paciente) comboBoxPacientes.getSelectedItem());
            a.setMedico((Medico) comboBoxMedicos.getSelectedItem());
            a.setDataHora(Timestamp.valueOf(txtDataHora.getText()));
            a.setStatus(txtStatus.getText());
            a.setSintomas(txtSintomas.getText());
            a.setDiagnostico(txtDiagnostico.getText());
            a.setObservacoes(txtObservacoes.getText());

            if (dao.inserir(a)) {
                JOptionPane.showMessageDialog(this, "Agendamento criado!");
                carregarAgendamentos();
                limparCampos();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    // ------------------------------------------------------
    // ATUALIZAR
    // ------------------------------------------------------
    private void atualizarAgendamento() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um item!");
            return;
        }

        try {
            Agendamento a = new Agendamento();
            a.setId(Integer.parseInt(txtId.getText()));
            a.setPaciente((Paciente) comboBoxPacientes.getSelectedItem());
            a.setMedico((Medico) comboBoxMedicos.getSelectedItem());
            a.setDataHora(Timestamp.valueOf(txtDataHora.getText()));
            a.setStatus(txtStatus.getText());
            a.setSintomas(txtSintomas.getText());
            a.setDiagnostico(txtDiagnostico.getText());
            a.setObservacoes(txtObservacoes.getText());

            if (dao.atualizar(a)) {
                JOptionPane.showMessageDialog(this, "Atualizado!");
                carregarAgendamentos();
                limparCampos();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    // ------------------------------------------------------
    // EXCLUIR
    // ------------------------------------------------------
    private void excluirAgendamento() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione algo!");
            return;
        }

        int id = Integer.parseInt(txtId.getText());

        if (dao.deletar(id)) {
            JOptionPane.showMessageDialog(this, "Excluído!");
            carregarAgendamentos();
            limparCampos();
        }
    }

    private void limparCampos() {
        txtId.setText("");
        comboBoxPacientes.setSelectedItem(null);
        comboBoxMedicos.setSelectedItem(null);
        txtDataHora.setText("");
        txtStatus.setText("agendada");
        txtSintomas.setText("");
        txtDiagnostico.setText("");
        txtObservacoes.setText("");
    }

    // Para selecionar corretamente paciente/medico ao clicar na tabela
    private Paciente buscaPacienteTabela(int row) {
        return pacienteDAO.listar().stream()
                .filter(p -> modeloTabela.getValueAt(row, 1).toString().contains(String.valueOf(p.getId())))
                .findFirst().orElse(null);
    }

    private Medico buscaMedicoTabela(int row) {
        return medicoDAO.listar().stream()
                .filter(m -> modeloTabela.getValueAt(row, 2).toString().contains(String.valueOf(m.getId())))
                .findFirst().orElse(null);
    }
}
