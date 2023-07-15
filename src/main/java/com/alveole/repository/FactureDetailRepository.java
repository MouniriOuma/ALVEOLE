package com.alveole.repository;

import com.alveole.model.FactureDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FactureDetailRepository extends JpaRepository<FactureDetails, Integer> {
}

