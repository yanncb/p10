package com.bibliotheque.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_utilisateur")
public class Utilisateur {

    @Id
    @SequenceGenerator(name = "UTILISATEUR_SEQ_GENERATOR", sequenceName = "UTILISATEUR_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UTILISATEUR_SEQ_GENERATOR")
    @Column(name = "util_id")
    private int id;

    @Column(name = "util_prenom")
    private String prenom;

    @Column(name = "util_nom")
    private String nom;

    @Column(name = "util_mot_de_passe")
    private String motDePasse;

    @Column(name = "util_adresse")
    private String adresse;

    @Column(name = "util_num_tel")
    private String numeroDeTelephone;

    @Column(name = "util_mail")
    private String mail;

    @Column(name = "util_carte_bibliotheque")
    private String numCarte;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t_roles_utilisateur", joinColumns = @JoinColumn(name = "util_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Exemplaire> exemplaireList;

    public Utilisateur() {
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumeroDeTelephone() {
        return numeroDeTelephone;
    }

    public void setNumeroDeTelephone(String numeroDeTelephone) {
        this.numeroDeTelephone = numeroDeTelephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNumCarte() {
        return numCarte;
    }

    public void setNumCarte(String numCarte) {
        this.numCarte = numCarte;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", adresse='" + adresse + '\'' +
                ", numeroDeTelephone='" + numeroDeTelephone + '\'' +
                ", mail='" + mail + '\'' +
                ", numCarte=" + numCarte +
                '}';
    }
}
