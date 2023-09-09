package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.ProductCost;
import com.alveole.repository.ProductCostRepository;

@RestController
@RequestMapping("/productCosts")
public class ProductCostController {

    @Autowired
    private ProductCostRepository productCostRepository;

    @GetMapping
    public ResponseEntity<List<ProductCost>> getAllProductCosts() {
        try {
            List<ProductCost> productCosts = productCostRepository.findAll();
            return new ResponseEntity<>(productCosts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<ProductCost> createProductCost(@RequestBody ProductCost productCost) {
        try {
            ProductCost createdProductCost = productCostRepository.save(productCost);
            return new ResponseEntity<>(createdProductCost, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PostMapping
    public ResponseEntity<ProductCost> createProductCost(@RequestBody ProductCost ProductCost) {
        try {
            // Get today's date
            LocalDate currentDate = LocalDate.now();

            // Check if a row exists with today's date
            Optional<ProductCost> existingProductCost = ProductCostRepository.findByDate(currentDate);

            if (existingProductCost.isPresent()) {
                // Row with today's date already exists, update the cost
                ProductCost existingCost = existingProductCost.get();
                existingCost.setCost(ProductCost.getCost());
                ProductCost updatedProductCost = ProductCostRepository.save(existingCost);
                return new ResponseEntity<>(updatedProductCost, HttpStatus.OK);
            } else {
                // Row with today's date does not exist, create a new row
                ProductCost createdProductCost = ProductCostRepository.save(ProductCost);
                return new ResponseEntity<>(createdProductCost, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/


    @GetMapping("/{id}")
    public ResponseEntity<ProductCost> getProductCostById(@PathVariable int id) {
        try {
            ProductCost productCost = productCostRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product Cost not found with ID: " + id));
            return ResponseEntity.ok(productCost);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProductCost(@PathVariable int id) {
        try {
            boolean exists = productCostRepository.existsById(id);
            if (!exists) {
                return ResponseEntity.notFound().build();
            }

            productCostRepository.deleteById(id);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

