package com.aati.scm.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.*;

import com.aati.scm.config.ConnectionFactory;
import com.aati.scm.model.dao.LoginDAO;
import com.aati.scm.model.entity.Login;

public class PainelLogin extends JPanel {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin, botaoSair;

    private LoginDAO loginDAO ;

    public PainelLogin() throws SQLException {
        this.loginDAO = new LoginDAO(ConnectionFactory.getConnection());

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 248, 255)); // fundo azul claro

        // TÍTULO
        JLabel titulo = new JLabel("Acesso ao Sistema", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(40, 80, 180));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // PAINEL CAMPOS
        JPanel painelCampos = new JPanel(new GridLayout(2, 2, 15, 15));
        painelCampos.setBackground(Color.WHITE);
        painelCampos.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 220, 240), 1, true),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        JLabel labelUsuario = new JLabel("Usuário:");
        JLabel labelSenha = new JLabel("Senha:");

        labelUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelSenha.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelUsuario.setForeground(new Color(50, 60, 90));
        labelSenha.setForeground(new Color(50, 60, 90));

        campoUsuario = criarCampoTexto();
        campoSenha = new JPasswordField();
        campoSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoSenha.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230), 1, true));
        campoSenha.setBackground(new Color(250, 250, 255));

        painelCampos.add(labelUsuario);
        painelCampos.add(campoUsuario);
        painelCampos.add(labelSenha);
        painelCampos.add(campoSenha);

        add(painelCampos, BorderLayout.CENTER);

        // PAINEL BOTÕES
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(245, 248, 255));

        botaoLogin = criarBotao("💻 Logar", new Color(52, 120, 235));
        botaoSair  = criarBotao("❌ Sair", new Color(180, 60, 60));

        painelBotoes.add(botaoLogin);
        painelBotoes.add(botaoSair);

        add(painelBotoes, BorderLayout.SOUTH);

        // AÇÕES
        botaoLogin.addActionListener(e -> logar());
        botaoSair.addActionListener(e -> System.exit(0));
    }

    // CAMPO ESTILIZADO
    private JTextField criarCampoTexto() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBackground(new Color(250, 250, 255));
        txt.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230), 1, true));
        return txt;
    }

    // BOTÃO ESTILIZADO
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(cor.darker()); }
            public void mouseExited(MouseEvent evt)  { btn.setBackground(cor); }
        });

        return btn;
    }

    // ----------------------------------------------------
    // 🔐 LÓGICA COMPLETA DE LOGIN COM LoginDAO
    // ----------------------------------------------------
    private void logar() {
        String username = campoUsuario.getText();
        String senhaDigitada = new String(campoSenha.getPassword());

        try {
            Login usuario = loginDAO.buscarPorUsername(username);

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado!");
                return;
            }

            if (!usuario.isAtivo()) {
                JOptionPane.showMessageDialog(this, "Usuário está desativado!");
                return;
            }

            // --------- Comparação de senha ----------
            // Caso esteja usando hash, coloque aqui a verificação com BCrypt
            //
            // if (!BCrypt.checkpw(senhaDigitada, usuario.getSenha())) ...
            //-----------------------------------------

            if (!senhaDigitada.equals(usuario.getSenha())) {
                JOptionPane.showMessageDialog(this, "Senha incorreta!");
                return;
            }

            // LOGIN OK
            JOptionPane.showMessageDialog(this,
                "Bem-vindo(a), " + usuario.getNomeCompleto() + "!");

            // FECHA A JANELA ATUAL (onde o painel está)
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();

            // ABRE A TELA PRINCIPAL OU RELATÓRIOS
            new PainelRelatorio();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao realizar login: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
