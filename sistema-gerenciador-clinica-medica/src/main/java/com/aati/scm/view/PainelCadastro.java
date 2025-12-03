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
        setBorder(new EmptyBorder(20, 30, 20, 30));
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
        // PAINEL DO FORMULÁRIO (GridBagLayout)
        // ---------------------------------------------------------
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(Color.WHITE);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 230), 1, true),
                new EmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonte = new Font("Segoe UI", Font.PLAIN, 15);

        campoNome = criarCampoTexto(fonte);
        campoUsername = criarCampoTexto(fonte);
        campoSenha = new JPasswordField();
        campoSenha.setFont(fonte);
        campoSenha.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230), 1, true));

        comboPapel = new JComboBox<>(PapelUsario.values());
        comboPapel.setFont(fonte);

        checkAtivo = new JCheckBox("Ativo");
        checkAtivo.setFont(fonte);
        checkAtivo.setSelected(true);
        checkAtivo.setBackground(Color.WHITE);

        // Adicionar linhas
        adicionarLinha(painelForm, gbc, 0, "Nome Completo:", campoNome);
        adicionarLinha(painelForm, gbc, 1, "Username:", campoUsername);
        adicionarLinha(painelForm, gbc, 2, "Senha:", campoSenha);
        adicionarLinha(painelForm, gbc, 3, "Papel:", comboPapel);
        adicionarLinha(painelForm, gbc, 4, "Ativo:", checkAtivo);

        add(painelForm, BorderLayout.CENTER);

        // ---------------------------------------------------------
        // BOTÕES
        // ---------------------------------------------------------
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setBackground(new Color(245, 248, 255));

        botaoSalvar = criarBotao("Salvar", new Color(46, 139, 87));
        botaoLimpar = criarBotao("Limpar", new Color(128, 128, 128));

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoLimpar);

        add(painelBotoes, BorderLayout.SOUTH);

        // ---------------------------------------------------------
        // AÇÕES
        // ---------------------------------------------------------
        botaoSalvar.addActionListener(e -> salvarUsuario());
        botaoLimpar.addActionListener(e -> limparCampos());
    }

    // ---------------------------------------------------------
    // MÉTODOS DE FORMATAÇÃO
    // ---------------------------------------------------------

    private JTextField criarCampoTexto(Font fonte) {
        JTextField txt = new JTextField();
        txt.setFont(fonte);
        txt.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230), 1, true));
        txt.setPreferredSize(new Dimension(350, 35)); // <-- CAMPO GRANDE
        return txt;
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(50, 60, 90));
        return label;
    }

    private void adicionarLinha(JPanel painel, GridBagConstraints gbc, int linha, String texto, JComponent campo) {

        gbc.gridy = linha;

        // Label
        gbc.gridx = 0;
        gbc.weightx = 0.2;
        painel.add(criarLabel(texto), gbc);

        // Campo
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        painel.add(campo, gbc);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 25, 10, 25));

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
            login.setSenha(new String(campoSenha.getPassword()));
            login.setPapel((PapelUsario) comboPapel.getSelectedItem());
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
