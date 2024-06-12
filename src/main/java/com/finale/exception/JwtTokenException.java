package com.finale.exception;

import io.jsonwebtoken.JwtException;

public class JwtTokenException extends JwtException {

    public JwtTokenException(String message) {
        super(message);
    }
}
