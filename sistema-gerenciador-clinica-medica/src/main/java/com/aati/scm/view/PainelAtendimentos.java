package com.aati.scm.view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.aati.scm.model.dao.AtendimentoDAO;
import com.aati.scm.model.dao.MedicoDAO;
import com.aati.scm.model.dao.PacienteDAO;
import com.aati.scm.model.entity.Atendimento;
import com.aati.scm.model.entity.Medico;
import com.aati.scm.model.entity.Paciente;

import java.awt.*;
import java.util.List;

public class PainelAtendimentos extends JPanel {

    private JComboBox<Paciente> cbPaciente;
    private JComboBox<Medico> cbMedico;

    private JTextArea txtDiagnostico, txtSintomas, txtObservacoes;

    private JButton btnSalvar, btnAtualizar, btnExcluir, btnLimpar;

    private JTable tabela;
    private DefaultTableModel modelo;

    public PainelAtendimentos() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Cadastro de Atendimentos");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));

        // Paciente
        form.add(new JLabel("Paciente:"));
        cbPaciente = new JComboBox<>();
        form.add(cbPaciente);

        // Médico
        form.add(new JLabel("Médico:"));
        cbMedico = new JComboBox<>();
        form.add(cbMedico);

        // Diagnóstico
        form.add(new JLabel("Diagnóstico:"));
        txtDiagnostico = new JTextArea(3, 20);
        form.add(new JScrollPane(txtDiagnostico));

        // Sintomas
        form.add(new JLabel("Sintomas:"));
        txtSintomas = new JTextArea(3, 20);
        form.add(new JScrollPane(txtSintomas));

        // Observações
        form.add(new JLabel("Observações:"));
        txtObservacoes = new JTextArea(3, 20);
        form.add(new JScrollPane(txtObservacoes));

        add(form, BorderLayout.CENTER);

        // ---------------- BOTOES ----------------
        JPanel botoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");

        botoes.add(btnSalvar);
        botoes.add(btnAtualizar);
        botoes.add(btnExcluir);
        botoes.add(btnLimpar);

        add(botoes, BorderLayout.SOUTH);

        // ---------------- TABELA ----------------
        modelo = new DefaultTableModel(
                new Object[]{"Paciente", "Médico", "Diagnóstico", "Sintomas", "Observações"}, 0
        );

        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(tabela), BorderLayout.EAST);

        // Eventos
        btnSalvar.addActionListener(e -> salvar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparCampos());
        tabela.getSelectionModel().addListSelectionListener(e -> preencherCamposTabela());

        carregarCombos();
        carregarTabela();
    }

    private void carregarCombos() {
        MedicoDAO mdao = new MedicoDAO();
        PacienteDAO pdao = new PacienteDAO();

        cbMedico.removeAllItems();
        cbPaciente.removeAllItems();

        for (Medico m : mdao.listar()) cbMedico.addItem(m);
        for (Paciente p : pdao.listar()) cbPaciente.addItem(p);
    }

    private void carregarTabela() {
        modelo.setRowCount(0);
        AtendimentoDAO dao = new AtendimentoDAO();

        List<Atendimento> lista = dao.listar();
        for (Atendimento a : lista) {
            modelo.addRow(new Object[]{
                    a.getPaciente().getNome(),
                    a.getMedico().getNome(),
                    a.getDiagnostico(),
                    a.getSintomas(),
                    a.getObservacoes()
            });
        }
    }

    private void salvar() {
        Atendimento a = new Atendimento();
        a.setDiagnostico(txtDiagnostico.getText());
        a.setSintomas(txtSintomas.getText());
        a.setObservacoes(txtObservacoes.getText());
        a.setPaciente((Paciente) cbPaciente.getSelectedItem());
        a.setMedico((Medico) cbMedico.getSelectedItem());

        if (a.getPaciente() == null || a.getMedico() == null) {
            JOptionPane.showMessageDialog(this, "Selecione paciente e médico!");
            return;
        }

        new AtendimentoDAO().inserir(a);
        JOptionPane.showMessageDialog(this, "Atendimento cadastrado!");
        carregarTabela();
        limparCampos();
    }

    private void atualizar() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um atendimento para atualizar.");
            return;
        }

        // Aqui você pode criar um método atualizar no DAO caso deseje
        JOptionPane.showMessageDialog(this, "Função atualizar ainda não implementada.");
    }

    private void excluir() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um atendimento para excluir.");
            return;
        }

        // Aqui você pode criar um método excluir no DAO caso deseje
        JOptionPane.showMessageDialog(this, "Função excluir ainda não implementada.");
    }

    private void preencherCamposTabela() {
        int row = tabela.getSelectedRow();
        if (row == -1) return;

        txtDiagnostico.setText((String) modelo.getValueAt(row, 2));
        txtSintomas.setText((String) modelo.getValueAt(row, 3));

        // Você pode adicionar mais campos se a tabela tiver mais dados
    }

    private void limparCampos() {
        txtDiagnostico.setText("");
        txtSintomas.setText("");
        txtObservacoes.setText("");
        cbMedico.setSelectedIndex(0);
        cbPaciente.setSelectedIndex(0);
    }
}
