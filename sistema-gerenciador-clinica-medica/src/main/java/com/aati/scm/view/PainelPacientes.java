package com.aati.scm.view;

import javax.swing.*;

import com.aati.scm.model.dao.PacienteDAO;
import com.aati.scm.model.entity.Paciente;

import java.awt.*;


public class PainelPacientes extends JPanel {
    private JTextField txtNome, txtCpf, txtTelefone, txtEndereco;
    private JTextArea txtHistorico;
    private JButton btnSalvar, btnLimpar;

    public PainelPacientes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Cadastro de Pacientes");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 10, 10));

        formulario.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        formulario.add(txtNome);

        formulario.add(new JLabel("CPF (11 dígitos):"));
        txtCpf = new JTextField();
        formulario.add(txtCpf);

        formulario.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        formulario.add(txtTelefone);

        formulario.add(new JLabel("Endereço:"));
        txtEndereco = new JTextField();
        formulario.add(txtEndereco);

        formulario.add(new JLabel("Histórico:"));
        txtHistorico = new JTextArea(4, 20);
        formulario.add(new JScrollPane(txtHistorico));

        JPanel botoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        botoes.add(btnSalvar);
        botoes.add(btnLimpar);

        add(formulario, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvarPaciente());
        btnLimpar.addActionListener(e -> limparCampos());
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

        PacienteDAO dao = new PacienteDAO();
        dao.inserir(p);
        JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!");
        limparCampos();
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtHistorico.setText("");
    }
}