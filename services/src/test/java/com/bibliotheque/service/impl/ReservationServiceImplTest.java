package com.bibliotheque.service.impl;

import com.bibliotheque.models.Livre;
import com.bibliotheque.models.Reservation;
import com.bibliotheque.models.Utilisateur;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepositoryMock;

    @Mock
    private LivreRepository livreRepositoryMock;

    @Mock
    private JavaMailSender emailSenderMock;

    @Mock
    private UtilisateurRepository utilisateurRepositoryMock;

    @InjectMocks
    public ReservationServiceImpl mockedReservationService;

    @Test
    void trouverlisteDeReservationAyantUneDateDenvoieDeMail() {
        Utilisateur utilisateur1 = new Utilisateur();
        Utilisateur utilisateur2 = new Utilisateur();

        Livre livre1 = new Livre();
        Livre livre2 = new Livre();

        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        Reservation reservation3 = new Reservation();

        List<Reservation> reservationList = new ArrayList<>();

        utilisateur1.setId(111);
        utilisateur1.setPrenom("toto");

        utilisateur2.setId(222);
        utilisateur2.setPrenom("titi");

        livre1.setId(1111);
        livre1.setTitre("titre1");

        livre2.setId(2222);
        livre2.setTitre("titre2");

        reservation1.setUtilisateur(utilisateur1);
        reservation1.setLivre(livre1);
        reservation1.setDateEnvoieMail(LocalDate.now());

        reservation2.setUtilisateur(utilisateur2);
        reservation2.setLivre(livre1);

        reservation3.setUtilisateur(utilisateur1);
        reservation3.setLivre(livre2);

        reservationList.add(reservation1);
        reservationList.add(reservation2);
        reservationList.add(reservation3);

        Mockito.when(reservationRepositoryMock.findAll()).thenReturn(reservationList);

        List<Reservation> reservationList1 = mockedReservationService.trouverlisteDeReservationAyantUneDateDenvoieDeMail();

        assertEquals(reservationList1.size(), 1);

    }

    @Test
    void premierUtilisateurDansLaFileDattente() {
        Utilisateur utilisateur1 = new Utilisateur();
        Utilisateur utilisateur2 = new Utilisateur();

        Livre livre1 = new Livre();
        Livre livre2 = new Livre();

        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        Reservation reservation3 = new Reservation();

        List<Reservation> reservationList = new ArrayList<>();

        utilisateur1.setId(111);
        utilisateur1.setPrenom("toto");

        utilisateur2.setId(222);
        utilisateur2.setPrenom("titi");

        livre1.setId(1111);
        livre1.setTitre("titre1");

        livre2.setId(2222);
        livre2.setTitre("titre2");

        reservation1.setUtilisateur(utilisateur1);
        reservation1.setLivre(livre1);
        reservation1.setDateEnvoieMail(LocalDate.now());

        reservation2.setUtilisateur(utilisateur2);
        reservation2.setLivre(livre1);

        reservation3.setUtilisateur(utilisateur1);
        reservation3.setLivre(livre2);

        reservationList.add(reservation1);
        reservationList.add(reservation2);
        reservationList.add(reservation3);

        Mockito.when(livreRepositoryMock.findById(livre1.getId())).thenReturn(livre1);
        Mockito.when(reservationRepositoryMock.findByLivreOrderById(livre1)).thenReturn(reservationList);

        Utilisateur utilisateur = mockedReservationService.premierUtilisateurDansLaFileDattente(livre1.getId());

        assertEquals(utilisateur.getId(), 111);
    }

}