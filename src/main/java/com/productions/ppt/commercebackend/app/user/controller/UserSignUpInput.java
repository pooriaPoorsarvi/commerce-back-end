package com.productions.ppt.commercebackend.app.user.controller;

import javax.validation.constraints.NotNull;

class UserSignUpInput {
    @NotNull
    String email;
    @NotNull
    String password;

    public UserSignUpInput() {
    }

    public UserSignUpInput(@NotNull String email, @NotNull String password) {
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
