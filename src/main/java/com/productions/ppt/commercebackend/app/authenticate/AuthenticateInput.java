package com.productions.ppt.commercebackend.app.authenticate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AuthenticateInput {

    @NotNull
    String email;
    @NotNull
    String password;

    public AuthenticateInput() {
    }

    public AuthenticateInput(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
