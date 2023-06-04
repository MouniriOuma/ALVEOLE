package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.ProductionCost;
import com.alveole.repository.ProductionCostRepository;

@RestController
@RequestMapping("/productionCosts")
public class ProductionCostController {

    @Autowired
    private ProductionCostRepository productionCostRepository;

    @GetMapping
    public ResponseEntity<List<ProductionCost>> getAllProductionCosts() {
        try {
            List<ProductionCost> productionCosts = productionCostRepository.findAll();
            return new ResponseEntity<>(productionCosts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ProductionCost> createProductionCost(@RequestBody ProductionCost productionCost) {
        try {
            ProductionCost createdProductionCost = productionCostRepository.save(productionCost);
            return new ResponseEntity<>(createdProductionCost, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductionCost> getProductionCostById(@PathVariable int id) {
        try {
            ProductionCost productionCost = productionCostRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Production Cost not found with ID: " + id));
            return ResponseEntity.ok(productionCost);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductionCost> updateProductionCost(@PathVariable int id,
                                                               @RequestBody ProductionCost productionCostDetails) {
        try {
            ProductionCost productionCost = productionCostRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Production Cost not found with ID: " + id));

            productionCost.setDate(productionCostDetails.getDate());
            productionCost.setCost(productionCostDetails.getCost());

            ProductionCost updatedProductionCost = productionCostRepository.save(productionCost);
            return ResponseEntity.ok(updatedProductionCost);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProductionCost(@PathVariable int id) {
        try {
            boolean exists = productionCostRepository.existsById(id);
            if (!exists) {
                return ResponseEntity.notFound().build();
            }

            productionCostRepository.deleteById(id);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

