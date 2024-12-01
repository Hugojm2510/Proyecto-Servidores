package com.vedruna.trabajofinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.trabajofinal.dto.CreateDeveloperDTO;
import com.vedruna.trabajofinal.dto.DeveloperDTO;
import com.vedruna.trabajofinal.dto.ProjectDTO;
import com.vedruna.trabajofinal.persistance.models.Developer;
import com.vedruna.trabajofinal.persistance.models.Project;
import com.vedruna.trabajofinal.persistance.repository.DeveloperRepositoryI;
import com.vedruna.trabajofinal.persistance.repository.ProjectRepositoryI;
import com.vedruna.trabajofinal.persistance.repository.TechnologiesRepository;
import com.vedruna.trabajofinal.services.DeveloperServiceI;

import jakarta.persistence.Column;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/developer")
@CrossOrigin
public class DeveloperController {
    
    @Autowired
    DeveloperServiceI devMngmnt;

    @Autowired
    ProjectRepositoryI proMngmnt;

    @Autowired
    DeveloperRepositoryI dRepo;

    // crear el dev
    @PostMapping("/create")
    public ResponseEntity<CreateDeveloperDTO> createDeveloper(@RequestBody CreateDeveloperDTO developer) {        
        try {            

            if (developer.getDevName() == null || developer.getDevName().isEmpty()) {
                throw new IllegalArgumentException("El nombre de la tecnología es obligatorio.");
            }

            // comprueba si el email del dev existe
            if (dRepo.existsByEmail(developer.getEmail())) {
                throw new IllegalArgumentException("El developer con el email '" + developer.getEmail() + "' ya existe.");
            }

            // comprueba si el linkedin del dev existe
            if (dRepo.existsByLinkedinUrl(developer.getLinkedinUrl())) {
                throw new IllegalArgumentException("El developer con el email '" + developer.getLinkedinUrl() + "' ya existe.");
            }

            // comprueba si el github del dev existe
            if (dRepo.existsByGithubUrl(developer.getGithubUrl())) {
                throw new IllegalArgumentException("El developer con el email '" + developer.getGithubUrl() + "' ya existe.");
            }

            Developer saveDeveloper = devMngmnt.saveDeveloper(developer);
            CreateDeveloperDTO createdeveloperDTO = new CreateDeveloperDTO(saveDeveloper);
            return ResponseEntity.ok(createdeveloperDTO);    
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
        /*
        la linea de Developer, guarda el nuevo developer en la bbdd
        y la siguiente linea coge la variable anterior que tiene todos los datos y las utiliza
        en el constructor de createDeveloperDTO
        lo que he hecho es que al crear un developer, me devuelva la estructura de CreateDeveloperDTO
        */
    }

    // borrar un dev
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Developer> deleteDeveloper(@PathVariable Integer id) {
        try {
            // comprueba si existe el id, como minimo el id es 1, si pone uno que sea cero o
            // inferior, salta la excepcion
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build(); // ID no válido
            }

            devMngmnt.deleteDeveloper(id);
            return ResponseEntity.noContent().build();                
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    // agregar un dev a un project
    @PutMapping("/worked/{projectId}/{devId}")
    public ResponseEntity<ProjectDTO> addDeveloperToProject(@PathVariable Integer projectId, @PathVariable Integer devId) {
        try {
            Project project = devMngmnt.addDeveloperToProject(projectId, devId);
            return ResponseEntity.ok(new ProjectDTO(project));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
