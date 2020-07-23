package com.bibliotheque.service.impl;

import com.bibliotheque.models.Exemplaire;
import com.bibliotheque.models.Livre;
import com.bibliotheque.models.Reservation;
import com.bibliotheque.models.Utilisateur;
import com.bibliotheque.repository.ExemplaireRepository;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.UtilisateurRepository;
import com.bibliotheque.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivreServiceImpl implements LivreService {

    public static final int PERIODE_PROLONGEE_DE_PRET = 60;
    private static final int PERIODE_INITIALE_DE_PRET = 30;


    @Autowired
    LivreRepository livreRepository;

    @Autowired
    ExemplaireRepository exemplaireRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public List<Livre> rechercherTousLesLivres() {
        return livreRepository.findAll();
    }

    @Override
    public Livre findById(Integer id) {
        Optional<Livre> optionalLivre = livreRepository.findById(id);
        if (optionalLivre.isPresent()) {
            return optionalLivre.get();
        }
        return null;
    }

    @Override
    public List<Livre> rechercherParAuteurOuTitre(String motCle) {

        String motCleRecherche = "%" + motCle + "%";
        List<Livre> livreList = livreRepository.rechercherParAuteurOuTitre(motCleRecherche);

        return livreList;
    }


    @Override
    public List<Livre> rechercherTousLesLivresPourUtilisateur(int id) {
        List<Livre> livres = livreRepository.rechercherTousLesLivresPourUtilisateur(id);
        for (Livre livre : livres) {
            for (Exemplaire exemplaire : livre.getExemplaireList()) {
                calculerDateRetour(exemplaire);
            }
        }
        return livres;
    }

    //TODO finir les boucles for pour y inclure le nombre de personnes avant nous prochaine dispo, et numero dans la file. Je veux retourner la liste de livre reservé par utilisateur + date de retour la plus proche pour chaque livre
    public List<Livre> rechercherTousLesLivresReserveParUtilisateur(int utilisateurId) {
        List<Livre> livreList = livreRepository.rechercherTousLesLivresReserveParUtilisateur(utilisateurId);

        return livreList;
    }


    //TODO A TESTER
    private Exemplaire calculerDateRetour(Exemplaire exemplaire) {
        if (exemplaire.isProlongerEmprunt()) {
            exemplaire.setDateRetour(exemplaire.getDateDemprunt().plusDays(PERIODE_PROLONGEE_DE_PRET));
        } else {
            exemplaire.setDateRetour(exemplaire.getDateDemprunt().plusDays(PERIODE_INITIALE_DE_PRET));
        }
        return exemplaire;
    }

    //TODO A TESTER en MOCK
    public List<Livre> trouverLesLivresDontLesExemplairesSontEnRetard() {
        List<Livre> livresEmpruntes = livreRepository.rechercherLivreDontExemplaireEnRetard();
        List<Livre> livreARendre = new ArrayList<>();

        for (Livre livre : livresEmpruntes) {
            List<Exemplaire> exemplairesARendre = new ArrayList<>();

            for (Exemplaire exemplaire : livre.getExemplaireList()) {
                calculerDateRetour(exemplaire);
                if (exemplaire.getDateRetour().isBefore(LocalDate.now()) || exemplaire.getDateRetour().isEqual(LocalDate.now())) {
                    exemplairesARendre.add(exemplaire);
                }
            }

            if (!exemplairesARendre.isEmpty()) {
                livreARendre.add(livre);
            }
        }

        return livreARendre;
    }


    @Override
    public Exemplaire prolongerEmPrunt(int exemplaireId) {

        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId);
        exemplaire.setProlongerEmprunt(true);
        exemplaireRepository.save(exemplaire);
        calculerDateRetour(exemplaire);

        return exemplaire;
    }

    // TODO A VERIFIER
    public Livre rechercherDateRetourLaPlusproche(int livreId) {

        Livre livre = livreRepository.findById(livreId);
        List<Exemplaire> listExemplaire = new ArrayList<>();
        Exemplaire exemplaireComparaison = new Exemplaire();
        exemplaireComparaison.setDateRetour(LocalDate.now().minusMonths(1));

        for (Exemplaire exemplaire1 : livre.getExemplaireList()) {

            if (exemplaire1.isPret()) {
                calculerDateRetour(exemplaire1);
                if (exemplaireComparaison.getDateRetour().isBefore(exemplaire1.getDateRetour()) && exemplaire1.getDateRetour().isAfter(LocalDate.now()))
                    exemplaireComparaison = exemplaire1;
            }
            exemplaireComparaison.setProchaineDispo(exemplaireComparaison.getDateRetour());

        }
        listExemplaire.add(exemplaireComparaison);
        livre.setExemplaireList(listExemplaire);


        return livre;
    }

    public Livre reservationLivre(int livreId, int utilisateurId) {
        Livre livre = livreRepository.findById(livreId);
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId);
        Reservation reservation = new Reservation();
        reservation.setUtilisateur(utilisateur);
        reservation.setLivre(livre);
        reservationRepository.save(reservation);

        return livre;
    }

    public List<Livre> rechercherTousLesLivresReserverParUtilisateurAvecProchainExemplaireDisponible(int utilisateurId) {

        List<Livre> livreList = rechercherTousLesLivresReserveParUtilisateur(utilisateurId);
        for (Livre livre : livreList) {
            rechercherDateRetourLaPlusproche(livre.getId());
        }

        return livreList;
    }

    public void annulerReservation(int livreId, int utilisateurId) {
       Reservation reservation = reservationRepository.findBylivreIdAndUtilisateurId(livreId, utilisateurId);
        reservationRepository.delete(reservation);
    }


    // -------------------------------------------- PARTIE RESERVE AU PERSONNEL -------------------------------------

    //TODO A TESTER en MOCK

    @Override
    public Exemplaire creerEmprunt(int exemplaireId, int utilisateurId) {
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId);
        exemplaire.setPret(true);
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId);
        exemplaire.setUtilisateur(utilisateur);
        exemplaire.setDateDemprunt(LocalDate.now());
        exemplaireRepository.save(exemplaire);
        return exemplaire;
    }

    //TODO A TESTER en MOCK

    @Override
    public Exemplaire retourEmprunt(int exemplaireId) {
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId);
        exemplaire.setPret(false);
        exemplaire.setUtilisateur(null);
        exemplaire.setDateDemprunt(null);
        exemplaire.setProlongerEmprunt(false);
        exemplaireRepository.save(exemplaire);
        return exemplaire;
    }

}
