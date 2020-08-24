package com.bibliotheque.service.impl;

import com.bibliotheque.models.Exemplaire;
import com.bibliotheque.models.Livre;
import com.bibliotheque.models.Reservation;
import com.bibliotheque.models.Utilisateur;
import com.bibliotheque.repository.ExemplaireRepository;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LivreServiceImplTest {


    @Mock
    private LivreRepository livreRepositoryMock;

    @Mock
    private ReservationRepository reservationRepositoryMock;

    @Mock
    private ExemplaireRepository exemplaireRepositoryMock;

    @Mock
    private UtilisateurRepository utilisateurRepositoryMock;


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
        Utilisateur premierUtilisateurIdDansLaFileDattente = mockedLivreService.premierUtilisateurDansLaFileDattente(livre.getId());


        //THEN
        assertEquals(premierUtilisateurIdDansLaFileDattente.getId(), 123);

    }

    @Test
    void prolongerEmPrunt() {
        //GIVEN
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setId(123);
        exemplaire.setProlongerEmprunt(false);
        exemplaire.setDateDemprunt(LocalDate.now());

        Mockito.when(exemplaireRepositoryMock.findById(exemplaire.getId())).thenReturn(exemplaire);
        //WHEN
        Exemplaire exemplaireProlonger = mockedLivreService.prolongerEmPrunt(exemplaire.getId());

        //THEN
        assertTrue(exemplaireProlonger.isProlongerEmprunt());

        assertEquals(exemplaireProlonger.getDateRetour(), LocalDate.now().plusDays(60));
    }

    @Test
    void rechercherDateRetourLaPlusproche() {

        //GIVEN
        Livre livre = new Livre();
        Exemplaire exemplaire1 = new Exemplaire();
        Exemplaire exemplaire2 = new Exemplaire();
        Exemplaire exemplaire3 = new Exemplaire();

        exemplaire1.setDateDemprunt(LocalDate.now().minusDays(2));
        exemplaire1.setDateRetour(LocalDate.now());
        exemplaire1.setId(1111);
        exemplaire1.setPret(true);

        exemplaire2.setDateDemprunt(LocalDate.now().minusDays(10));
        exemplaire2.setDateRetour(LocalDate.now().plusDays(8));
        exemplaire2.setId(2222);
        exemplaire2.setPret(true);


        exemplaire3.setDateDemprunt(LocalDate.now().minusDays(12));
        exemplaire3.setDateRetour(LocalDate.now().plusDays(10));
        exemplaire3.setId(3333);
        exemplaire3.setPret(true);


        List<Exemplaire> listeDExemplaireAMettreDansLeLivre = new ArrayList<>();
        listeDExemplaireAMettreDansLeLivre.add(exemplaire1);
        listeDExemplaireAMettreDansLeLivre.add(exemplaire2);
        listeDExemplaireAMettreDansLeLivre.add(exemplaire3);


        livre.setId(123);
        livre.setExemplaireList(listeDExemplaireAMettreDansLeLivre);

        Mockito.when(livreRepositoryMock.findById(livre.getId())).thenReturn(livre);

        //WHEN
        Livre livreAvecDateDeRetourlaPlusProche = mockedLivreService.rechercherDateRetourLaPlusproche(livre.getId());

        Exemplaire exemplairePlusProcheAGarder = livreAvecDateDeRetourlaPlusProche.getExemplaireList().get(0);

        //THEN
        assertEquals(exemplairePlusProcheAGarder.getId(), 1111);
    }


    @Test
    void reservationLivre() {
        Livre livre = new Livre();
        Utilisateur utilisateur = new Utilisateur();
        Reservation reservation = new Reservation();
        Exemplaire exemplaire = new Exemplaire();

        exemplaire.setId(11);
        exemplaire.setDateDemprunt(LocalDate.now());
        exemplaire.setPret(true);
        exemplaire.setDateRetour(LocalDate.now().plusDays(30));
        List<Exemplaire> exemplaireList = new ArrayList<>();
        exemplaireList.add(exemplaire);


        utilisateur.setId(1111);
        utilisateur.setNom("toto");

        livre.setId(111);
        livre.setTitre("livreNom");
        livre.setExemplaireList(exemplaireList);

        reservation.setLivre(livre);
        reservation.setUtilisateur(utilisateur);

        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation);

        livre.setReservationList(reservationList);


        Mockito.when(livreRepositoryMock.findById(livre.getId())).thenReturn(livre);
        Mockito.when(utilisateurRepositoryMock.findById(utilisateur.getId())).thenReturn(utilisateur);
        Mockito.when(reservationRepositoryMock.findBylivreIdAndUtilisateurId(livre.getId(), utilisateur.getId())).thenReturn(reservation);

        Livre livreAvecListDeResa = mockedLivreService.reservationLivre(livre.getId(), utilisateur.getId());

        assertEquals(livreAvecListDeResa.getExemplaireList().size(), 1);
    }

    @Test
    void creerEmprunt() {
        Livre livre = new Livre();
        Utilisateur utilisateur = new Utilisateur();
        Exemplaire exemplaire = new Exemplaire();
        Reservation reservation = new Reservation();

        exemplaire.setId(11);
        exemplaire.setDateDemprunt(LocalDate.now());
        exemplaire.setPret(false);
        exemplaire.setDateRetour(LocalDate.now().plusDays(30));
        List<Exemplaire> exemplaireList = new ArrayList<>();
        exemplaireList.add(exemplaire);


        utilisateur.setId(1111);
        utilisateur.setNom("toto");

        livre.setId(111);
        livre.setTitre("livreNom");
        livre.setExemplaireList(exemplaireList);

        List<Reservation> reservationList = new ArrayList<>();

        livre.setReservationList(reservationList);
        reservation.setLivre(livre);
        reservation.setUtilisateur(utilisateur);
        reservationList.add(reservation);

        livre.setReservationList(reservationList);
        Mockito.when(reservationRepositoryMock.findBylivreIdAndUtilisateurId(livre.getId(), utilisateur.getId())).thenReturn(reservation);
        Mockito.when(exemplaireRepositoryMock.findById(exemplaire.getId())).thenReturn(exemplaire);
        Mockito.when(utilisateurRepositoryMock.findById(utilisateur.getId())).thenReturn(utilisateur);
        Mockito.when(exemplaireRepositoryMock.save(exemplaire)).thenReturn(exemplaire);

        Exemplaire exemplaire1 = mockedLivreService.creerEmprunt(exemplaire.getId(), livre.getId(), utilisateur.getId());

        assertEquals(exemplaire.isPret(), true);

    }

    @Test
    void retourEmprunt() {

        //GIVEN
        Livre livre = new Livre();
        Utilisateur utilisateur = new Utilisateur();
        Exemplaire exemplaire = new Exemplaire();
        Reservation reservation = new Reservation();

        exemplaire.setId(11);
        exemplaire.setDateDemprunt(LocalDate.now());
        exemplaire.setPret(false);
        exemplaire.setDateRetour(LocalDate.now().plusDays(30));
        List<Exemplaire> exemplaireList = new ArrayList<>();
        exemplaireList.add(exemplaire);


        utilisateur.setId(1111);
        utilisateur.setNom("toto");

        livre.setId(111);
        livre.setTitre("livreNom");
        livre.setExemplaireList(exemplaireList);

        List<Reservation> reservationList = new ArrayList<>();

        reservation.setLivre(livre);
        reservation.setUtilisateur(utilisateur);
        reservationList.add(reservation);
        livre.setReservationList(reservationList);


        Mockito.when(livreRepositoryMock.findByExemplaireList(exemplaire)).thenReturn(livre);
        Mockito.when(exemplaireRepositoryMock.findById(exemplaire.getId())).thenReturn(exemplaire);
        Mockito.when(reservationRepositoryMock.findBylivreIdAndUtilisateurId(livre.getId(), utilisateur.getId())).thenReturn(null);
        Mockito.when(livreRepositoryMock.findById(livre.getId())).thenReturn(livre);
        Mockito.when(reservationRepositoryMock.findByLivreOrderById(livre)).thenReturn(reservationList);

        //WHERE
        Exemplaire exemplaire1 = mockedLivreService.retourEmprunt(exemplaire.getId());

        //THEN
        assertEquals(exemplaire1.isPret(), false);
        assertEquals(exemplaire.isProlongerEmprunt(), false);

        assertNull(exemplaire.getUtilisateur());
        assertNull(exemplaire.getDateDemprunt());

    }

    @Test
    void rechercherTousLesLivres() {
        List<Livre> livreList = new ArrayList<>();
        List<Livre> livreList2 = new ArrayList<>();

        Livre livre1 = new Livre();
        Livre livre2 = new Livre();
        Livre livre3 = new Livre();

        livre1.setTitre("livre1 titre");
        livre1.setId(111);

        livre2.setTitre("livre2 titre");
        livre2.setId(222);

        livre3.setTitre("livre3 titre");
        livre3.setId(333);

        livreList.add(livre1);
        livreList.add(livre2);
        livreList.add(livre3);

        Mockito.when(livreRepositoryMock.findAll()).thenReturn(livreList);

        livreList2 = mockedLivreService.rechercherTousLesLivres();

        assertEquals(livreList2.size(), 3);


    }

    @Test
    void rechercherParAuteurOuTitre() {
        List<Livre> livreList = new ArrayList<>();
        List<Livre> livreList2 = new ArrayList<>();

        Livre livre1 = new Livre();
        Livre livre2 = new Livre();
        Livre livre3 = new Livre();

        livre1.setTitre("livre1 titre");
        livre1.setId(111);
        livre1.setTitre("toto");
        livre1.setAuteur("jkr");

        livre2.setTitre("livre2 titre");
        livre2.setId(222);
        livre2.setTitre("titi");
        livre2.setAuteur("jkr");

        livre3.setTitre("livre3 titre");
        livre3.setId(333);
        livre3.setTitre("tata");
        livre3.setAuteur("jkr");

        livreList.add(livre1);
        livreList.add(livre2);
        livreList.add(livre3);

        Mockito.when(livreRepositoryMock.rechercherParAuteurOuTitre("%jkr%")).thenReturn(livreList);

        livreList2 = mockedLivreService.rechercherParAuteurOuTitre("jkr");

        assertEquals(livreList2.size(), 3);

    }
}