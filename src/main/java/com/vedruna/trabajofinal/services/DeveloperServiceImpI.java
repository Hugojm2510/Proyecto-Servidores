package com.vedruna.trabajofinal.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vedruna.trabajofinal.dto.CreateDeveloperDTO;
import com.vedruna.trabajofinal.persistance.models.Developer;
import com.vedruna.trabajofinal.persistance.models.Project;
import com.vedruna.trabajofinal.persistance.repository.DeveloperRepositoryI;
import com.vedruna.trabajofinal.persistance.repository.ProjectRepositoryI;

@Service
public class DeveloperServiceImpI implements DeveloperServiceI{

    @Autowired
    DeveloperRepositoryI dRepo;

    @Autowired
    ProjectRepositoryI pRepo;

    @Override
    public Developer createDeveloper(CreateDeveloperDTO developer) {
        try {
            // comprueba si algun dev tiene el email
            if (dRepo.existsByEmail(developer.getEmail())) {
                throw new IllegalArgumentException("El developer con el email ya existe");
            }

            // comprueba si algun dev tiene el linkedin
            if (dRepo.existsByLinkedinUrl(developer.getLinkedinUrl())) {
                throw new IllegalArgumentException("El developer con el linkedin ya existe");
            }

            // comprueba si algun dev tiene el github
            if (dRepo.existsByGithubUrl(developer.getGithubUrl())) {
                throw new IllegalArgumentException("El developer con el github ya existe");
            }

            // Hacemos el metodo de conversion
            Developer newDeveloper = new Developer();
            newDeveloper.setDevId(developer.getDevId());
            newDeveloper.setDevName(developer.getDevName());
            newDeveloper.setDevSurname(developer.getDevSurname());
            newDeveloper.setEmail(developer.getEmail());
            newDeveloper.setLinkedinUrl(developer.getLinkedinUrl());
            newDeveloper.setGithubUrl(developer.getGithubUrl());

            return dRepo.save(newDeveloper);
            
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            // Capturar cualquier otra excepcion inesperada
            throw new RuntimeException("Ocurrio un error inesperado");
        }
    }

    @Override
    public Developer saveDeveloper(CreateDeveloperDTO developer) {
        try {
            // comprueba que el email no este vacio
            if (developer.getEmail() == null || developer.getEmail().isEmpty()) {
                throw new IllegalArgumentException("El email es obligatorio.");
            }
    
            // comprueba que el nombre no este vacio
            if (developer.getDevName() == null || developer.getDevName().isEmpty()) {
                throw new IllegalArgumentException("El nombre del developer es obligatorio.");
            }
    
            // Validar formato del email
            if (!developer.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new IllegalArgumentException("El email tiene un formato inválido.");
            }

            // hacemos la conversion
            Developer developerSave = new Developer();
            developerSave.setDevId(developer.getDevId());
            developerSave.setDevName(developer.getDevName());
            developerSave.setDevSurname(developer.getDevSurname());
            developerSave.setEmail(developer.getEmail());
            developerSave.setLinkedinUrl(developer.getLinkedinUrl());
            developerSave.setGithubUrl(developer.getGithubUrl());
    
            return dRepo.save(developerSave);    

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error inesperado");
        }
    }

    
    @Override
    public Developer deleteDeveloper(Integer id) {
        try {
            //aqui comprobamos que exista el project con el id
            Developer developer = dRepo.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("Developer con id "+id+" no encontrado"));

            dRepo.deleteById(id);
            return developer;
 
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error inesperado");
        }
    }

    @Override
    public Project addDeveloperToProject(Integer projectId, Integer devId) {
        try {
            // comprobamos si existe el project por el id
            Project project = pRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

            // comprobamos si existe el dev por el id
            Developer dev = dRepo.findById(devId)
                .orElseThrow(() -> new IllegalArgumentException("Developer no encontrado"));
            
            project.getDevelopers().add(dev);
            return pRepo.save(project);
            
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No se pudo agregar el developer al proyecto: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Error inesperado al agregar el developer al proyecto");
        }
    }
}