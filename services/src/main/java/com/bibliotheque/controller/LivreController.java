package com.bibliotheque.controller;

import com.bibliotheque.exception.LivreNotFoundexception;
import com.bibliotheque.models.Exemplaire;
import com.bibliotheque.models.Livre;
import com.bibliotheque.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LivreController {

    @Autowired
    LivreService livreService;



    @GetMapping(value = "/livres")
    public List<Livre> listeDeLivre() {

        List<Livre> livres = livreService.rechercherTousLesLivres();
        if (livres.isEmpty()) throw new LivreNotFoundexception("Aucun livre n'est disponible pour le moment");

        return livres;
    }

    @GetMapping(value = "/livres-en-retard")
    public List<Livre> listeDeLivreEnRetard(){
        List<Livre> livreList = livreService.trouverLesLivresDontLesExemplairesSontEnRetard();
        return livreList;
    }

    @GetMapping(value = "/livre/{livreId}")
    public Livre recupererUnLivreParId(@PathVariable int livreId) {
        Livre livre = livreService.findById(livreId);

        return livre;
    }


    @GetMapping(value = "/recherche")
    List<Livre> rechercherLivres(@RequestParam("motCle") String motCle) {
        String motCleRecherche = "%" + motCle + "%";

        return livreService.rechercherParAuteurOuTitre(motCleRecherche);
    }

    @GetMapping(value = "liste-de-mes-emprunts/{utilisateurId}")
    public List<Livre> exemplaireList(@PathVariable("utilisateurId") Integer id) {
        List<Livre> livreList = livreService.rechercherTousLesLivresPourUtilisateur(id);
        return livreList;
    }

    @GetMapping(value = "liste-de-mes-reservations/{utilisateurId}")
    public List<Livre> listeExemplaireReserve(@PathVariable("utilisateurId") Integer id) {
        List<Livre> livreList = livreService.rechercherTousLesLivresReserveParUtilisateur(id);
        return livreList;
    }

    @PostMapping("prolonger-emprunt/{exemplaireId}")
    public Exemplaire prolongerEmprunt(@PathVariable("exemplaireId") Integer id) {
        Exemplaire exemplaire = livreService.prolongerEmPrunt(id);
        return exemplaire;
    }

    @GetMapping("/prochaineDispo/{livreId}")
    public Exemplaire dateProchaineDispo(@PathVariable("livreId") int livreId){
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setProchaineDispo(LocalDate.now());
        return exemplaire;
    }

    @PostMapping("/reserverLivre/{livreId}")
    public Livre reserverLivre(@PathVariable("livreId") int livreId){
        Livre livre = new Livre();
        return livre;
    }

    @GetMapping("/rechercherLivreReserveParUtilisateur/{utilisateurId}")
    public List<Livre> rechercherTousLesLivresReserverParUtilisateurAvecProchainExemplaireDisponible(@PathVariable("utilisateurId") int utilisateurId){
        List<Livre> livreList = new ArrayList<>();
        Livre livre = new Livre();
        livre.setId(12);
        livre.setAuteur("Rulio");
        livre.setTitre("julio");
        List<Exemplaire> exemplaireList = new ArrayList<>() ;
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setProchaineDispo(LocalDate.now());
        exemplaire.setPositionFile(2);
        exemplaireList.add(exemplaire);
        livre.setExemplaireList(exemplaireList);
        livreList.add(livre);

        return livreList;
    }

    @PostMapping(value = "/annuler-reservation/{livreId}")
    public Livre annulerReservation(@PathVariable("livreId") Integer livreId){
        return null;
    }

    // -------------------------------------------- PARTIE RESERVE AU PERSONNEL -------------------------------------

    @PostMapping(value = "creer-emprunt/{exemplaireId}/{utilisateurId}")
    public Exemplaire creerEmprunt(@PathVariable("exemplaireId") Integer exemplaireId, @PathVariable ("utilisateurId") Integer utilisateurId) {
        Exemplaire exemplaire = livreService.creerEmprunt(exemplaireId, utilisateurId);
        return exemplaire;
    }

    @GetMapping(value = "retour-emprunt/{exemplaireId}")
    public Exemplaire retourEmprunt(@PathVariable("exemplaireId") Integer exemplaireId) {
        Exemplaire exemplaire = livreService.retourEmprunt(exemplaireId);
        return exemplaire;
    }



}
