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

import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<Reservation> trouverlisteDeReservationAyantUneDateDenvoieDeMail() {
        List<Reservation> listeDeReservation = reservationRepository.findAll();
        List<Reservation> listeDeReservationAvecUneDateDenvoieDeMAil = new ArrayList<>();
        for (Reservation reservation : listeDeReservation) {
            if (reservation.getDateEnvoieMail() != null) {
                listeDeReservationAvecUneDateDenvoieDeMAil.add(reservation);
            }

        }
        return listeDeReservationAvecUneDateDenvoieDeMAil;
    }

    @Override
    public void supprimerListeDeReservation(List<Reservation> reservationList) {
        for (Reservation reservation : reservationList) {
            reservationRepository.delete(reservation);
            Livre livre = livreRepository.findByReservationId(reservation.getId());
            Utilisateur utilisateur = premierUtilisateurDansLaFileDattente(livre.getId());
            envoieDeMailPourPremierDeLaListeDattente(utilisateur, livre);
            reservation.setDateEnvoieMail(LocalDate.now());
            reservationRepository.save(reservation);
        }

    }

        public Utilisateur premierUtilisateurDansLaFileDattente(int livreId) {
            Livre livreReserve = livreRepository.findById(livreId);
            List<Reservation> listeDattenteDesReservations = reservationRepository.findByLivreOrderById(livreReserve);
            if (listeDattenteDesReservations.isEmpty()) {
                return null;
            }
            return listeDattenteDesReservations.get(0).getUtilisateur();

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




//    public int premierUtilisateurIdDansLaFileDattente(int reservationId) {
//        Livre livreReserve = livreRepository.findByReservationId(reservationId);
//        List<Reservation> listeDattenteDesReservations = reservationRepository.findByLivreOrderById(livreReserve);
//        int positionDansLaListeDattente = 1;
//        Utilisateur utilisateurAPrevenirParMail = new Utilisateur();
//
//        for (Reservation reservation : listeDattenteDesReservations) {
//            if (positionDansLaListeDattente == 1) {
//                utilisateurAPrevenirParMail.setId(reservation.getUtilisateur().getId());
//                positionDansLaListeDattente++;
//            }
//        }
//
//        return utilisateurAPrevenirParMail.getId();
//}
