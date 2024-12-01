package com.vedruna.trabajofinal.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedruna.trabajofinal.persistance.models.Developer;

@Repository
public interface DeveloperRepositoryI extends JpaRepository<Developer, Integer>{
    boolean existsByEmail(String email);
    boolean existsByLinkedinUrl(String linkedinUrl);
    boolean existsByGithubUrl(String githubUrl);
}
