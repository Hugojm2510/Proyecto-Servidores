package com.vedruna.trabajofinal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vedruna.trabajofinal.dto.CreateProjectDTO;
import com.vedruna.trabajofinal.dto.ProjectDTO;
import com.vedruna.trabajofinal.persistance.models.Project;

public interface ProjectServiceI {
    
    // utilizamos Page para definir en que formato queremos ver los proyectos
    Page<ProjectDTO> getAll(Pageable pageable);

    /*
    este servicio se va a utilizar para buscar un proyecto por una palabra,
    - Vamos a poner una lista, aun que el projectName es unico, el comienzo
    del nombre puede ser igual:
    - InstaGaming
    - InstaPlay
    Una gran ventaja es que no vamos a tener que añadir Lower o Upper, como
    en una BBDD, ya que Spring Data Jpa, ya lo contiene para utilizarlo.
    */
    List<ProjectDTO> getProjectNameStartingWithIgnoreCase(String prefix);

    // servicio para crear el project
    Project createProject(CreateProjectDTO project);
    // servicio para guardar el project
    Project saveProject(CreateProjectDTO project);
    // servicio para guardar el project
    Project updateProject(Integer id, ProjectDTO project);
    // servicio para eliminar el project
    Project deleteProject(Integer id);
}
