package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.Bill;
import com.alveole.repository.BillRepository;

@RestController
@RequestMapping("/bills")
@Secured("ROLE_ADMIN")
public class BillController {
	private static final Logger logger = LoggerFactory.getLogger(BillController.class);

	@Autowired
	private BillRepository billRepository;

	// Get all bills
	@GetMapping
	public ResponseEntity<List<Bill>> getAllBills() {
		try {
			List<Bill> bills = billRepository.findAll();
			// System.out.print("Retrieved bills are "+bills.get(0).getAmount());
			logger.info("Bills successfully retrieved {}", bills.get(0));
			return new ResponseEntity<>(bills, HttpStatus.OK);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.info("Error {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// create a bill
	@PostMapping
	public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
		try {
			Bill createdBill = billRepository.save(bill);
			return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get a bill by ID
	@GetMapping("/{billNumber}")
	public ResponseEntity<Bill> getBillByNumber(@PathVariable int billNumber) {
		try {
			Bill bill = billRepository.findById(billNumber)
					.orElseThrow(() -> new ResourceNotFoundException("Bill not found with bill number: " + billNumber));
			return ResponseEntity.ok(bill);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// update a bill
	@PutMapping("/{billNumber}")
	public ResponseEntity<Bill> updateBill(@PathVariable int billNumber, @RequestBody Bill billDetails) {
		try {
			Bill bill = billRepository.findById(billNumber)
					.orElseThrow(() -> new ResourceNotFoundException("Bill not found with bill number: " + billNumber));

			bill.setSupplier(billDetails.getSupplier());
			bill.setDate(billDetails.getDate());
			bill.setAmount(billDetails.getAmount());
			bill.setStatus(billDetails.getStatus());

			Bill updatedBill = billRepository.save(bill);
			return ResponseEntity.ok(updatedBill);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// delete a bill
	@DeleteMapping("/{billNumber}")
	public ResponseEntity<Map<String, Boolean>> deleteBill(@PathVariable int billNumber) {
		try {
			boolean exists = billRepository.existsById(billNumber);
			if (!exists) {
				return ResponseEntity.notFound().build();
			}

			billRepository.deleteById(billNumber);

			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
