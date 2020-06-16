package com.bibliotheque.Service;

import com.bibliotheque.models.Exemplaire;
import com.bibliotheque.models.Livre;
import com.bibliotheque.models.Utilisateur;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BatchService {

    /**
     * demande au back la liste de livre dont les exemplaires sont en retard
     * @return liste de livre
     */
    List<Livre> listeDeLivreDontLesExemplairesSontEnRetard();


//    List<Exemplaire> listExemplairesEnRetard();


    /**
     * liste d'utilisateur possedant des exemplaires qui n'ont pas été rendue dans les délais
     * @return
     */
    List<Utilisateur> listUtilisateurEnRetard();

    /**
     * methode d'envoie de mail
     * @return null
     */
    String sendMaill();
}
