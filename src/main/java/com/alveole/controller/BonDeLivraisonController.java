package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.BonDeLivraison;
import com.alveole.repository.BonDeLivraisonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/bon-de-livraison")
public class BonDeLivraisonController {

    @Autowired
    private BonDeLivraisonRepository bonDeLivraisonRepository;


    // Get all bon de livraison
    @GetMapping
    public ResponseEntity<List<BonDeLivraison>> getAllBonDeLivraison() {
        try {
            List<BonDeLivraison> bonDeLivraisons = bonDeLivraisonRepository.findAll();
            return new ResponseEntity<>(bonDeLivraisons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a bon de livraison
    @PostMapping
    public ResponseEntity<BonDeLivraison> createBonDeLivraison(@RequestBody BonDeLivraison bonDeLivraison) {
        try {
            BonDeLivraison createdBonDeLivraison = bonDeLivraisonRepository.save(bonDeLivraison);
            return new ResponseEntity<>(createdBonDeLivraison, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a bon de livraison by ID
    @GetMapping("/{id}")
    public ResponseEntity<BonDeLivraison> getBonDeLivraisonById(@PathVariable Integer id) {
        try {
            BonDeLivraison bonDeLivraison = bonDeLivraisonRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bon de livraison not found with ID: " + id));
            return ResponseEntity.ok(bonDeLivraison);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a bon de livraison
    @PutMapping("/update/{id}")
    public ResponseEntity<BonDeLivraison> updateBonDeLivraison(@PathVariable Integer id, @RequestBody BonDeLivraison bonDeLivraisonDetails) {
        try {
            BonDeLivraison bonDeLivraison = bonDeLivraisonRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bon de livraison not found with ID: " + id));

            bonDeLivraison.setNumeroLivraison(bonDeLivraisonDetails.getNumeroLivraison());
            bonDeLivraison.setNumeroCommande(bonDeLivraisonDetails.getNumeroCommande());
            bonDeLivraison.setDateLivraison(bonDeLivraisonDetails.getDateLivraison());
            bonDeLivraison.setClient(bonDeLivraisonDetails.getClient());

            BonDeLivraison updatedBonDeLivraison = bonDeLivraisonRepository.save(bonDeLivraison);
            return ResponseEntity.ok(updatedBonDeLivraison);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a bon de livraison
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBonDeLivraison(@PathVariable Integer id) {
        try {
            boolean exists = bonDeLivraisonRepository.existsById(id);
            if (!exists) {
                return ResponseEntity.notFound().build();
            }

            bonDeLivraisonRepository.deleteById(id);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

