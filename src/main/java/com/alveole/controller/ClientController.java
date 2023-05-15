package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.Client;
import com.alveole.repository.ClientRepository;



@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
	
	
	 // Get all clients
	 @GetMapping("/clients")
	 public ResponseEntity<List<Client>> getAllClients() {
	     try {
	         List<Client> clients = clientRepository.findAll();
	         return new ResponseEntity<>(clients, HttpStatus.OK);
	     } catch (Exception e) {
	         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	     }
	 }
	
	 // Create a client
	 @PostMapping("/clients")
	 public ResponseEntity<Client> createClient(@RequestBody Client client) {
	     try {
	         Client createdClient = clientRepository.save(client);
	         return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
	     } catch (Exception e) {
	         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	     }
	 }

	// Get a client by ID
	 @GetMapping("/clients/{ice}")
	 public ResponseEntity<Client> getClientById(@PathVariable int ice) {
	     try {
	         Client client = clientRepository.findById(ice)
	                 .orElseThrow(() -> new ResourceNotFoundException("Client not found with ICE: " + ice));
	         return ResponseEntity.ok(client);
	     } catch (ResourceNotFoundException e) {
	         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	     } catch (Exception e) {
	         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	     }
	 }

	 // Update a client
	 @PutMapping("/clients/{ice}")
	 public ResponseEntity<Client> updateClient(@PathVariable int ice, @RequestBody Client clientDetails) {
	     try {
	         Client client = clientRepository.findById(ice)
	                 .orElseThrow(() -> new ResourceNotFoundException("Client not found with ICE: " + ice));

	         client.setRaisonSocial(clientDetails.getRaisonSocial());
	         client.setAdresse(clientDetails.getAdresse());
	         client.setContact(clientDetails.getContact());
	         client.setAdresseEmail(clientDetails.getAdresseEmail());

	         Client updatedClient = clientRepository.save(client);
	         return ResponseEntity.ok(updatedClient);
	     } catch (ResourceNotFoundException e) {
	         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	     } catch (Exception e) {
	         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	     }
	 }

	 // Delete a client
	 @DeleteMapping("/clients/{ice}")
	 public ResponseEntity<Map<String, Boolean>> deleteClient(@PathVariable int ice) {
	     try {
	         boolean exists = clientRepository.existsById(ice);
	         if (!exists) {
	             return ResponseEntity.notFound().build();
	         }
	         
	         clientRepository.deleteById(ice);
	         
	         Map<String, Boolean> response = new HashMap<>();
	         response.put("deleted", Boolean.TRUE);
	         return ResponseEntity.ok(response);
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	     }
	 }

}
