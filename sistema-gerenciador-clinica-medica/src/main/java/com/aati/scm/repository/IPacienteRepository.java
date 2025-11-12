package com.aati.scm.repository;

import java.util.List;

import com.aati.scm.model.dao.PacienteDAO;
import com.aati.scm.model.entity.Paciente;

public class IPacienteRepository implements PacienteRepository {

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    @Override
    public void salvarPaciente(Paciente paciente) {
        pacienteDAO.inserir(paciente);
    }

    @Override
    public List<Paciente> pegarTodosPacientes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pegarTodosPacientes'");
    }

    @Override
    public void deletarPaciente(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletarPaciente'");
    }

}
