package com.vedruna.trabajofinal.persistance.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedruna.trabajofinal.dto.ProjectDTO;
import com.vedruna.trabajofinal.persistance.models.Project;

@Repository
public interface ProjectRepositoryI extends JpaRepository<Project, Integer>{
    
    Page<Project> findAll(Pageable pageable);
    List<Project> findProjectByProjectName(String projecName);
    List<Project> findProjectByProjectNameStartingWithIgnoreCase(String prefix);
    
    // utilizado para validaciones
    Boolean existsByProjectName(String projectName);
}
