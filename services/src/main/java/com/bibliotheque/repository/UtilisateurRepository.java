package com.bibliotheque.repository;

import com.bibliotheque.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Utilisateur findByNumCarte(String numCarte);

    Utilisateur findById(int utilisateurId);
}
