package com.bibliotheque.service.impl;

import com.bibliotheque.models.Reservation;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;


    @Override
    public List<Reservation> trouverlisteDeReservationAyantUnExemplaireRevenuDepuisplusDe48h() {
        List<Reservation> listeDeReservation = reservationRepository.findAllReservationwithDateDenvoie();
        return listeDeReservation;
    }
}
