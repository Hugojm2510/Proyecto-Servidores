package com.vedruna.trabajofinal.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.vedruna.trabajofinal.dto.CreateProjectDTO;
import com.vedruna.trabajofinal.dto.ProjectDTO;
import com.vedruna.trabajofinal.persistance.models.Project;
import com.vedruna.trabajofinal.persistance.models.Status;
import com.vedruna.trabajofinal.persistance.repository.ProjectRepositoryI;
import com.vedruna.trabajofinal.persistance.repository.StatusRepositoryI;

@Service
public class ProjectServiceImpI implements ProjectServiceI{
    
    @Autowired
    ProjectRepositoryI pRepo;

    @Autowired
    StatusRepositoryI sRepo;

    // Obtener todos los proyectos
    @Override
    public Page<ProjectDTO> getAll(Pageable pageable) {
        try {

            // si los parametros del pageable son 0 o menores salta la excepcion
            if (pageable.getPageSize() <= 0 || pageable.getPageNumber() < 0) {
                throw new IllegalArgumentException("Los parametros no son validos");
            }
            Page<Project> projectPage = pRepo.findAll(pageable);

            // Si no se encuentran proyectos, se podría lanzar una excepción o devolver una página vacía
            if (projectPage.isEmpty()) {
                throw new EmptyResultDataAccessException("No se encontraron proyectos.", 1);
            }

            Page<ProjectDTO> projectDTOPage = projectPage.map(project -> new ProjectDTO(project));    
            return projectDTOPage;

        } catch (IllegalArgumentException e) {
            // Lanza una excepción si los parámetros de paginación son incorrectos
            throw new IllegalArgumentException(e.getMessage());
        } catch (DataAccessException e) {
            // Lanza una excepción si hay un problema de acceso a la base de datos
            throw new RuntimeException("Error al acceder a la base de datos para obtener los proyectos.", e);
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada
            throw new RuntimeException("Error inesperado al obtener los proyectos.", e);
        }
        /*
        en getAll, he utilizado un metodo de la interfaz Page, llamada ".map".
        Se utiliza para transformar cada elemento de la página aplicando una
        función dada, devolviendo una nueva página con los elementos transformados,
        pero manteniendo la información de paginación.

        - Itera sobre cada elemento de projectsPage (que es una Page<Project>).
        - Aplica la función lambda project -> new ProjectDTO(project) a cada Project,
          convirtiéndolo en un ProjectDTO.
        - Devuelve una nueva Page<ProjectDTO>, que contiene los DTO transformados y
          conserva los metadatos de paginación (como tamaño total, página actual, etc.).

        - Convierte cada Project de la página en un ProjectDTO y devuelve una nueva página
          con el mismo formato, pero con los elementos ya transformados.
        */
    }


    @Override
    public List<ProjectDTO> getProjectNameStartingWithIgnoreCase(String prefix) {
        try {
            // si la palabra es menor a 3 letras salta la excepcion
            if (prefix == null || prefix.length() < 3) {
                throw new IllegalArgumentException("La palabra debe contener al menos 3 letras.");
            }

            List<Project> projects = pRepo.findProjectByProjectNameStartingWithIgnoreCase(prefix);

            // si el project buscado esta vacio salta la excepcion
            if (projects.isEmpty()) {
                throw new IllegalArgumentException("No se encontraron proyectos que coincidan con la búsqueda.");
            }

            // Creamos la lista de los projects y añadimos los objetos de la lista project a la
            // lista projectDTO
            List<ProjectDTO> projectDTOs = new ArrayList<>();
            for (Project project : projects) {
                projectDTOs.add(new ProjectDTO(project));
            }

            return projectDTOs;

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            // Capturar cualquier otro error inesperado
            throw new RuntimeException("Error al obtener proyectos por nombre");
        }
    }
        /*
        aqui vamos a comprobar si existe el proyecto, ya que projectName es unico
        y no pueden haber dos con el mismo nombre, pero al buscar por una palabra
        si pueden haber dos proyectos que empiecen igual, pero terminen de distinta
        forma, tambien añadimos IgnoreCase para que no haya problemas con las mayusculas
        y con las minusculas.
        */


