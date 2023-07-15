package com.alveole.repository;

import com.alveole.model.BonDeLivraisonDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonDeLivraisonDetailsRepository extends JpaRepository<BonDeLivraisonDetails, Integer> {

}

