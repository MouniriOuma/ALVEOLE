package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.WaterElec;
import com.alveole.repository.WaterElecRepository;

@RestController
@RequestMapping("/waterElecs")

public class WaterElecController {
	private static final Logger logger = LoggerFactory.getLogger(WaterElecController.class);

	@Autowired
	private WaterElecRepository waterElecRepository;

	// Get all water or electricity bills
	@GetMapping
	public ResponseEntity<List<WaterElec>> getAllWaterElecs() {
		try {
			List<WaterElec> waterElecs = waterElecRepository.findAll();
			return new ResponseEntity<>(waterElecs, HttpStatus.OK);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.info("Error {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// create a WaterElec
	@PostMapping
	public ResponseEntity<WaterElec> createWaterElec(@RequestBody WaterElec waterElec) {
		try {
			WaterElec createdWaterElec = waterElecRepository.save(waterElec);
			return new ResponseEntity<>(createdWaterElec, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Get a WaterElec by ID
	@GetMapping("/{billNum}")
	public ResponseEntity<WaterElec> getWaterElecByNumber(@PathVariable int billNum) {
		try {
			WaterElec waterElec = waterElecRepository.findById(billNum)
					.orElseThrow(() -> new ResourceNotFoundException("Bill not found with bill number: " + billNum));
			return ResponseEntity.ok(waterElec);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// update a WaterElec
	@PutMapping("/{billNum}")
	public ResponseEntity<WaterElec> updateWaterElec(@PathVariable int billNum, @RequestBody WaterElec billDetails) {
		try {
			WaterElec waterElec = waterElecRepository.findById(billNum)
					.orElseThrow(() -> new ResourceNotFoundException("Bill not found with bill number: " + billNum));

			waterElec.setBill_Num(billDetails.getBill_Num());
			waterElec.setWater_elec(billDetails.getWater_elec());
			waterElec.setDate(billDetails.getDate());
			waterElec.setCost(billDetails.getCost());

			WaterElec updatedWaterElec = waterElecRepository.save(waterElec);
			return ResponseEntity.ok(updatedWaterElec);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// delete a waterElec
	@DeleteMapping("/{billNum}")
	public ResponseEntity<Map<String, Boolean>> deleteWaterElec(@PathVariable int billNum) {
		try {
			boolean exists = waterElecRepository.existsById(billNum);
			if (!exists) {
				return ResponseEntity.notFound().build();
			}

			waterElecRepository.deleteById(billNum);

			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}