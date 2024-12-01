package com.vedruna.trabajofinal.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vedruna.trabajofinal.persistance.models.Status;

@Repository
public interface StatusRepositoryI extends JpaRepository<Status, Integer> {

}
