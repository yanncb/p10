package com.bibliotheque.service.impl;

import com.bibliotheque.models.Livre;
import com.bibliotheque.models.Reservation;
import com.bibliotheque.models.Utilisateur;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LivreServiceImplTest {


    @Mock
    private LivreRepository livreRepositoryMock;

    @Mock
    private ReservationRepository reservationRepositoryMock;


    @InjectMocks
    public LivreServiceImpl mockedLivreService;


    @Test
    void premierUtilisateurIdDansLaFileDattente() {
        // GIVEN
        Livre livre = new Livre();
        Utilisateur utilisateur = new Utilisateur();
        Utilisateur utilisateur1 = new Utilisateur();

        Reservation reservation = new Reservation();
        Reservation reservation1 = new Reservation();

        utilisateur.setId(123);
        utilisateur.setNumCarte("123");
        utilisateur.setPrenom("utilisateur1");
        utilisateur.setNom("utilisateur1");
        utilisateur.setMail("utilisateur1@gmail.com");

        utilisateur1.setId(124);
        utilisateur1.setNumCarte("124");
        utilisateur1.setPrenom("utilisateur2");
        utilisateur1.setNom("utilisateur2");
        utilisateur1.setMail("utilisateur2@gmail.com");

        livre.setId(1111);
        livre.setTitre("titreDuLivre");

        livre.setId(2222);
        livre.setTitre("titreDuLivre2");

        reservation.setUtilisateur(utilisateur);
        reservation.setLivre(livre);

        reservation1.setUtilisateur(utilisateur1);
        reservation1.setLivre(livre);

        Mockito.when(reservationRepositoryMock.findByLivreOrderById(livre)).thenReturn(Arrays.asList(reservation, reservation1));
        Mockito.when(livreRepositoryMock.findById(livre.getId())).thenReturn(livre);

        // WHEN
        int premierUtilisateurIdDansLaFileDattente = mockedLivreService.premierUtilisateurIdDansLaFileDattente(livre.getId());


        //THEN
        assertEquals(premierUtilisateurIdDansLaFileDattente, 123);

    }

    @Test
    void prolongerEmPrunt() {
    }

    @Test
    void rechercherDateRetourLaPlusproche() {
    }

    @Test
    void reservationLivre() {
    }

    @Test
    void annulerReservation() {
    }

    @Test
    void creerEmprunt() {
    }

    @Test
    void retourEmprunt() {
    }

    @Test
    void envoieDeMailPourPremierDeLaListeDattente() {
    }
}