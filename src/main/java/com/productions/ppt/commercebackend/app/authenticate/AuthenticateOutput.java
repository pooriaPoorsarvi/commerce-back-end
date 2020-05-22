package com.productions.ppt.commercebackend.app.authenticate;

import javax.validation.constraints.NotNull;

class AuthenticateOutput {

    @NotNull
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
