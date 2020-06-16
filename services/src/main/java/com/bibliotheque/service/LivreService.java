package com.bibliotheque.service;

import com.bibliotheque.models.Exemplaire;
import com.bibliotheque.models.Livre;

import java.util.List;

public interface LivreService {

    /**
     * Permets de chercher tous les utilisateurs
     *
     * @return liste d'utilisateur
     */
    List<Livre> rechercherTousLesLivres();

    /**
     * Permets de cherche un livre par son id
     *
     * @param id identifiant unique
     * @return livre
     */
    Livre findById(Integer id);

    /**
     * Rechercher une liste de livre par un mot clé
     *
     * @param motCle nom ou titre
     * @return le LIVRE
     */
    List<Livre> rechercherParAuteurOuTitre(String motCle);

    /**
     * Methode permettant de retorne la liste de livre etant en pret chez un utilisateur.
     * @param id id de l'utilisateur
     * @return liste de livre detenu par un utilisateur
     */
    List<Livre> rechercherTousLesLivresPourUtilisateur(int id);

    /**
     * Prolongation de l'emprunt de 4 semaines
     *
     * @param exemplaireId id de l'exemplaire pour savoir quel exemplaire a prolonger
     * @return exemplaire.
     */
    Exemplaire prolongerEmPrunt(int exemplaireId);

    /**
     * creer un emprunt pour le personnel de la bibliotheque
     *
     * @param exemplaireId  exemplaire id pour identifier l'exemplaire
     * @param utilisateurId utilisateur id pour identifier l'utilisateur emprunteur
     * @return exemplaire.
     */
    Exemplaire creerEmprunt(int exemplaireId, int utilisateurId);

    /**
     * retour d'un pret
     *
     * @param exemplaireId L'id de l'exemplaire pour trouver les informations neccessaire aux retours
     * @return exemplaire
     */
    Exemplaire retourEmprunt(int exemplaireId);


    /**
     * Chercher les livre dont les exemplaires sont en retard
     * @return liste de livre dont les exemplaires sont en retard.
     */
     List<Livre> trouverLesLivresDontLesExemplairesSontEnRetard();

    }


