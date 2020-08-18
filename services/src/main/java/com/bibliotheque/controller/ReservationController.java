package com.bibliotheque.controller;

import com.bibliotheque.models.Reservation;
import com.bibliotheque.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping(value = "/exemplaire-plus-de-deux-jours")
    public List<Reservation> listeDeReservationAyantUnExemplaireRevenuDepuisplusDe48h(){
        List<Reservation> reservationList = reservationService.trouverlisteDeReservationAyantUneDateDenvoieDeMail();
        return reservationList;
    }

    @GetMapping(value = "/liste-a-supprimer-plus-48h")
    public void listeDeReservationASupprimer(List<Reservation> reservationList){
        reservationService.supprimerListeDeReservation(reservationList);

    }


}
