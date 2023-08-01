package com.alveole.repository;

import com.alveole.model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Integer> {

    @Query("SELECT f FROM Facture f LEFT JOIN FETCH f.factureDetails fd WHERE f.id = ?1")
    Facture findByIdWithDetails(Integer factureId);

}

