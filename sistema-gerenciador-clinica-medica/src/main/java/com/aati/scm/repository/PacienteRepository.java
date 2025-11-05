package com.aati.scm.repository;

import java.util.List;

import com.aati.scm.model.entity.Paciente;

public interface PacienteRepository {
    


    public void salvarPaciente(Paciente paciente);
    public List<Paciente> pegarTodosPacientes();
    public void deletarPaciente(Long id);

}
