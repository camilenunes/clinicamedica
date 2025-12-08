package com.aati.scm; 

import java.sql.SQLException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.aati.scm.view.TelaLogin;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {

        UIManager.setLookAndFeel(new FlatMacLightLaf());

        SwingUtilities.invokeLater(() -> {

           
            try {
                TelaLogin tela = new TelaLogin();
                tela.setVisible(true);

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
}