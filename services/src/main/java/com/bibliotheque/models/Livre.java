package com.bibliotheque.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_livre")
public class Livre {

    @Id
    @SequenceGenerator(name = "LIVRE_SEQ_GENERATOR", sequenceName = "LIVRE_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIVRE_SEQ_GENERATOR")
    @Column(name = "livre_id")
    private int id;

    @Column(name = "livre_titre")
    private String titre;

    @Column(name = "livre_auteur")
    private String auteur;

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL)
    private List<Exemplaire> exemplaireList;

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL)
    private List<Reservation> reservationList;

    @Transient
    private int positionFile;


    public Livre() {
    }

    public Livre(String titre, String auteur, List<Exemplaire> exemplaireList) {
        this.titre = titre;
        this.auteur = auteur;
        this.exemplaireList = exemplaireList;
    }

    public int getPositionFile() {
        return positionFile;
    }

    public void setPositionFile(int positionFile) {
        this.positionFile = positionFile;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
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
        return "Livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                '}';
    }
}
