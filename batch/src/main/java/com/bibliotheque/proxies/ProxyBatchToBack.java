package com.bibliotheque.proxies;

import com.bibliotheque.models.Exemplaire;
import com.bibliotheque.models.Livre;
import com.bibliotheque.models.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "microservice-back", url = "localhost:9000")
public interface ProxyBatchToBack {

    @GetMapping(value = "/livres-en-retard")
    List<Livre>listeDeLivreDontLesExemplairesSontEnRetard();

    @GetMapping(value = "/exemplaire-plus-de-deux-jours")
    List<Reservation> listeDeReservationDontLesDatesDeRetourSontSuperieurA48h();

    @GetMapping(value = "/liste-a-supprimer-plus-48h")
    List<Reservation> listeDeReservationASupprimer(List<Reservation> reservationList);

}

