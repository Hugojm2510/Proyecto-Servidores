package com.vedruna.trabajofinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vedruna.trabajofinal.dto.ProjectDTO;
import com.vedruna.trabajofinal.dto.TechnologiesDTO;
import com.vedruna.trabajofinal.persistance.models.Project;
import com.vedruna.trabajofinal.persistance.models.Technologies;
import com.vedruna.trabajofinal.persistance.repository.ProjectRepositoryI;
import com.vedruna.trabajofinal.persistance.repository.TechnologiesRepository;
import com.vedruna.trabajofinal.services.ProjectServiceI;
import com.vedruna.trabajofinal.services.TechnologiesServiceI;

@RestController
@RequestMapping("api/v1/technologie")
@CrossOrigin
public class TechnologiesController {
    
    @Autowired
    TechnologiesServiceI techMngmnt;

    @Autowired
    TechnologiesRepository tRepo;

    @Autowired
    ProjectRepositoryI pRepo;

    @Autowired
    ProjectServiceI proMngmnt;


    @PostMapping("/create")
    public ResponseEntity<TechnologiesDTO> createTechnologie(@RequestBody TechnologiesDTO technologies) {        
        try {
            // Validación de datos
            if (technologies.getTechName() == null || technologies.getTechName().isEmpty()) {
                throw new IllegalArgumentException("El nombre de la tecnología es obligatorio.");
            }

            // comprueba si el nombre del tech existe
            if (tRepo.existsByTechName(technologies.getTechName())) {
                throw new IllegalArgumentException("La tecnología con el nombre '" + technologies.getTechName() + "' ya existe.");
            }

            Technologies saveTechnologies = techMngmnt.saveTechnologies(technologies);
            TechnologiesDTO techdto = new TechnologiesDTO(saveTechnologies);

            return ResponseEntity.ok(techdto);
            
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error inesperado");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Technologies> deleteTechnologie(@PathVariable Integer id) {
        try {
            // comprueba si existe el id
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build(); // ID no válido
            }

            techMngmnt.deleteTechnologies(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
                // 404 Not Found

        } catch (Exception e) {
            // Capturamos cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(null);
            // 500 Internal Server Error

        }
    }


    
    @PutMapping("/used/{projectId}/{techId}")
    public ResponseEntity<ProjectDTO> addTechnToProject(@PathVariable Integer projectId, @PathVariable Integer techId) {
        try {
            Project project = techMngmnt.addTechToProject(projectId, techId);
            return ResponseEntity.ok(new ProjectDTO(project));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
