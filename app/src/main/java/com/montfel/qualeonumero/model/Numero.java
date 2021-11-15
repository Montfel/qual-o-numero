package com.montfel.qualeonumero.model;

import androidx.annotation.NonNull;

public class Numero {
    private String value;

    public String getValue() {
        return value;
    }

    @NonNull
    public String toString() {
        return getValue();
    }
}
