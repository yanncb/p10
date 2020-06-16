package com.bibliotheque.web.controller;

import com.bibliotheque.web.beans.ExemplaireBean;
import com.bibliotheque.web.beans.LivreBean;
import com.bibliotheque.web.beans.Recherche;
import com.bibliotheque.web.proxies.MServiceBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RechercheController {

    @Autowired
    private MServiceBack rechercherLivres;

    @GetMapping("/recherche")
    public String rechercherUnLivre(Model model, @RequestParam(name = "motCle", defaultValue = "") String motCle){

        if (motCle.isEmpty()){
            List<LivreBean> livreBeanList = rechercherLivres.listeDesLivres();
            model.addAttribute("livreBeanList", livreBeanList);
        } else if (!motCle.isEmpty()){
            List<LivreBean> livreBeanList = rechercherLivres.rechercherLivres(motCle);
            model.addAttribute("livreBeanList", livreBeanList);
            model.addAttribute("motCle", motCle);
        }
        return "/recherche";
    }
}
