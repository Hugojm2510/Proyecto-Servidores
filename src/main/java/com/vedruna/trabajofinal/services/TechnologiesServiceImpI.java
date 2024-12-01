package com.vedruna.trabajofinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vedruna.trabajofinal.dto.TechnologiesDTO;
import com.vedruna.trabajofinal.persistance.models.Project;
import com.vedruna.trabajofinal.persistance.models.Technologies;
import com.vedruna.trabajofinal.persistance.repository.ProjectRepositoryI;
import com.vedruna.trabajofinal.persistance.repository.TechnologiesRepository;

@Service
public class TechnologiesServiceImpI implements TechnologiesServiceI{
    
    @Autowired
    TechnologiesRepository tRepo;

    @Autowired
    ProjectRepositoryI pRepo;

    @Override
    public Technologies createTechnologies(TechnologiesDTO technologies) {
        try {
            // si algun bloque de la estructura es null, salta la excepcion
            if (technologies == null) {
                throw new IllegalArgumentException("La estructura del dto de tecnologia no puede ser nula");
            }

            // si la tech ya existe, salta la excepcion
            if (tRepo.existsById(technologies.getTechId())) {
                throw new IllegalArgumentException("La tecnologia ya existe");
            }

            // Hacemos la conversion
            Technologies newTechnologies = new Technologies();
            newTechnologies.setTechId(technologies.getTechId());
            newTechnologies.setTechName(technologies.getTechName());

            return tRepo.save(newTechnologies);
        
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tecnologia existe");
        } catch (Exception e) {
            // Capturamos cualquier error inesperado
            throw new RuntimeException("ocurrio un error inesperado al crear la tecnologia");
        }
        
    }

    @Override
    public Technologies saveTechnologies(TechnologiesDTO technologies) {
        try {

            // si algun bloque de la estructura es null, salta la excepcion
            if (technologies == null) {
                throw new IllegalArgumentException("El DTO de la tecnología no puede ser nulo.");
            }

            // Hacemos la conversion
            Technologies technologieSave = new Technologies();
            technologieSave.setTechId(technologies.getTechId());
            technologieSave.setTechName(technologies.getTechName());

            return tRepo.save(technologieSave);

        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Tecnologia no encontrada");
        } catch (Exception e) {
            // Capturamos cualquier error inesperado
            throw new RuntimeException("ocurrio un error inesperado al guardar tecnologia");
        }
        
    }

    @Override
    public Technologies deleteTechnologies(Integer id) {
        try {
            Technologies technologie = tRepo.findById(id).orElse(null);

            // comprobamos si existe el tech por el id
            if (id == null || !tRepo.existsById(id)) {
                throw new IllegalArgumentException("Tecnología con id " + id + " no encontrada.");
            }
          
            // Si el proyecto existe, lo eliminamos
            tRepo.deleteById(id);
    
            // Retornamos el proyecto eliminado
            return technologie;

        } catch (IllegalArgumentException e) {
            // Si no se encuentra el proyecto, lanzamos una excepción
            throw new IllegalArgumentException("Tecnologia con id " + id + " no encontrado");
        } catch (Exception e) {
            // Capturamos cualquier error inesperado
            throw new RuntimeException("Error al eliminar tecnologia");
        }
    }

    @Override
    public Project addTechToProject(Integer projectId, Integer techId) {
        try {
            // comprobar si el id de project existe
            Project project = pRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

            // comprobar si el id de tech existe    
            Technologies tech = tRepo.findById(techId)
                .orElseThrow(() -> new IllegalArgumentException("Developer no encontrado"));
            
            project.getTechnologies().add(tech);
            return pRepo.save(project);
            
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No se pudo agregar el developer al proyecto: " + e.getMessage());
        } catch (RuntimeException e) {
            // Capturamos cualquier error inesperado
            throw new RuntimeException("Error inesperado al agregar el developer al proyecto");
        }
    }   
}
