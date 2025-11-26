package com.aati.scm; 

import java.sql.SQLException;

import javax.swing.SwingUtilities;

import com.aati.scm.view.TelaPrincipal;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           
            try {
                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
}