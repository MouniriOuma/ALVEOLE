package com.alveole.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alveole.model.Product;

public interface ProductRepository extends JpaRepository<Product,  Integer> {

}
