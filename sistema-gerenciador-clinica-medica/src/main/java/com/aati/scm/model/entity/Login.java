package com.aati.scm.model.entity;

import com.aati.scm.model.enums.PapelUsario;

public class Login {

    private int id;
    private PapelUsario papel;
    private String senha;
    private String username;
    private String nomeCompleto;
    private boolean ativo;


    public Login() {
    }

    public Login(PapelUsario papel, String senha, String username) {
        this.papel = papel;
        this.senha = senha;
        this.username = username;
    }
    


    
    

    public PapelUsario getPapel() {
        return papel;
    }
    public void setPapel(PapelUsario papel) {
        this.papel = papel;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
