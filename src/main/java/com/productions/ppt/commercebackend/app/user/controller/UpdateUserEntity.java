package com.productions.ppt.commercebackend.app.user.controller;

import com.productions.ppt.commercebackend.app.user.models.UserEntity;

import javax.validation.constraints.NotNull;

public class UpdateUserEntity extends UserEntity {
    @NotNull
    String oldPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
