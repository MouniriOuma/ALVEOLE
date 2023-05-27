package com.alveole.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alveole.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient,  Integer> {

}
