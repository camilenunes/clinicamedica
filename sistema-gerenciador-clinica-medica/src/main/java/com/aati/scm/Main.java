package com.aati.scm;

import com.aati.scm.model.entity.Paciente;
import com.aati.scm.repository.IPacienteRepository;

public class Main {
    public static void main(String[] args) {
        IPacienteRepository pacienteRepository = new IPacienteRepository();


        Paciente paciente = new Paciente("William Colasso", "13132033944", "47992064694", "asdad", "asad");
        

        pacienteRepository.salvarPaciente(paciente);
    
    }
}