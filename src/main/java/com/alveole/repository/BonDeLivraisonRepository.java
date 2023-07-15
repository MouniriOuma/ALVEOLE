package com.alveole.repository;

import com.alveole.model.BonDeLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonDeLivraisonRepository extends JpaRepository<BonDeLivraison, Integer> {

}

