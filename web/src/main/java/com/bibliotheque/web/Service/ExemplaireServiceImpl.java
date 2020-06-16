package com.bibliotheque.web.Service;


import com.bibliotheque.web.beans.ExemplaireBean;
import com.bibliotheque.web.beans.LivreBean;
import org.springframework.stereotype.Service;

@Service
public class ExemplaireServiceImpl implements ExemplaireService {


    @Override
    public int calculNbDispo(LivreBean livre) {
        int nbExemplaires = 0;
        for (ExemplaireBean exemplaire :  livre.getExemplaireList()) {
            if (!exemplaire.isPret()){
                nbExemplaires++;
            }
        }
        return nbExemplaires;
    }
}
