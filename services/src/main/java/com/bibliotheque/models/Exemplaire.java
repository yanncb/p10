package com.bibliotheque.models;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "t_exemplaire")
public class Exemplaire {


    @Id
    @SequenceGenerator(name = "EXEMPLAIRE_SEQ_GENERATOR", sequenceName = "EXEMPLAIRE_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXEMPLAIRE_SEQ_GENERATOR")
    @Column(name = "exemplaire_id")
    private int id;

    @Column(name = "exemplaire_pret")
    private boolean pret;

    @Column(name = "exemplaire_date_emprunt")
    private LocalDate dateDemprunt;

    @Column(name = "exemplaire_prolonger")
    private boolean prolongerEmprunt;

    @ManyToOne
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    @ManyToOne
    @JoinColumn(name = "bibli_id", nullable = false)
    private Bibliotheque bibliotheque;

    @ManyToOne
    @JoinColumn(name = "util_id")
    private Utilisateur utilisateur;

    @Transient
    private LocalDate dateRetour;

    public Exemplaire() {
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
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

    public LocalDate getDateDemprunt() {
        return dateDemprunt;
    }

    public void setDateDemprunt(LocalDate dateDemprunt) {
        this.dateDemprunt = dateDemprunt;
    }

    public boolean isProlongerEmprunt() {
        return prolongerEmprunt;
    }

    public void setProlongerEmprunt(boolean prolongerEmprunt) {
        this.prolongerEmprunt = prolongerEmprunt;
    }


    @Override
    public String toString() {
        return "Exemplaire{" +
                "id=" + id +
                ", pret=" + pret +
                ", dateDemprunt=" + dateDemprunt +
                ", prolongerEmprunt=" + prolongerEmprunt +
                '}';
    }
}
