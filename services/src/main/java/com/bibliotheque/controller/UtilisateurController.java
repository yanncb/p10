package com.bibliotheque.controller;

import com.bibliotheque.models.Utilisateur;
import com.bibliotheque.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;


    @GetMapping("/connexionUtilisateur/{numCarte}")
    Utilisateur connexionUtilisateur(@PathVariable("numCarte") String numCarte){
       Utilisateur utilisateur = utilisateurService.rechercherUtilisateurParNumCarte(numCarte);
        return utilisateur;
    }

    @PostMapping("/ajout-utilisateur")
    Utilisateur ajouterUtilisateur(@RequestBody Utilisateur utilisateur){
        Utilisateur utilisateur1 = utilisateurService.ajouterUtilisateur(utilisateur);
        return utilisateur1;
    }

}
