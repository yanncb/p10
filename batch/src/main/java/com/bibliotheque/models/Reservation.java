package com.bibliotheque.models;


import java.time.LocalDate;

public class Reservation {


    private int id;

    private Utilisateur utilisateur;

    private Livre livre;

    private LocalDate dateEnvoieMail;

    public int getId() {
        return id;
    }


    public LocalDate getDateEnvoieMail() {
        return dateEnvoieMail;
    }

    public void setDateEnvoieMail(LocalDate dateEnvoieMail) {
        this.dateEnvoieMail = dateEnvoieMail;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

}

