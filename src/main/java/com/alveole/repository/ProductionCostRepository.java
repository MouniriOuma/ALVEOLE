package com.alveole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alveole.model.ProductionCost;

@Repository
public interface ProductionCostRepository extends JpaRepository<ProductionCost, Integer> {

}

