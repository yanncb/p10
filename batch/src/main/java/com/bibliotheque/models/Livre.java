package com.bibliotheque.models;

import java.util.List;

public class Livre {

    private int id;
    private String titre;
    private String auteur;

    private int positionFile;

    private List<Exemplaire> exemplaireList;

    private List<Reservation> reservationList;

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }


    public int getPositionFile() {
        return positionFile;
    }

    public void setPositionFile(int positionFile) {
        this.positionFile = positionFile;
    }

    public List<Exemplaire> getExemplaireList() {
        return exemplaireList;
    }

    public void setExemplaireList(List<Exemplaire> exemplaireList) {
        this.exemplaireList = exemplaireList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        return "LivreBean{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                '}';
    }
}
