package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.Ingredient;
import com.alveole.repository.IngredientRepository;



@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;
    
    // Get all Ingredient
    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        try {
            List<Ingredient> ingredients = ingredientRepository.findAll();
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // create an ingredient
    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        try {
            Ingredient createdIngredient = ingredientRepository.save(ingredient);
            return new ResponseEntity<>(createdIngredient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get an ingredient by id
    @GetMapping("/{ingredientId}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable int ingredientId) {
        try {
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with ingredient ID: " + ingredientId));
            return ResponseEntity.ok(ingredient);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // update an ingredient
    @PutMapping("/{ingredientId}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable int ingredientId, @RequestBody Ingredient ingredientDetails) {
        try {
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with ingredient ID: " + ingredientId));

            ingredient.setName(ingredientDetails.getName());
            ingredient.setStockQuantity(ingredientDetails.getStockQuantity());
            ingredient.setUnitPrice(ingredientDetails.getUnitPrice());
            ingredient.setDescription(ingredientDetails.getDescription());
            ingredient.setSupplier(ingredientDetails.getSupplier());
            ingredient.setUnitOfMeasurement(ingredientDetails.getUnitOfMeasurement());
            ingredient.setMaxQuantity(ingredientDetails.getMaxQuantity());

            Ingredient updatedIngredient = ingredientRepository.save(ingredient);
            return ResponseEntity.ok(updatedIngredient);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // delete an ingredient
    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<Map<String, Boolean>> deleteIngredient(@PathVariable int ingredientId) {
        try {
            boolean exists = ingredientRepository.existsById(ingredientId);
            if (!exists) {
                return ResponseEntity.notFound().build();
            }

            ingredientRepository.deleteById(ingredientId);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
