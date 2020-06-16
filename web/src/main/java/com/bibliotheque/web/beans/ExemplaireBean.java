package com.bibliotheque.web.beans;

import java.time.LocalDate;
import java.util.Date;

public class ExemplaireBean {

    private int id;
    private boolean pret;
    private Date dateDemprunt;
    private boolean prolongerEmprunt;

    private BibliothequeBean bibliotheque;
    private UtilisateurBean utilisateur;

    private LocalDate dateRetour;


    public BibliothequeBean getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(BibliothequeBean bibliotheque) {
        this.bibliotheque = bibliotheque;
    }

    public UtilisateurBean getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurBean utilisateur) {
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPret() {
        return pret;
    }

    public void setPret(boolean pret) {
        this.pret = pret;
    }

    public Date getDateDemprunt() {
        return dateDemprunt;
    }

    public void setDateDemprunt(Date dateDemprunt) {
        this.dateDemprunt = dateDemprunt;
    }

    public boolean isProlongerEmprunt() {
        return prolongerEmprunt;
    }

    public void setProlongerEmprunt(boolean prolongerEmprunt) {
        this.prolongerEmprunt = prolongerEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    @Override
    public String toString() {
        return "ExemplaireBean{" +
                "id=" + id +
                ", pret=" + pret +
                ", dateDemprunt=" + dateDemprunt +
                ", prolongerEmprunt=" + prolongerEmprunt +
                '}';
    }
}
