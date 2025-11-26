package com.aati.scm.model.entity;

import java.sql.Timestamp;

public class Agendamento {

    private Integer id;
    private Paciente paciente;
    private Medico medico;
    private Timestamp dataHora;
    private String status; // agendada, realizada, cancelada
    private String sintomas;
    private String diagnostico;
    private String observacoes;
    private Timestamp createdAt;

    public Agendamento() {
    }

    public Agendamento(Integer id, Paciente paciente, Medico medico, Timestamp dataHora,
                       String status, String sintomas, String diagnostico,
                       String observacoes, Timestamp createdAt) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.status = status;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.observacoes = observacoes;
        this.createdAt = createdAt;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                "id=" + id +
                ", paciente=" + (paciente != null ? paciente.getId() : null) +
                ", medico=" + (medico != null ? medico.getId() : null) +
                ", dataHora=" + dataHora +
                ", status='" + status + '\'' +
                ", sintomas='" + sintomas + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
