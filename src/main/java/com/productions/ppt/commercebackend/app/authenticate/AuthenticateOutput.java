package com.productions.ppt.commercebackend.app.authenticate;

public class AuthenticateOutput {

    String jwt;

    public AuthenticateOutput(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
