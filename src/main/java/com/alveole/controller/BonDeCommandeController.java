package com.alveole.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alveole.PDFExporter.BonDeCommandePDFExporter;
import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.BonDeCommande;
import com.alveole.repository.BonDeCommandeRepository;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> updateBonDeCommande(@PathVariable int id, @RequestBody BonDeCommande updatedBonDeCommande) {
        Optional<BonDeCommande> optionalBonDeCommande = bonDeCommandeRepository.findById(id);

        if (optionalBonDeCommande.isPresent()) {
            BonDeCommande existingBonDeCommande = optionalBonDeCommande.get();
            existingBonDeCommande.setNumeroCommande(updatedBonDeCommande.getNumeroCommande());
            existingBonDeCommande.setDateCommande(updatedBonDeCommande.getDateCommande());
            existingBonDeCommande.setClient(updatedBonDeCommande.getClient());
            existingBonDeCommande.setTotalHT(updatedBonDeCommande.getTotalHT());

            // Update BonDeCommandeDetails if needed
            if (updatedBonDeCommande.getBonDeCommandeDetails() != null) {
                existingBonDeCommande.getBonDeCommandeDetails().clear();
                existingBonDeCommande.getBonDeCommandeDetails().addAll(updatedBonDeCommande.getBonDeCommandeDetails());
            }

            bonDeCommandeRepository.save(existingBonDeCommande);
            return ResponseEntity.ok("Bon de commande updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
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

    //export bon de commande to pdf
    @GetMapping("/export/pdf/{id}")
    public void exportBonDeCommandeToPDF(@PathVariable int id, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=bondecommande_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Optional<BonDeCommande> bonDeCommandeOptional = bonDeCommandeRepository.findById(id);

        // Check if the bon de commande with the given ID exists
        if (bonDeCommandeOptional.isPresent()) {
            BonDeCommande bonDeCommande = bonDeCommandeOptional.get();
            BonDeCommandePDFExporter exporter = new BonDeCommandePDFExporter(bonDeCommande);
            exporter.export(response);
        } else {
            // Bon de commande with the given ID is not found
            // Send an error response with HTTP status code 404 (Not Found)
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bon de commande with ID " + id + " not found.");
        }
    }


}
