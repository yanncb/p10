package com.bibliotheque.web.Service;

import com.bibliotheque.web.beans.UtilisateurBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UtilisateurService  extends UserDetailsService {

    /**
     * ajout utilisateur
     * @param utilisateur ajoute un utilisateur
     */
    void ajouterUtilisateur(UtilisateurBean utilisateur);
}
