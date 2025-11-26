package com.aati.scm.view;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.aati.scm.model.entity.Login;
import com.aati.scm.model.enums.PapelUsario;
import com.aati.scm.config.ConnectionFactory;
import com.aati.scm.model.dao.LoginDAO;

public class PainelCadastro extends JPanel {

    // Campos
    private JTextField campoNome;
    private JTextField campoUsername;
    private JPasswordField campoSenha;
    private JComboBox<PapelUsario> comboPapel;
    private JCheckBox checkAtivo;

    private JButton botaoSalvar;
    private JButton botaoLimpar;

    private LoginDAO loginDAO;

    public PainelCadastro() throws SQLException {
        this.loginDAO = new LoginDAO(ConnectionFactory.getConnection());

        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 248, 255));

        // ---------------------------------------------------------
        // TÍTULO
        // ---------------------------------------------------------
        JLabel titulo = new JLabel("Cadastro de Usuários", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(40, 80, 180));
        titulo.setBorder(new EmptyBorder(10, 0, 20, 0));

        add(titulo, BorderLayout.NORTH);

        // ---------------------------------------------------------
        // FORMULÁRIO
        // ---------------------------------------------------------
        JPanel painelForm = new JPanel(new GridLayout(5, 2, 15, 15));
        painelForm.setBackground(Color.WHITE);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 220, 240), 1, true),
            new EmptyBorder(20, 30, 20, 30)
        ));

        // LABELSF
        JLabel labelNome = criarLabel("Nome Completo:");
        JLabel labelUsername = criarLabel("Username:");
        JLabel labelSenha = criarLabel("Senha:");
        JLabel labelPapel = criarLabel("Papel:");
        JLabel labelAtivo = criarLabel("Ativo:");

        // CAMPOS
        campoNome = criarCampoTexto();
        campoUsername = criarCampoTexto();
        campoSenha = new JPasswordField();
        campoSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoSenha.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230), 1, true));

        comboPapel = new JComboBox<>(PapelUsario.values());
        comboPapel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        checkAtivo = new JCheckBox("Sim");
        checkAtivo.setSelected(true);
        checkAtivo.setBackground(Color.WHITE);
        checkAtivo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // ADICIONA CAMPOS NO FORMULÁRIO
        painelForm.add(labelNome);
        painelForm.add(campoNome);

        painelForm.add(labelUsername);
        painelForm.add(campoUsername);

        painelForm.add(labelSenha);
        painelForm.add(campoSenha);

        painelForm.add(labelPapel);
        painelForm.add(comboPapel);

        painelForm.add(labelAtivo);
        painelForm.add(checkAtivo);

        add(painelForm, BorderLayout.CENTER);

        // ---------------------------------------------------------
        // BOTÕES
        // ---------------------------------------------------------
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(245, 248, 255));

        botaoSalvar = criarBotao("Salvar", new Color(46, 139, 87));
        botaoLimpar = criarBotao("Limpar", new Color(128, 128, 128));

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoLimpar);

        add(painelBotoes, BorderLayout.SOUTH);

        // AÇÕES
        botaoSalvar.addActionListener(e -> salvarUsuario());
        botaoLimpar.addActionListener(e -> limparCampos());
    }

    // ---------------------------------------------------------
    // MÉTODOS DE UTILIDADE
    // ---------------------------------------------------------

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(50, 60, 90));
        return label;
    }

    private JTextField criarCampoTexto() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230), 1, true));
        return txt;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));

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

    // ---------------------------------------------------------
    // AÇÕES
    // ---------------------------------------------------------

    private void salvarUsuario() {
        try {
            Login login = new Login();

            login.setNomeCompleto(campoNome.getText());
            login.setUsername(campoUsername.getText());
            login.setSenha(new String(campoSenha.getPassword())); // hash depois
            login.setPapel(PapelUsario.valueOf(comboPapel.getSelectedItem().toString()));
            login.setAtivo(checkAtivo.isSelected());

            loginDAO.inserir(login);

            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            limparCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar usuário: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoUsername.setText("");
        campoSenha.setText("");
        comboPapel.setSelectedIndex(0);
        checkAtivo.setSelected(true);
    }
}
