package com.bibliotheque.proxies;

import com.bibliotheque.models.Livre;
import com.bibliotheque.models.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservice-back", url = "localhost:9000")
public interface ProxyBatchToBack {

    @GetMapping(value = "/livres-en-retard")
    List<Livre> listeDeLivreDontLesExemplairesSontEnRetard();

    @GetMapping(value = "/exemplaire-plus-de-deux-jours")
    void listeDeReservationDontLesDatesDeRetourSontSuperieurA48h();
//
//    @PostMapping(value = "/liste-a-supprimer-plus-48h")
//    List<Reservation> listeDeReservationASupprimer(@RequestBody List<Reservation> reservationList);

}

