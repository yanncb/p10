package com.bibliotheque.repository;

import com.bibliotheque.models.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {

    Exemplaire findById(int exemplaireId);


    List<Exemplaire> findByPretIsTrue();

}
