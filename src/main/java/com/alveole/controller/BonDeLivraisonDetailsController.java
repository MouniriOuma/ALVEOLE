package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.BonDeLivraisonDetails;
import com.alveole.repository.BonDeLivraisonDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/bon-de-livraison-details")
public class BonDeLivraisonDetailsController {

    @Autowired
    private BonDeLivraisonDetailsRepository bonDeLivraisonDetailsRepository;

    // Get all bon de livraison details
    @GetMapping
    public ResponseEntity<List<BonDeLivraisonDetails>> getAllBonDeLivraisonDetails() {
        try {
            List<BonDeLivraisonDetails> bonDeLivraisonDetailsList = bonDeLivraisonDetailsRepository.findAll();
            return new ResponseEntity<>(bonDeLivraisonDetailsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a bon de livraison details
    @PostMapping
    public ResponseEntity<BonDeLivraisonDetails> createBonDeLivraisonDetails(@RequestBody BonDeLivraisonDetails bonDeLivraisonDetails) {
        try {
            BonDeLivraisonDetails createdBonDeLivraisonDetails = bonDeLivraisonDetailsRepository.save(bonDeLivraisonDetails);
            return new ResponseEntity<>(createdBonDeLivraisonDetails, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a bon de livraison details by ID
    @GetMapping("/{id}")
    public ResponseEntity<BonDeLivraisonDetails> getBonDeLivraisonDetailsById(@PathVariable Integer id) {
        try {
            BonDeLivraisonDetails bonDeLivraisonDetails = bonDeLivraisonDetailsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bon de livraison details not found with ID: " + id));
            return ResponseEntity.ok(bonDeLivraisonDetails);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a bon de livraison details
    @PutMapping("/{id}")
    public ResponseEntity<BonDeLivraisonDetails> updateBonDeLivraisonDetails(@PathVariable Integer id, @RequestBody BonDeLivraisonDetails bonDeLivraisonDetails) {
        try {
            BonDeLivraisonDetails existingBonDeLivraisonDetails = bonDeLivraisonDetailsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bon de livraison details not found with ID: " + id));

            existingBonDeLivraisonDetails.setProduit(bonDeLivraisonDetails.getProduit());
            existingBonDeLivraisonDetails.setQuantiteCommande(bonDeLivraisonDetails.getQuantiteCommande());
            existingBonDeLivraisonDetails.setPrixUnitaire(bonDeLivraisonDetails.getPrixUnitaire());
            existingBonDeLivraisonDetails.setTotalHT(bonDeLivraisonDetails.getTotalHT());

            BonDeLivraisonDetails updatedBonDeLivraisonDetails = bonDeLivraisonDetailsRepository.save(existingBonDeLivraisonDetails);
            return ResponseEntity.ok(updatedBonDeLivraisonDetails);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a bon de livraison details
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBonDeLivraisonDetails(@PathVariable Integer id) {
        try {
            boolean exists = bonDeLivraisonDetailsRepository.existsById(id);
            if (!exists) {
                return ResponseEntity.notFound().build();
            }

            bonDeLivraisonDetailsRepository.deleteById(id);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