    @Override
    public Project createProject(CreateProjectDTO project) {
        try{
            // comprueba si algun project tiene el projectName
            if (pRepo.existsByProjectName(project.getProjectName())) {
                throw new IllegalArgumentException("El developer con el email ya existe");
            }

            // el repository interactua con la BBDD y solo interactua con la entidad Project,
            // entonces hacemos una conversion de ProjectDTO a Project 
            Project newProject = new Project();
            newProject.setProjectId(project.getProjectId());
            newProject.setProjectName(project.getProjectName());
            newProject.setDescription(project.getDescription());
            newProject.setStartDate(project.getStartDate());
            newProject.setEndDate(project.getEndDate());
            newProject.setRepositoryUrl(project.getRepositoryUrl());
            newProject.setDemoUrl(project.getDemoUrl());
            newProject.setPicture(project.getPicture());

            // aqui vamos a agregar el statusId
            Status status = sRepo.findById(project.getStatusId())
                .orElseThrow(() -> new IllegalArgumentException("Status no encontrado"));
            newProject.setStatus(status);
            
            // si existe manda un aviso
            List<Project> projectExistente = pRepo.findProjectByProjectName(project.getProjectName());
            if (!projectExistente.isEmpty()) {
                throw new IllegalArgumentException("El proyecto con nombre "+project.getProjectName()+" ya existe, no se puede crear");
            }

            // guardamos el project nuevo
            return pRepo.save(newProject);

        } catch (IllegalArgumentException e) {
            // Capturar el error en caso de no encontrar el proyecto
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            // Capturar cualquier otro error inesperado
            throw new RuntimeException("Ocurrio un error inesperado"); 
        }
    }

    @Override
    public Project saveProject(CreateProjectDTO project) {
        try {
            // comprueba si projectName esta vacio o es null
            if (project.getProjectName() == null || project.getProjectName().isEmpty()) {
                throw new IllegalArgumentException("El nombre es obligatorio.");
            }

            // hacemos la conversion
            Project projectSave = new Project();
            project.setProjectId(project.getProjectId());
            projectSave.setProjectName(project.getProjectName());
            projectSave.setDescription(project.getDescription());
            projectSave.setStartDate(project.getStartDate());
            projectSave.setEndDate(project.getEndDate());
            projectSave.setRepositoryUrl(project.getRepositoryUrl());
            projectSave.setDemoUrl(project.getDemoUrl());
            projectSave.setPicture(project.getPicture());

            // comprobar si existe el statusId insertado y si existe se guarda
            Status status = sRepo.findById(project.getStatusId())   
                .orElseThrow(() -> new IllegalArgumentException("Status no encontrado"));
            projectSave.setStatus(status);

            return pRepo.save(projectSave);

        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        } catch (RuntimeException e) {
            // Capturamos cualquier error inesperado 
            throw new RuntimeException("Ha ocurrido un problema");
        }
    }

    @Override
    public Project updateProject(Integer id, ProjectDTO project) {
        try {
            // comprueba si existe el proyecto con ese id y sino lanza una excepcion
            Project projectExistente = pRepo.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("Proyecto no encontrado con id "+id));

            // Validación para evitar modificar el status, developer o technology
            if (project.getStatus() != null) {
                throw new IllegalArgumentException("No se puede modificar el status del proyecto.");
            }
            if (project.getDevelopers() != null && !project.getDevelopers().isEmpty()) {
                throw new IllegalArgumentException("No se puede modificar los developers del proyecto.");
            }
            if (project.getTechnologies() != null && !project.getTechnologies().isEmpty()) {
                throw new IllegalArgumentException("No se puede modificar las tecnologías del proyecto.");
            }

            // hacemos la conversion
            projectExistente.setProjectId(project.getProjectId());
            projectExistente.setProjectName(project.getProjectName());
            projectExistente.setDescription(project.getDescription());
            projectExistente.setStartDate(project.getStartDate());
            projectExistente.setEndDate(project.getEndDate  ());
            projectExistente.setRepositoryUrl(project.getRepositoryUrl());
            projectExistente.setDemoUrl(project.getDemoUrl());
            projectExistente.setPicture(project.getPicture());

            return pRepo.save(projectExistente);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("proyecto no encontrado");
        } catch (RuntimeException e) {
            throw new RuntimeException("Ha ocurrido un error inesperado");
        }
    }


    @Override
    public Project deleteProject(Integer id) {
        try {
            // Comprobamos si el project existe, mediante el id
            Project project = pRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto con id " + id + " no encontrado"));
    
            // Si el proyecto existe, lo eliminamos
            pRepo.deleteById(id);
    
            // Retornamos el proyecto eliminado
            return project;
        } catch (IllegalArgumentException e) {
            // Si no se encuentra el proyecto, lanzamos una excepción
            throw new IllegalArgumentException("Proyecto con id " + id + " no encontrado");
        } catch (RuntimeException e) {
            throw new RuntimeException("Ha ocurrido un error inesperado");
        }
    }
}
