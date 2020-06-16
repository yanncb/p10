package com.bibliotheque.web.Service;

import com.bibliotheque.web.beans.UtilisateurBean;
import com.bibliotheque.web.proxies.MServiceBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private MServiceBack mServiceBack;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String numCarte) throws UsernameNotFoundException, DataAccessException {
        UtilisateurBean utilisateur = mServiceBack.connexionUtilisateur(numCarte.toLowerCase());
        if (utilisateur == null) {
            throw new UsernameNotFoundException(numCarte + " non trouvé");
        }
        return utilisateur;
    }

    public void ajouterUtilisateur(UtilisateurBean utilisateur) {
        String password = utilisateur.getMotDePasse();
        String encryptedPassword = passwordEncoder.encode(password);
        utilisateur.setMotDePasse(encryptedPassword);
        mServiceBack.ajoutUtilisateur(utilisateur);
    }
}
