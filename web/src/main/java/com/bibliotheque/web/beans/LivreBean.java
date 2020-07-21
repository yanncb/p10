package com.bibliotheque.web.beans;

import java.util.List;

public class LivreBean {

    private int id;

    private String titre;

    private String auteur;

    private List<ExemplaireBean> exemplaireList;

    private List<ReservationBean> reservationList;

    public List<ReservationBean> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<ReservationBean> reservationList) {
        this.reservationList = reservationList;
    }

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
