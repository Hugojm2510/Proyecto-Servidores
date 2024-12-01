package com.vedruna.trabajofinal.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedruna.trabajofinal.persistance.models.Technologies;

@Repository
public interface TechnologiesRepository extends JpaRepository<Technologies, Integer>{
    boolean existsByTechName(String techName);
}
