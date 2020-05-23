package com.productions.ppt.commercebackend.app.user.controller;

import javax.validation.constraints.NotNull;

public class SingUpResult {

    @NotNull
    private String jwt;

    @NotNull
    private String email;

    public SingUpResult() {
    }

    public SingUpResult(String jwt, String email) {
        this.jwt = jwt;
        this.email = email;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
