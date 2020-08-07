package com.bibliotheque.Service;

import com.bibliotheque.models.Exemplaire;
import com.bibliotheque.models.Livre;
import com.bibliotheque.models.Reservation;
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

//    je veux supprimer une reservation d'un' utilisateur ayant reservé un livre des qu'il peut recuperer un exemplaire en bibli depuis plus de 48h'

    /**
     * Demande la liste de reservation dont un exemplaire est dispo, si une reservation est disponible depuis deux jours on supprime la reservation pour le premier utilisateur
     * il faut voir si dans la liste de resa un exemplaire d'un livre reservé est dispo
     * @param utilisateurId
     * @param livreId
     * @return
     */
    List<Reservation>supprimerReservationQuandLivreDispoDepuisPlusDe48H(int utilisateurId,int livreId);


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

    /**
     * Méthode d'envoie de mail
     * @param utilisateur l'utilisateur etant le premier de la liste d'attente
     * @return rien
     */
    String envoieDeNotificationParMailALutilisateurAyantReserveUnLivreEtCeTrouvantPremierDeLaListe(Utilisateur utilisateur);


}
