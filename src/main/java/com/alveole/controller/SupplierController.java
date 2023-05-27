package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.Supplier;
import com.alveole.repository.SupplierRepository;


@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    // get all suppliers
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        try {
            List<Supplier> suppliers = supplierRepository.findAll();
            return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create a supplier
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        try {
            Supplier createdSupplier = supplierRepository.save(supplier);
            return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get supplier by id 
    @GetMapping("/{supplierId}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable int supplierId) {
        try {
            Supplier supplier = supplierRepository.findById(supplierId)
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with supplier ID: " + supplierId));
            return ResponseEntity.ok(supplier);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update a supplier 
    @PutMapping("/{supplierId}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable int supplierId, @RequestBody Supplier supplierDetails) {
        try {
            Supplier supplier = supplierRepository.findById(supplierId)
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with supplier ID: " + supplierId));

            supplier.setAddress(supplierDetails.getAddress());
            supplier.setContact(supplierDetails.getContact());
            supplier.setEmail(supplierDetails.getEmail());
            supplier.setSuppliedProduct(supplierDetails.getSuppliedProduct());
            supplier.setCin(supplierDetails.getCin());
            supplier.setFirstName(supplierDetails.getFirstName());
            supplier.setLastName(supplierDetails.getLastName());
            supplier.setIce(supplierDetails.getIce());
            supplier.setBusinessName(supplierDetails.getBusinessName());

            Supplier updatedSupplier = supplierRepository.save(supplier);
            return ResponseEntity.ok(updatedSupplier);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //delete a supplier 
    @DeleteMapping("/{supplierId}")
    public ResponseEntity<Map<String, Boolean>> deleteClient(@PathVariable int supplierId) {
	     try {
	         boolean exists = supplierRepository.existsById(supplierId);
	         if (!exists) {
	             return ResponseEntity.notFound().build();
	         }
	         
	         supplierRepository.deleteById(supplierId);
	         
	         Map<String, Boolean> response = new HashMap<>();
	         response.put("deleted", Boolean.TRUE);
	         return ResponseEntity.ok(response);
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	     }
	 }

}

