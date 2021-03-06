package com.bibliotheque.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_reservation")
public class Reservation {

    @Id
    @SequenceGenerator(name = "RESERVATION_SEQ_GENERATOR", sequenceName = "RESERVATION_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVATION_SEQ_GENERATOR")
    @Column(name = "reservation_id")
    private int id;

    @Column(name = "reservation_date_envoie_mail")
    private LocalDate dateEnvoieMail;

    @ManyToOne
    @JoinColumn(name = "util_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

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

