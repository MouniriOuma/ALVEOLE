package com.alveole.repository;

import com.alveole.model.BonDeCommandeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BonDeCommandeDetailsRepository extends JpaRepository<BonDeCommandeDetails, Integer> {
}

