package com.bibliotheque.web.controller;

import com.bibliotheque.web.Service.ExemplaireService;
import com.bibliotheque.web.beans.LivreBean;
import com.bibliotheque.web.beans.UtilisateurBean;
import com.bibliotheque.web.proxies.MServiceBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private MServiceBack rechercherLivres;

    @GetMapping(value = "/")
    public String welcome(Model model) {
        return "/index";
    }

    @GetMapping(value = "/livres")
    public String afficherTousLesLivres(Model model) {
        List<LivreBean> livres = rechercherLivres.listeDesLivres();
        model.addAttribute("livres", livres);
        return "livres";
    }

    @GetMapping(value = "/livre/{id}")
    public String afficherLivreParId(Model model, @PathVariable int id, Authentication authentication) {

        LivreBean livre = rechercherLivres.recupererUnLivre(id);
        int nbExemplaires = exemplaireService.calculNbDispo(livre);
        int nbUtilisateurAyantReserve = exemplaireService.calculNbReservation(livre);
        LivreBean livreBean = rechercherLivres.prochaineDispo(livre.getId());
        UtilisateurBean utilisateurBean = (UtilisateurBean) authentication.getPrincipal();
        //TODO ajouter condition pour que le bouton ne réaparaisse quand on a deja une reservation ...
        model.addAttribute("livreBean", livreBean);
        model.addAttribute("nbExemplaires", nbExemplaires);
        model.addAttribute("nbReservation", nbUtilisateurAyantReserve);
        model.addAttribute("livre", livre);
        model.addAttribute("utilisateur", utilisateurBean);

        return "livre";
    }

//TODO FINIR
    @GetMapping(value = "/reserver/{livreId}/{utilisateurId}")
    public String reserverUnLivre(Model model, @PathVariable int livreId, Authentication authentication) {
        LivreBean livre = rechercherLivres.recupererUnLivre(livreId);
        UtilisateurBean utilisateurBean = (UtilisateurBean) authentication.getPrincipal();
        model.addAttribute("livre", livre);
        model.addAttribute("utilisateur", utilisateurBean);
        rechercherLivres.reserverLivre(livreId, utilisateurBean.getId());

        return "redirect:/liste-de-mes-reservations";
    }

    @GetMapping(value = "/liste-de-mes-emprunts")
    public String afficherMesEmprunts(Model model, Authentication authentication) {
        UtilisateurBean utilisateurBean = (UtilisateurBean) authentication.getPrincipal();
        List<LivreBean> livreBeans = rechercherLivres.rechercherTousLesLivresPourUtilisateur(utilisateurBean.getId());
        model.addAttribute("livreBeans", livreBeans);
        return "liste-de-mes-emprunts";
    }

    @GetMapping(value = "/prolonger-emprunt/{exemplaireId}")
    public String affichageConfirmationProlongation(Model model, @PathVariable int exemplaireId) {
        LivreBean livre = rechercherLivres.recupererUnLivre(exemplaireId);
        model.addAttribute("livres", livre);
        rechercherLivres.prolongeremprunt(exemplaireId);

        return "redirect:/liste-de-mes-emprunts";
    }

    @GetMapping(value = "/liste-de-mes-reservations")
    public String afficherMesReservations(Model model, Authentication authentication) {
        UtilisateurBean utilisateurBean = (UtilisateurBean) authentication.getPrincipal();
        List<LivreBean> livreAvecUnSeulExemplaireQuiALaDateDeRetourLaPlusProcheList = rechercherLivres.rechercherTousLesLivresReserverParUtilisateurAvecProchainExemplaireDisponible(utilisateurBean.getId());
        model.addAttribute("livreAvec", livreAvecUnSeulExemplaireQuiALaDateDeRetourLaPlusProcheList);
        model.addAttribute("utilisateurId", ((UtilisateurBean) authentication.getPrincipal()).getId());
        return "liste-de-mes-reservations";
    }
//TODO FINIR
    @GetMapping(value = "/annuler-reservation/{livreId}/{utilisateurId}")
    public String annulerReservation(Model model, @PathVariable int livreId, Authentication authentication) {
        LivreBean livre = rechercherLivres.recupererUnLivre(livreId);
        UtilisateurBean utilisateurBean = (UtilisateurBean) authentication.getPrincipal();
        rechercherLivres.annulerReservation(livreId, utilisateurBean.getId());
        model.addAttribute("livre", livre);
        model.addAttribute("utilisateur", ((UtilisateurBean) authentication.getPrincipal()).getId());
        return "redirect:/liste-de-mes-reservations";

    }
}


