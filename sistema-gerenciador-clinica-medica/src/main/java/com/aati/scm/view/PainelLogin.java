package com.aati.scm.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.aati.scm.config.ConnectionFactory;
import com.aati.scm.model.dao.LoginDAO;
import com.aati.scm.model.entity.Login;

public class PainelLogin extends JPanel {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin, botaoSair;
    private LoginDAO loginDAO;

    public PainelLogin() throws SQLException {

        this.loginDAO = new LoginDAO(ConnectionFactory.getConnection());

        setLayout(new GridBagLayout()); 
        setBackground(new Color(235, 239, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // -------------------------------
        // CAIXA BRANCA CENTRAL
        // -------------------------------
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(350, 350));
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 230), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        // -------------------------------
        // TÍTULO
        // -------------------------------
        JLabel titulo = new JLabel("Login do Sistema", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 160));

        g.gridx = 0;  
        g.gridy = 0;
        g.gridwidth = 2;
        card.add(titulo, g);

        // -------------------------------
        // CAMPO LOGIN
        // -------------------------------
        JLabel labelUsuario = new JLabel("Usuário:");
        labelUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelUsuario.setForeground(new Color(50, 60, 90));

        g.gridy++; 
        g.gridwidth = 2;
        card.add(labelUsuario, g);

        campoUsuario = new JTextField();
        estilizarCampo(campoUsuario);

        g.gridy++;
        card.add(campoUsuario, g);

        // -------------------------------
        // CAMPO SENHA
        // -------------------------------
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelSenha.setForeground(new Color(50, 60, 90));

        g.gridy++;
        card.add(labelSenha, g);

        campoSenha = new JPasswordField();
        estilizarCampo(campoSenha);

        g.gridy++;
        card.add(campoSenha, g);

        // -------------------------------
        // BOTÕES
        // -------------------------------
        botaoLogin = criarBotao("Entrar", new Color(52, 120, 235));
        botaoSair  = criarBotao("Sair", new Color(180, 60, 60));

        g.gridy++;
        g.gridwidth = 1;
        g.gridx = 0;
        card.add(botaoLogin, g);

        g.gridx = 1;
        card.add(botaoSair, g);

        // Adiciona o card ao centro
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);

        // Ações
        botaoLogin.addActionListener(e -> logar());
        botaoSair.addActionListener(e -> System.exit(0));
    }

    // -------------------------------
    // ESTILO DOS CAMPOS
    // -------------------------------
    private void estilizarCampo(JTextField campo) {
        campo.setPreferredSize(new Dimension(200, 35));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBackground(new Color(248, 248, 255));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 230), 1, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    // -------------------------------
    // ESTILO DOS BOTÕES
    // -------------------------------
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(cor.darker()); }
            public void mouseExited(MouseEvent evt)  { btn.setBackground(cor); }
        });

        return btn;
    }

    // -------------------------------
    // LÓGICA DO LOGIN
    // -------------------------------
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
    
            // Comparação de senha (mude para hash se quiser)
            if (!senhaDigitada.equals(usuario.getSenha())) {
                JOptionPane.showMessageDialog(this, "Senha incorreta!");
                return;
            }
    
            // LOGIN OK
            JOptionPane.showMessageDialog(this,
                "Bem-vindo(a), " + usuario.getNomeCompleto() + "!");
    
            // FECHAR JANELA DE LOGIN
            JFrame janela = (JFrame) SwingUtilities.getWindowAncestor(this);
            janela.dispose();
    
            // ABRIR JANELA DE RELATÓRIO (QUE É UM JFRAME)
            TelaPrincipal telaPrincipal = new TelaPrincipal( );
            telaPrincipal.setLocationRelativeTo(null);
            telaPrincipal.setVisible(true);
    
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao realizar login: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    }
    