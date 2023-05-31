package com.alveole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alveole.model.User;

@Repository
public interface UserRepository extends CrudRepository<com.alveole.model.User, Integer> {
    com.alveole.model.User findByUsername(String username);
}