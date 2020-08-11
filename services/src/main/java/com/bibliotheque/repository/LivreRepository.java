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

    Livre findById(int livreId);

//    @Query("select l from Livre where l.id= :livreId and l.reservationList.utilisateur.id= :utilisateurId")
//    Livre findByIdAndReserservationListUtilisateurId(int livreId, int utilisateurId);

    @Query("select l from Livre l where l.auteur like :motCle or l.titre like :motCle")
    List<Livre> rechercherParAuteurOuTitre(@Param("motCle") String motCle);

    @Query("select distinct l from Livre l  join fetch l.exemplaireList e join fetch Utilisateur u on u = e.utilisateur WHERE u.id = :id")
    List<Livre> rechercherTousLesLivresPourUtilisateur(@Param("id") int id);

    @Query("select distinct l from Livre l  join fetch l.exemplaireList e join fetch Utilisateur u on u = e.utilisateur WHERE e.pret = true")
    List<Livre> rechercherLivreDontExemplaireEnRetard();

    @Query("select distinct l from Livre l join fetch l.reservationList r join fetch r.utilisateur u WHERE u.id = :id")
    List<Livre> rechercherTousLesLivresReserveParUtilisateur(@Param("id") int id);

    Livre findByExemplaireList(Exemplaire exemplaire);

    @Query("select l from Livre l join fetch l.reservationList r where  r.id= :reservationId")
    Livre findByReservationId(@Param("reservationId") int reservationId);

}
