package com.bibliotheque.web.beans;

public class Recherche {

    private String titre;
    private String auteur;

    public Recherche(){}

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    @Override
    public String toString() {
        return "Recherche{" +
                "titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                '}';
    }
}
