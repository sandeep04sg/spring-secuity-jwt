package com.spring.security.jwt.model;

public class AuthenticationResponse {

    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
    public String getJwt() {
        return jwt;
    }
}
