package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.Facture;
import com.alveole.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/factures")
public class FactureController {

    @Autowired
    private FactureRepository factureRepository;

    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {
        try {
            List<Facture> factures = factureRepository.findAll();
            return new ResponseEntity<>(factures, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable int id) {
        try {
            Facture facture = factureRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Facture not found with ID: " + id));
            return ResponseEntity.ok(facture);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Facture> createFacture(@RequestBody Facture facture) {
        try {
            Facture createdFacture = factureRepository.save(facture);
            return new ResponseEntity<>(createdFacture, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facture> updateFacture(@PathVariable int id, @RequestBody Facture factureDetails) {
        try {
            Facture facture = factureRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Facture not found with ID: " + id));

            facture.setNumeroFacture(factureDetails.getNumeroFacture());
            facture.setNumeroCommande(factureDetails.getNumeroCommande());
            facture.setDateFacture(factureDetails.getDateFacture());
            facture.setClient(factureDetails.getClient());
            facture.setTotalHT(factureDetails.getTotalHT());
            facture.setTotalTVA(factureDetails.getTotalTVA());
            facture.setTotalTTC(factureDetails.getTotalTTC());

            Facture updatedFacture = factureRepository.save(facture);
            return ResponseEntity.ok(updatedFacture);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteFacture(@PathVariable int id) {
        try {
            boolean exists = factureRepository.existsById(id);
            if (!exists) {
                return ResponseEntity.notFound().build();
            }

            factureRepository.deleteById(id);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
