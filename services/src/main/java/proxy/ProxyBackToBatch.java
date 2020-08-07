package proxy;

import com.bibliotheque.models.Utilisateur;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservice-batch", url = "localhost:9000")
public interface ProxyBackToBatch {

    @GetMapping(value = "/envoie-mail-premier-de-la-liste/{utilisateurId}")
    Utilisateur envoieDeMailAUtilisateurAyantReserveLivreEtEtantPremierDeLaListeDattente(int utilisateurId);


}
