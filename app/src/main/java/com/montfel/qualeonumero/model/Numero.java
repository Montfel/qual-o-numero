package com.montfel.qualeonumero.model;

public class Numero {
    private String value;
    private String StatusCode;
    private String Error;

    public String getValue() {
        return value;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public String getError() {
        return Error;
    }

    public String toString() {
        return getValue() + " " + getError() + " " + getStatusCode() ;
    }
}
