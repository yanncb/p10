package com.bibliotheque.service.impl;

import com.bibliotheque.models.Utilisateur;
import com.bibliotheque.repository.RoleRepository;
import com.bibliotheque.repository.UtilisateurRepository;
import com.bibliotheque.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Utilisateur> rechercherTousLesUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(utilisateur.getMotDePasse());
        utilisateur.setAdresse(utilisateur.getAdresse());
        utilisateur.setMail(utilisateur.getMail());
        utilisateur.setNom(utilisateur.getNom());
        utilisateur.setPrenom(utilisateur.getPrenom());
        utilisateur.setNumCarte(utilisateur.getNumCarte());
        utilisateur.setNumeroDeTelephone(utilisateur.getNumeroDeTelephone());
        utilisateurRepository.save(utilisateur);
        return utilisateur;
    }

    @Override
    public Utilisateur rechercherUtilisateurParNumCarte(String numCarte) {
        return utilisateurRepository.findByNumCarte(numCarte);
    }
}
