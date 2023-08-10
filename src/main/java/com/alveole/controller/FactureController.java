package com.alveole.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alveole.PDFExporter.FacturePDFExporter;
import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.Facture;
import com.alveole.repository.FactureRepository;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
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

    @GetMapping("/WithDetails/{factureId}")
    public ResponseEntity<Facture> getFactureWithDetails(@PathVariable int factureId) {
        try {
            Facture facture = factureRepository.findByIdWithDetails(factureId);
            if (facture != null) {
                return new ResponseEntity<>(facture, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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
    public ResponseEntity<?> updateFacture(@PathVariable int id, @RequestBody Facture updatedFacture) {
        try {
            Facture facture = factureRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Facture not found with ID: " + id));

            facture.setNumeroFacture(updatedFacture.getNumeroFacture());
            facture.setNumeroCommande(updatedFacture.getNumeroCommande());
            facture.setNumeroLivraison(updatedFacture.getNumeroLivraison());
            facture.setDateFacture(updatedFacture.getDateFacture());
            facture.setClient(updatedFacture.getClient());
            facture.setTotalHT(updatedFacture.getTotalHT());
            facture.setTotalTVA(updatedFacture.getTotalTVA());
            facture.setTotalTTC(updatedFacture.getTotalTTC());

            // Update FactureDetails if needed
            if (updatedFacture.getFactureDetails() != null) {
                facture.getFactureDetails().clear();
                facture.getFactureDetails().addAll(updatedFacture.getFactureDetails());
            }

            Facture updatedFactureEntity = factureRepository.save(facture);
            return ResponseEntity.ok(updatedFactureEntity);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    //export facture to pdf
    @GetMapping("/export/pdf/{id}")
    public void exportToPDF(@PathVariable int id, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=facture_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Optional<Facture> factureOptional = factureRepository.findById(id);

        // Check if the facture with the given ID exists
        if (factureOptional.isPresent()) {
            Facture facture = factureOptional.get();
            FacturePDFExporter exporter = new FacturePDFExporter(facture);
            exporter.export(response);
        } else {
            // Facture with the given ID is not found
            // Send an error response with HTTP status code 404 (Not Found)
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Facture with ID " + id + " not found.");
        }
    }


}
