package com.aati.scm.model.entity;

public class Paciente {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String historico;

    // Construtor vazio
    public Paciente() {}

    // Construtor completo
    public Paciente(String nome, String cpf, String telefone, String endereco, String historico) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.historico = historico;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getHistorico() { return historico; }
    public void setHistorico(String historico) { this.historico = historico; }

    @Override
    public String toString() {
        return nome + " (" + cpf + ")";
    }
}