package com.alveole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Set;

import com.alveole.model.ERole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.alveole.exception.ResourceNotFoundException;
import com.alveole.model.User;
import com.alveole.repository.UserRepository;
import com.alveole.model.Role;



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

    @GetMapping("role/{username}")
    public ResponseEntity<?> getUserRoleByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Set<Role> roles = user.getRoles();

        if (roles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<String> roleNames = roles.stream()
                .map(role -> role.getName().toString()) // Convert ERole to String
                .collect(Collectors.toList());

        return ResponseEntity.ok(roleNames);
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


