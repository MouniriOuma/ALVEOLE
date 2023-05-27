package com.alveole.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alveole.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier,  Integer> {

}
