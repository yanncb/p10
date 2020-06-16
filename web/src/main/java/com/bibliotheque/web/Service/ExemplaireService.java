package com.bibliotheque.web.Service;

import com.bibliotheque.web.beans.LivreBean;
import org.springframework.stereotype.Service;

@Service
public interface ExemplaireService {

    /**
     * Methode permettant de calculer le nombre d'exemplaire de livre disponible en fonction de ceux preté
     * @param livre liste d'exemplaire presente dans livre
     * @return nombre d'exemplaire disponible non prété
     */
    int calculNbDispo(LivreBean livre);
}
