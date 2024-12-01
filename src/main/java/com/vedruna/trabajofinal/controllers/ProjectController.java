package com.vedruna.trabajofinal.controllers;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vedruna.trabajofinal.dto.CreateProjectDTO;
import com.vedruna.trabajofinal.dto.ProjectDTO;
import com.vedruna.trabajofinal.persistance.models.Project;
import com.vedruna.trabajofinal.persistance.repository.ProjectRepositoryI;
import com.vedruna.trabajofinal.services.ProjectServiceI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/project")
@CrossOrigin
public class ProjectController {
    
    @Autowired
    private ProjectServiceI proMngmnt;

    @Autowired
    private ProjectRepositoryI pRepo;

    // Obtener todos los projects
    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> getAllProjects(Pageable pageable) {
        try {
            Page<ProjectDTO> projects = proMngmnt.getAll(pageable);

            // Comprueba si el project esta vacio
            if (projects.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // Comprueba si los parametros del Page son correctos
            if (pageable.getPageNumber() < 0 || pageable.getPageSize() <= 0) {
                throw new IllegalArgumentException("Los parámetros de paginación no son válidos.");
            }

            return ResponseEntity.ok(proMngmnt.getAll(pageable));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Obtener el project por la palabra
    @GetMapping("/word/{projectName}")
    public ResponseEntity<List<ProjectDTO>> getProjectByPrefix(@PathVariable String projectName) {
        try {

            // validar el projectName
            if (projectName == null || projectName.length() < 3) {
                throw new IllegalArgumentException("El nombre del proyecto debe tener al menos 3 caracteres.");
            }

            // comprueba si se ha insertado un prefijo
            if (projectName == null || projectName.trim().isEmpty()) {
                throw new IllegalArgumentException("El prefijo no puede estar vacío.");
            }

            // comprueba si el projectName es menor de 3 letras
            if (projectName.length() < 3) {
                throw new IllegalArgumentException("El prefijo debe tener al menos 3 caracteres.");
            }

            return ResponseEntity.ok(proMngmnt.getProjectNameStartingWithIgnoreCase(projectName));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Crear el project
    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody CreateProjectDTO project) {   
        try {
            if (pRepo.existsByProjectName(project.getProjectName())) {
                throw new IllegalArgumentException("El projet con el nombre2 ya existe");
            }

            // Validar que la fecha de inicio no sea anterior a hoy
            if (project.getStartDate() != null && project.getStartDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("La fecha de inicio no puede ser anterior al día actual.");
            }

            // validar que la fecha de inicio no sea posterior a la fecha de finalizacion
            if (project.getEndDate() != null && project.getStartDate().isAfter(project.getEndDate())) {
                throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de finalización.");
            }
            
            return ResponseEntity.ok(proMngmnt.saveProject(project));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Actualizar el project
    @PutMapping("/update/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer id, @RequestBody ProjectDTO project) {
        try {
     
            // Verificar si el id es válido (puedes hacer más verificaciones específicas)
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body(null); // ID no válido
            }

            Project projectModificado = proMngmnt.updateProject(id, project);
            return ResponseEntity.ok(projectModificado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    /*
    - En este controller si trabajas con Optional en los servicios (updateProject) puedes utilzar el .map,
    - E-n caso de no trabajar con Optional en los servicios (updateProject), te saldra que .map no es compatible
      y no podras utilizarlo.
    - También depende de que quieras hacer si utilizas Optional, utiliza metodos como .map, .orElse. Lo fundamental,
      es que con Optional es mas enrevesado y sin utilizarlo es mas sencillo y directo, pero vas a tener que utilizar
      mas excepciones.   
    */

    // Eliminar el project
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Integer id) {
        try {
            // comprueba que exista el project con el id
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build(); // ID no válido
            }

            proMngmnt.deleteProject(id);
            return ResponseEntity.noContent().build();
            /*
            Indica que la solicitud se procesó correctamente y el recurso se eliminó,
            pero no hay contenido que devolver en el cuerpo de la respuesta.
            .noContent()
            - devuelve el "estado HTTP 204 No Content".
            - indica que la operacion fue exitosa 
            */
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }   
}
