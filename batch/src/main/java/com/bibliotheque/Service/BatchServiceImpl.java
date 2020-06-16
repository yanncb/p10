package com.bibliotheque.Service;


import com.bibliotheque.models.Exemplaire;
import com.bibliotheque.models.Livre;
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

//    public List<Exemplaire> listExemplairesEnRetard() {
//        List<Livre> livres = listeDeLivreDontLesExemplairesSontEnRetard();
//        List<Exemplaire> ExemplairesEnRetard = new ArrayList<>();
//        for (Livre livre : livres) {
//            for (Exemplaire exemplaire : livre.getExemplaireList()) {
//                ExemplairesEnRetard.add(exemplaire);
//            }
//        }
//        return ExemplairesEnRetard;
//    }


    // creation sendmail dans service liste resa enretard et mail a tous els retardataire


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
