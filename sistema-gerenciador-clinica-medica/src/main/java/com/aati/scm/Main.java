package com.aati.scm;

import javax.swing.SwingUtilities;

import com.aati.scm.model.entity.Paciente;
import com.aati.scm.repository.IPacienteRepository;
import com.aati.scm.view.TelaPrincipal;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    
    }
}