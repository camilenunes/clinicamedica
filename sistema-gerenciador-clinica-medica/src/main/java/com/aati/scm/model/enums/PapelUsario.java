package com.aati.scm.model.enums;

public enum PapelUsario {

    ADMIN("ADMIN"),
    MEDICO("MEDICO"),
    SECRETARIA("SECRETARIA");

    private final String value;

    public String getValue() {
        return this.value;

    }

    PapelUsario(String value) {
        this.value = value;
    }

}
