package com.example.ChatApp.response;

public class AuthResponse {
    private String jwt;
    private boolean isAuth;

    public AuthResponse( String jwt,boolean isAuth) {
        this.isAuth = isAuth;
        this.jwt = jwt;
    }
}
