package com.bibliotheque.service;

import com.bibliotheque.models.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    /**
     * Permets de chercher tous les utilisateurs
     *
     * @return liste d'utilisateur
     */
    List<Utilisateur> rechercherTousLesUtilisateurs();

    /**
     * ajouter Utilisateur
     *
     * @param utilisateur information d'un utilisateur pour sauvegard en base
     * @return
     */
    Utilisateur ajouterUtilisateur(Utilisateur utilisateur);


    /**
     * Recherche d'un utilisateur par son num de carte
     * @param numCarte numCarte identifiant unique
     * @return utilisateur avec ce num de carte
     */
    Utilisateur rechercherUtilisateurParNumCarte(String numCarte);
}
