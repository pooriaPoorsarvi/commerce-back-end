package com.productions.ppt.commercebackend.app.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {
    @Id
    String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
