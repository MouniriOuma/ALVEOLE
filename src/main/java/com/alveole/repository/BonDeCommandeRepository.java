package com.alveole.repository;

import com.alveole.model.BonDeCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonDeCommandeRepository extends JpaRepository<BonDeCommande, Integer> {
}
