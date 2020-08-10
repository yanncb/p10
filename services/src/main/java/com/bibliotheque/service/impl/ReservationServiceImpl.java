package com.bibliotheque.service.impl;

import com.bibliotheque.models.Livre;
import com.bibliotheque.models.Reservation;
import com.bibliotheque.models.Utilisateur;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.UtilisateurRepository;
import com.bibliotheque.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    LivreRepository livreRepository;

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    UtilisateurRepository utilisateurRepository;


    @Override
    public List<Reservation> trouverlisteDeReservationAyantUnExemplaireRevenuDepuisplusDe48h() {
        List<Reservation> listeDeReservation = reservationRepository.findAllReservationwithDateDenvoie();
        return listeDeReservation;
    }

    @Override
    public String supprimerListeDeReservation(List<Reservation> reservationList) {
        for (Reservation reservation : reservationList) {
            reservationRepository.delete(reservation);
            Utilisateur utilisateur = utilisateurRepository.findById(premierUtilisateurIdDansLaFileDattente(reservation.getId()));
            Livre livre = livreRepository.findByReservationId(reservation.getId());
            envoieDeMailPourPremierDeLaListeDattente(utilisateur, livre);
        }

        return "Suppresion terminé";
    }


    public int premierUtilisateurIdDansLaFileDattente(int reservationId) {
        Livre livreReserve = livreRepository.findByReservationId(reservationId);
        List<Reservation> listeDattenteDesReservations = reservationRepository.findByLivreOrderById(livreReserve);
        int positionDansLaListeDattente = 1;
        Utilisateur utilisateurAPrevenirParMail = new Utilisateur();

        for (Reservation reservation : listeDattenteDesReservations) {
            if (positionDansLaListeDattente == 1) {
                utilisateurAPrevenirParMail.setId(reservation.getUtilisateur().getId());
                positionDansLaListeDattente++;
            }
        }
        return utilisateurAPrevenirParMail.getId();
    }

    public String envoieDeMailPourPremierDeLaListeDattente(Utilisateur utilisateur, Livre livre) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(utilisateur.getMail());

        message.setSubject("Bonjour, c'est votre bibliotheque vous pouvez venir recuperer l'exemplaire " + livre.getTitre() + "que vous avez reservé");
        message.setText("Vous avez 48h pour venir recuperé votre exemplaire du " + livre.getTitre() + " passé ce delais nous ne pourrons pas vous garantir qu'il sera encore disponible.");

        emailSender.send(message);

        return "Email Sent!";


    }
}
