package com.alveole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alveole.model.ProductionCost;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProductionCostRepository extends JpaRepository<ProductionCost, Integer> {
    Optional<ProductionCost> findByDate(LocalDate date);
}


