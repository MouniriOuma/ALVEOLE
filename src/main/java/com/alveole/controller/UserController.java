package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.User;
import com.alveole.repository.UserRepository;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById((long) id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser( @RequestBody User user, @PathVariable int id) {
        Optional<User> userOptional = userRepository.findById((long) id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setRoles(user.getRoles());

            User updatedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById((long) id);
        if (userOptional.isPresent()) {
            userRepository.deleteById((long) id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

/*
@RestController
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = (List<User>) userRepository.findAll();
			logger.info("Users successfully retrieved {}", users.get(0));

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
			logger.info("Error {}", e.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userRepository.save(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        try {
            User user = userRepository.findById((long) userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User userDetails) {
        try {
            User user = userRepository.findById((long) userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setRoles(userDetails.getRoles());

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable int userId) {
        try {
            boolean exists = userRepository.existsById((long) userId);
            if (!exists) {
                return ResponseEntity.notFound().build();
            }

            userRepository.deleteById((long) userId);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
*/
