package com.bibliotheque.repository;

import com.bibliotheque.models.Exemplaire;
import com.bibliotheque.models.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {

    @Query("select l from Livre l where l.auteur like :motCle or l.titre like :motCle")
    List<Livre> rechercherParAuteurOuTitre(@Param("motCle") String motCle);

    @Query("select distinct l from Livre l  join fetch l.exemplaireList e join fetch Utilisateur u on u = e.utilisateur WHERE u.id = :id")
    List<Livre> rechercherTousLesLivresPourUtilisateur(@Param("id") int id);

    @Query("select distinct l from Livre l  join fetch l.exemplaireList e join fetch Utilisateur u on u = e.utilisateur WHERE e.pret = true")
    List<Livre> rechercherLivreDontExemplaireEnRetard();
}
