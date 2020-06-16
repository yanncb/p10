package com.bibliotheque.web.beans;

import org.springframework.security.core.GrantedAuthority;

public class RoleBean implements GrantedAuthority {

    private int id;
    private String rolNom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolNom() {
        return rolNom;
    }

    public void setRolNom(String rolNom) {
        this.rolNom = rolNom;
    }

    @Override
    public String toString() {
        return "RoleBean{" +
                "id=" + id +
                ", rolNom='" + rolNom + '\'' +
                '}';
    }

    @Override
    public String getAuthority() {
        return rolNom;
    }
}
