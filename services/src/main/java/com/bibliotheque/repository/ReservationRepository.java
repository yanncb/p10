package com.bibliotheque.repository;

import com.bibliotheque.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("select r from Reservation r where r.livre.id = :livreId and r.utilisateur.id = :utilisateurId")
    Reservation findBylivreIdAndUtilisateurId(int livreId, int utilisateurId);
}
