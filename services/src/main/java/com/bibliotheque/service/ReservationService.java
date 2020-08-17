package com.bibliotheque.service;

import com.bibliotheque.models.Reservation;

import java.util.List;

public interface ReservationService {

    /**
     * Recupere la liste de reservation ayant une date d'envoie de mail ce qui veut dire qu'une reservation est en cours.
     *
     * @return liste de reservation
     */
    List<Reservation> trouverlisteDeReservationAyantUnExemplaireRevenuDepuisplusDe48h();

    void supprimerListeDeReservation(List<Reservation> reservationList);
}
