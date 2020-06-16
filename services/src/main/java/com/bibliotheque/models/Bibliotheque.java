package com.bibliotheque.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_bibliotheque")
public class Bibliotheque {

    @Id
    @SequenceGenerator(name = "BIBLIOTHEQUE_SEQ_GENERATOR", sequenceName = "BIBLIOTHEQUE_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BIBLIOTHEQUE_SEQ_GENERATOR")
    @Column(name = "bibli_id")
    private int id;

    @Column(name = "bibli_nom")
    private String nom;

    @OneToMany(mappedBy = "bibliotheque", cascade = CascadeType.ALL)
    private List<Exemplaire> exemplaireList;

    public Bibliotheque() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Exemplaire> getExemplaireList() {
        return exemplaireList;
    }

    public void setExemplaireList(List<Exemplaire> exemplaireList) {
        this.exemplaireList = exemplaireList;
    }

    @Override
    public String toString() {
        return "Bibliotheque{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", exemplaireList=" + exemplaireList +
                '}';
    }
}
