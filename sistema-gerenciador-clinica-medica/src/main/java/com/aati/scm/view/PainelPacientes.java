package com.aati.scm.view;

import javax.swing.*;
import java.awt.*;
import com.aati.scm.model.dao.PacienteDAO;
import com.aati.scm.model.entity.Paciente;

public class PainelPacientes extends JPanel {
    private JTextField txtNome, txtCpf, txtTelefone, txtEndereco;
    private JTextArea txtHistorico;
    private JButton btnSalvar, btnLimpar;

    public PainelPacientes() {
        // 🎨 Layout principal e estilo geral
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 248, 255)); // fundo suave
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 🩵 Título estilizado
        JLabel titulo = new JLabel("Cadastro de Pacientes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(40, 80, 180));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        // 📋 Painel de formulário central
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 220, 240), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 🧾 Campos do formulário
        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCpf = new JLabel("CPF (11 dígitos):");
        JLabel lblTelefone = new JLabel("Telefone:");
        JLabel lblEndereco = new JLabel("Endereço:");
        JLabel lblHistorico = new JLabel("Histórico:");

        txtNome = criarCampoTexto();
        txtCpf = criarCampoTexto();
        txtTelefone = criarCampoTexto();
        txtEndereco = criarCampoTexto();
        txtHistorico = new JTextArea(4, 20);
        txtHistorico.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtHistorico.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230), 1, true));
        txtHistorico.setWrapStyleWord(true);
        txtHistorico.setLineWrap(true);

        JScrollPane scrollHistorico = new JScrollPane(txtHistorico);
        scrollHistorico.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230)));

        int y = 0;
        adicionarCampo(formulario, lblNome, txtNome, gbc, y++);
        adicionarCampo(formulario, lblCpf, txtCpf, gbc, y++);
        adicionarCampo(formulario, lblTelefone, txtTelefone, gbc, y++);
        adicionarCampo(formulario, lblEndereco, txtEndereco, gbc, y++);
        adicionarCampo(formulario, lblHistorico, scrollHistorico, gbc, y++);

        add(formulario, BorderLayout.CENTER);

        // 🔘 Botões na parte inferior
        JPanel botoes = new JPanel();
        botoes.setBackground(new Color(245, 248, 255));
        btnSalvar = criarBotao("💾 Salvar", new Color(52, 120, 235));
        btnLimpar = criarBotao("🧹 Limpar", new Color(180, 60, 60));

        botoes.add(btnSalvar);
        botoes.add(btnLimpar);
        add(botoes, BorderLayout.SOUTH);

        // ⚙️ Ações dos botões
        btnSalvar.addActionListener(e -> salvarPaciente());
        btnLimpar.addActionListener(e -> limparCampos());
    }

    // 🔹 Cria um campo de texto com estilo padrão
    private JTextField criarCampoTexto() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230), 1, true));
        txt.setBackground(new Color(250, 250, 255));
        return txt;
    }

    // 🔹 Adiciona campo com label e componente
    private void adicionarCampo(JPanel panel, JLabel label, JComponent campo, GridBagConstraints gbc, int y) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(50, 60, 90));

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(campo, gbc);
    }

    // 🔹 Cria botão estilizado
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // efeito hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor);
            }
        });

        return btn;
    }

    // 💾 Salvar paciente no banco
    private void salvarPaciente() {
        Paciente p = new Paciente(
            txtNome.getText(),
            txtCpf.getText(),
            txtTelefone.getText(),
            txtEndereco.getText(),
            txtHistorico.getText()
        );

        if (p.getNome().isEmpty() || p.getCpf().length() != 11) {
            JOptionPane.showMessageDialog(this, "⚠️ Preencha o nome e CPF (11 dígitos).");
            return;
        }

        PacienteDAO dao = new PacienteDAO();
        dao.inserir(p);
        JOptionPane.showMessageDialog(this, "✅ Paciente cadastrado com sucesso!");
        limparCampos();
    }

    // 🧹 Limpar campos
    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtHistorico.setText("");
    }
}
