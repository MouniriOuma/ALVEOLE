package com.alveole.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alveole.model.Bill;

public interface BillRepository extends JpaRepository<Bill,  Integer> {

}
