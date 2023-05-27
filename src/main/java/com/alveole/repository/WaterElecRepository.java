package com.alveole.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alveole.model.WaterElec;

public interface WaterElecRepository extends JpaRepository<WaterElec,  Integer> {

}
