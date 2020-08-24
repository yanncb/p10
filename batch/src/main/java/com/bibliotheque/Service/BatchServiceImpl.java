package com.bibliotheque.Service;


import com.bibliotheque.models.Exemplaire;
import com.bibliotheque.models.Livre;
import com.bibliotheque.models.Reservation;
import com.bibliotheque.models.Utilisateur;
import com.bibliotheque.proxies.ProxyBatchToBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    ProxyBatchToBack proxyBatchToBack;

    @Autowired
    public JavaMailSender emailSender;


    @Override
    public List<Livre> listeDeLivreDontLesExemplairesSontEnRetard() {
        List<Livre> livresEnRetard = proxyBatchToBack.listeDeLivreDontLesExemplairesSontEnRetard();
        return livresEnRetard;
    }

    @Override
    public void listeDesReservationAyantUnExemplaireDispoDepuisPlusDe48h() {
         proxyBatchToBack.listeDeReservationDontLesDatesDeRetourSontSuperieurA48h();

    }


    public List<Utilisateur> listUtilisateurEnRetard() {
        List<Livre> livres = listeDeLivreDontLesExemplairesSontEnRetard();
        List<Utilisateur> utilisateursEnRetard = new ArrayList<>();
        for (Livre livre : livres) {
            for (Exemplaire exemplaire : livre.getExemplaireList()) {
                utilisateursEnRetard.add(exemplaire.getUtilisateur());
            }
        }
        return utilisateursEnRetard;
    }

    @ResponseBody
    @RequestMapping("/sendSimpleEmail")
    public String sendMaill() {
        for (Utilisateur utilisateur : listUtilisateurEnRetard()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(utilisateur.getMail());

            for (Livre livre : listeDeLivreDontLesExemplairesSontEnRetard()) {
                message.setSubject("Bonjour, c'est votre bibliotheque qui vous rappel qui vous devez nous retourner un ou plusieurs livre.");
                message.setText("Vous devez nous retourner votre exemplaire" + livre.getTitre() + " du livre emprunté à la bibliotheque votre temps d'emprunt est dépassée");
            }
            // Send Message!
            emailSender.send(message);
        }

        return "Email Sent!";

    }




}
//
//"quand un exemplaire revient j'informe lors de la saisie du retour le premiere la liste de reservation et je set une date.
//            lors du batch je recherche les reservations dont la date d'envoie de mail sont superieur à 48h et je les supprime.
//            Je vérifie si un nouveau 1er de la liste existe et je lui envoie un mail."
//Si tu es premier de la liste et que tu annule ta resa il faut informer tout de suite le nouveau premier ancien second.
//
//
//si le premier de la liste à deja une date c'est pas un nouveau.