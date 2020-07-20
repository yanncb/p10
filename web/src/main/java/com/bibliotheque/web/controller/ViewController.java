package com.bibliotheque.web.controller;

import com.bibliotheque.web.Service.ExemplaireService;
import com.bibliotheque.web.beans.ExemplaireBean;
import com.bibliotheque.web.beans.LivreBean;
import com.bibliotheque.web.beans.UtilisateurBean;
import com.bibliotheque.web.proxies.MServiceBack;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
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
        ExemplaireBean exemplaireBean = rechercherLivres.prochaineDispo(livre.getId());
        UtilisateurBean utilisateurBean = (UtilisateurBean) authentication.getPrincipal();
//        ExemplaireBean exemplaireBean = new ExemplaireBean();
//        exemplaireBean.setProchaineDispo(LocalDate.now());
        //TODO ajouter condition pour que le bouton ne réaparaisse quand on a deja une reservation ...
        model.addAttribute("exemplaireBean", exemplaireBean);
        model.addAttribute("nbExemplaires", nbExemplaires);
        model.addAttribute("livre", livre);
        model.addAttribute("utilisateur", utilisateurBean);


        return "livre";
    }

    @GetMapping(value = "/reserver/{livreId}")
    public String reserverUnLivreDesProchaineDispo(Model model, @PathVariable int livreId) {
        LivreBean livre = rechercherLivres.recupererUnLivre(livreId);
        model.addAttribute("livre", livre);
        rechercherLivres.reserverLivre(livreId);

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
        List<LivreBean> livreBeansList = rechercherLivres.rechercherTousLesLivresReserveParUtilisateur(utilisateurBean.getId());
        List<LivreBean> livreAvecUnSeulExemplaireList = rechercherLivres.rechercherTousLesLivresReserverParUtilisateurAvecProchainExemplaireDisponible(utilisateurBean.getId());

//        ExemplaireBean exemplaireBean = new ExemplaireBean();
//        exemplaireBean.setPositionFile(1);
//        exemplaireBean.setProchaineDispo(LocalDate.now());
        //TODO Avoir un seul exemplaire par livre.
        // TODO creer methode rechercherTousLesLivresReserverParUtilisateurAvecProchainExemplaireDisponible
        model.addAttribute("livreAvec", livreAvecUnSeulExemplaireList);
        model.addAttribute("livreBeans", livreBeansList);
//        model.addAttribute("exemplaireBean", exemplaireBean);
        return "liste-de-mes-reservations";
    }

    @GetMapping(value = "/annuler-reservation/{livreId}")
    public String annulerReservation(Model model, @PathVariable int livreId){
        LivreBean livre = rechercherLivres.recupererUnLivre(livreId);
        model.addAttribute("livre", livre);
        rechercherLivres.annulerReservation(livreId);

        return "redirect:/liste-de-mes-reservations";

    }
}


