package com.bibliotheque.web.beans;

import java.util.List;

public class BibliothequeBean {

    private int id;
    private String nom;

    private List<ExemplaireBean> exemplaireList;

    public List<ExemplaireBean> getExemplaireList() {
        return exemplaireList;
    }

    public void setExemplaireList(List<ExemplaireBean> exemplaireList) {
        this.exemplaireList = exemplaireList;
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

    @Override
    public String toString() {
        return "BibliothequeBean{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
