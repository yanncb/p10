package com.bibliotheque.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_roles")
public class Role {

    @Id
    @SequenceGenerator(name = "ROLE_SEQ_GENERATOR", sequenceName = "ROLE_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ_GENERATOR")
    @Column(name = "role_id")
    private int id;

    @Column(name = "role_nom")
    private String rolNom;


    public Role() {
    }

    public Role(String rolNom, Set<Utilisateur> utilisateurs) {
        this.rolNom = rolNom;
    }

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
        return "Role{" +
                "id=" + id +
                ", rol_longueur='" + rolNom + '\'' +
                '}';
    }


}
