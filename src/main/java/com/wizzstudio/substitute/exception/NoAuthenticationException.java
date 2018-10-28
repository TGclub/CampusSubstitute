package com.wizzstudio.substitute.exception;

public class NoAuthenticationException extends RuntimeException {
    public NoAuthenticationException() {
        super("");
    }

    public NoAuthenticationException(String message) {
        super(message);
    }
}
