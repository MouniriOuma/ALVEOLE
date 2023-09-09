package com.alveole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alveole.model.ProductCost;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProductCostRepository extends JpaRepository<ProductCost, Integer> {

}


