package com.aati.scm.model.enums;

public enum PapelUsario {

    ADMIN("admin"),
    MEDICO("medico"),
    SECRETARIA("secretaria");

    private final String value;

    public String getValue() {
        return this.value;

    }

    PapelUsario(String value) {
        this.value = value;
    }

}
