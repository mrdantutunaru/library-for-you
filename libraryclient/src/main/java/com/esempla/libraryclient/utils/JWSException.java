package com.esempla.libraryclient.utils;

public class JWSException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;

    public JWSException(String message) {
        super(message);
        this.message = message;
    }
}
