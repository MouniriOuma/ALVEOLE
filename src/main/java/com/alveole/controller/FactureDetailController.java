package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.Facture;
import com.alveole.model.FactureDetails;
import com.alveole.repository.FactureDetailRepository;
import com.alveole.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facture-details")
public class FactureDetailController {

    @Autowired
    private FactureDetailRepository factureDetailRepository;

    @GetMapping
    @Transactional
    public ResponseEntity<List<FactureDetails>> getAllFactureDetails() {
        try {
            List<FactureDetails> factureDetails = factureDetailRepository.findAll();
            return new ResponseEntity<>(factureDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<FactureDetails> getFactureDetailById(@PathVariable int id) {
        try {
            FactureDetails factureDetail = factureDetailRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Facture Detail not found with ID: " + id));
            return ResponseEntity.ok(factureDetail);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<FactureDetails> createFactureDetail(@RequestBody FactureDetails factureDetail) {
        try {
            FactureDetails createdFactureDetail = factureDetailRepository.save(factureDetail);
            return new ResponseEntity<>(createdFactureDetail, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FactureDetails> updateFactureDetail(@PathVariable int id,
                                                             @RequestBody FactureDetails factureDetailDetails) {
        try {
            FactureDetails factureDetail = factureDetailRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Facture Detail not found with ID: " + id));

            factureDetail.setProduit(factureDetailDetails.getProduit());
            factureDetail.setQuantiteCommande(factureDetailDetails.getQuantiteCommande());
            factureDetail.setPrixUnitaire(factureDetailDetails.getPrixUnitaire());


            FactureDetails updatedFactureDetail = factureDetailRepository.save(factureDetail);
            return ResponseEntity.ok(updatedFactureDetail);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteFactureDetail(@PathVariable int id) {
        try {
            boolean exists = factureDetailRepository.existsById(id);
            if (!exists) {
                return ResponseEntity.notFound().build();
            }

            factureDetailRepository.deleteById(id);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


