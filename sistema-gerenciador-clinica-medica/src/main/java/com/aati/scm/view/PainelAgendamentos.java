package com.aati.scm.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.List;

import com.aati.scm.model.dao.AgendamentoDAO;
import com.aati.scm.model.entity.Agendamento;
import com.aati.scm.model.entity.Medico;
import com.aati.scm.model.entity.Paciente;

public class PainelAgendamentos extends JPanel {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtId, txtPacienteId, txtMedicoId, txtDataHora, txtStatus, txtSintomas, txtDiagnostico, txtObservacoes;
    private JButton btnAdicionar, btnAtualizar, btnExcluir, btnLimpar, btnRecarregar;
    private AgendamentoDAO dao;

    public PainelAgendamentos() {
        dao = new AgendamentoDAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // ---------- TABELA ----------
        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Paciente", "Médico", "Data/Hora", "Status", "Sintomas", "Diagnóstico"},
                0
        );
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        // ---------- FORMULÁRIO ----------
        JPanel painelForm = new JPanel(new GridLayout(8, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createTitledBorder("Dados do Agendamento"));

        txtId = new JTextField();
        txtId.setEnabled(false);
        txtPacienteId = new JTextField();
        txtMedicoId = new JTextField();
        txtDataHora = new JTextField();
        txtStatus = new JTextField("agendada");
        txtSintomas = new JTextField();
        txtDiagnostico = new JTextField();
        txtObservacoes = new JTextField();

        painelForm.add(new JLabel("ID:"));
        painelForm.add(txtId);
        painelForm.add(new JLabel("ID Paciente:"));
        painelForm.add(txtPacienteId);
        painelForm.add(new JLabel("ID Médico:"));
        painelForm.add(txtMedicoId);
        painelForm.add(new JLabel("Data/Hora (AAAA-MM-DD HH:MM:SS):"));
        painelForm.add(txtDataHora);
        painelForm.add(new JLabel("Status:"));
        painelForm.add(txtStatus);
        painelForm.add(new JLabel("Sintomas:"));
        painelForm.add(txtSintomas);
        painelForm.add(new JLabel("Diagnóstico:"));
        painelForm.add(txtDiagnostico);
        painelForm.add(new JLabel("Observações:"));
        painelForm.add(txtObservacoes);

        add(painelForm, BorderLayout.EAST);

        // ---------- BOTÕES ----------
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAdicionar = new JButton("Adicionar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar Campos");
        btnRecarregar = new JButton("Recarregar");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnRecarregar);
        painelBotoes.add(btnLimpar);

        add(painelBotoes, BorderLayout.SOUTH);

        // ---------- EVENTOS ----------
        carregarAgendamentos();

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tabela.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(modeloTabela.getValueAt(row, 0).toString());
                    txtPacienteId.setText(modeloTabela.getValueAt(row, 1).toString());
                    txtMedicoId.setText(modeloTabela.getValueAt(row, 2).toString());
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
        btnLimpar.addActionListener(e -> limparCampos());
        btnRecarregar.addActionListener(e -> carregarAgendamentos());
    }

    // ============================================================
    // MÉTODOS AUXILIARES
    // ============================================================

    private void carregarAgendamentos() {
        modeloTabela.setRowCount(0);
        List<Agendamento> lista = dao.listar();
        for (Agendamento a : lista) {
            modeloTabela.addRow(new Object[]{
                    a.getId(),
                    a.getPaciente().getId() + " - " + a.getPaciente().getNome(),
                    a.getMedico().getId() + " - " + a.getMedico().getNome(),
                    a.getDataHora(),
                    a.getStatus(),
                    a.getSintomas(),
                    a.getDiagnostico()
            });
        }
    }

    private void adicionarAgendamento() {
        try {
            Agendamento a = new Agendamento();
            Paciente p = new Paciente();
            p.setId(Integer.parseInt(txtPacienteId.getText()));
            a.setPaciente(p);

            Medico m = new Medico();
            m.setId(Integer.parseInt(txtMedicoId.getText()));
            a.setMedico(m);

            a.setDataHora(Timestamp.valueOf(txtDataHora.getText()));
            a.setStatus(txtStatus.getText());
            a.setSintomas(txtSintomas.getText());
            a.setDiagnostico(txtDiagnostico.getText());
            a.setObservacoes(txtObservacoes.getText());

            if (dao.inserir(a)) {
                JOptionPane.showMessageDialog(this, "✅ Agendamento inserido com sucesso!");
                carregarAgendamentos();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Erro ao inserir agendamento!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Erro: " + ex.getMessage());
        }
    }

    private void atualizarAgendamento() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento para atualizar.");
            return;
        }

        try {
            Agendamento a = new Agendamento();
            a.setId(Integer.parseInt(txtId.getText()));

            Paciente p = new Paciente();
            p.setId(Integer.parseInt(txtPacienteId.getText()));
            a.setPaciente(p);

            Medico m = new Medico();
            m.setId(Integer.parseInt(txtMedicoId.getText()));
            a.setMedico(m);

            a.setDataHora(Timestamp.valueOf(txtDataHora.getText()));
            a.setStatus(txtStatus.getText());
            a.setSintomas(txtSintomas.getText());
            a.setDiagnostico(txtDiagnostico.getText());
            a.setObservacoes(txtObservacoes.getText());

            if (dao.atualizar(a)) {
                JOptionPane.showMessageDialog(this, "✅ Agendamento atualizado com sucesso!");
                carregarAgendamentos();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Erro ao atualizar agendamento!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Erro: " + ex.getMessage());
        }
    }

    private void excluirAgendamento() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento para excluir.");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o agendamento " + id + "?",
                "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deletar(id)) {
                JOptionPane.showMessageDialog(this, "✅ Agendamento excluído com sucesso!");
                carregarAgendamentos();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Erro ao excluir agendamento!");
            }
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtPacienteId.setText("");
        txtMedicoId.setText("");
        txtDataHora.setText("");
        txtStatus.setText("agendada");
        txtSintomas.setText("");
        txtDiagnostico.setText("");
        txtObservacoes.setText("");
    }

    
}
