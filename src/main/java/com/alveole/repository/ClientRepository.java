package com.alveole.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alveole.model.Client;

public interface ClientRepository extends JpaRepository<Client,  Integer> {

}
