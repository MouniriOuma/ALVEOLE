package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.BonDeCommande;
import com.alveole.repository.BonDeCommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/bon-de-commandes")
public class BonDeCommandeController {

    @Autowired
    private BonDeCommandeRepository bonDeCommandeRepository;

    // Get all bon de commandes
    @GetMapping
    public ResponseEntity<List<BonDeCommande>> getAllBonDeCommandes() {
        try {
            List<BonDeCommande> bonDeCommandes = bonDeCommandeRepository.findAll();
            return new ResponseEntity<>(bonDeCommandes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a bon de commande
    @PostMapping
    public ResponseEntity<BonDeCommande> createBonDeCommande(@RequestBody BonDeCommande bonDeCommande) {
        try {
            BonDeCommande createdBonDeCommande = bonDeCommandeRepository.save(bonDeCommande);
            return new ResponseEntity<>(createdBonDeCommande, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a bon de commande by ID
    @GetMapping("/{id}")
    public ResponseEntity<BonDeCommande> getBonDeCommandeById(@PathVariable int id) {
        try {
            BonDeCommande bonDeCommande = bonDeCommandeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bon de commande not found with ID: " + id));
            return ResponseEntity.ok(bonDeCommande);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a bon de commande
    @PutMapping("/{id}")
    public ResponseEntity<BonDeCommande> updateBonDeCommande(@PathVariable int id, @RequestBody BonDeCommande bonDeCommandeDetails) {
        try {
            BonDeCommande bonDeCommande = bonDeCommandeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bon de commande not found with ID: " + id));

            bonDeCommande.setNumeroCommande(bonDeCommandeDetails.getNumeroCommande());
            bonDeCommande.setDateCommande(bonDeCommandeDetails.getDateCommande());
            bonDeCommande.setClient(bonDeCommandeDetails.getClient());
            bonDeCommande.setTotalHT(bonDeCommandeDetails.getTotalHT());

            BonDeCommande updatedBonDeCommande = bonDeCommandeRepository.save(bonDeCommande);
            return ResponseEntity.ok(updatedBonDeCommande);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a bon de commande
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBonDeCommande(@PathVariable int id) {
        try {
            boolean exists = bonDeCommandeRepository.existsById(id);
            if (!exists) {
                return ResponseEntity.notFound().build();
            }

            bonDeCommandeRepository.deleteById(id);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
