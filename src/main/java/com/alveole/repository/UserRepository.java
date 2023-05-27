package com.alveole.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alveole.model.User;

public interface UserRepository extends JpaRepository<User,  Integer> {

}
