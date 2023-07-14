package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.BonDeCommandeDetails;
import com.alveole.repository.BonDeCommandeDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/bon-de-commande-details")
public class BonDeCommandeDetailsController {

    @Autowired
    private BonDeCommandeDetailsRepository bonDeCommandeDetailsRepository;

    // Get all bon de commande details
    @GetMapping
    public ResponseEntity<List<BonDeCommandeDetails>> getAllBonDeCommandeDetails() {
        try {
            List<BonDeCommandeDetails> bonDeCommandeDetails = bonDeCommandeDetailsRepository.findAll();
            return new ResponseEntity<>(bonDeCommandeDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a bon de commande detail
    @PostMapping
    public ResponseEntity<BonDeCommandeDetails> createBonDeCommandeDetail(@RequestBody BonDeCommandeDetails bonDeCommandeDetails) {
        try {
            BonDeCommandeDetails createdBonDeCommandeDetail = bonDeCommandeDetailsRepository.save(bonDeCommandeDetails);
            return new ResponseEntity<>(createdBonDeCommandeDetail, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a bon de commande detail by ID
    @GetMapping("/{id}")
    public ResponseEntity<BonDeCommandeDetails> getBonDeCommandeDetailById(@PathVariable int id) {
        try {
            BonDeCommandeDetails bonDeCommandeDetail = bonDeCommandeDetailsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bon de commande detail not found with ID: " + id));
            return ResponseEntity.ok(bonDeCommandeDetail);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a bon de commande detail
    @PutMapping("/{id}")
    public ResponseEntity<BonDeCommandeDetails> updateBonDeCommandeDetail(@PathVariable int id, @RequestBody BonDeCommandeDetails bonDeCommandeDetails) {
        try {
            BonDeCommandeDetails existingBonDeCommandeDetail = bonDeCommandeDetailsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bon de commande detail not found with ID: " + id));

            existingBonDeCommandeDetail.setProduit(bonDeCommandeDetails.getProduit());
            existingBonDeCommandeDetail.setQuantite(bonDeCommandeDetails.getQuantite());
            existingBonDeCommandeDetail.setPrixUnitaire(bonDeCommandeDetails.getPrixUnitaire());
            existingBonDeCommandeDetail.setTotalHT(bonDeCommandeDetails.getTotalHT());

            BonDeCommandeDetails updatedBonDeCommandeDetail = bonDeCommandeDetailsRepository.save(existingBonDeCommandeDetail);
            return ResponseEntity.ok(updatedBonDeCommandeDetail);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a bon de commande detail
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBonDeCommandeDetail(@PathVariable int id) {
        try {
            boolean exists = bonDeCommandeDetailsRepository.existsById(id);
            if (!exists) {
                return ResponseEntity.notFound().build();
            }

            bonDeCommandeDetailsRepository.deleteById(id);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

