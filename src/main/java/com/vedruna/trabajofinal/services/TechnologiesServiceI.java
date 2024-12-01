package com.vedruna.trabajofinal.services;

import com.vedruna.trabajofinal.dto.TechnologiesDTO;
import com.vedruna.trabajofinal.persistance.models.Project;
import com.vedruna.trabajofinal.persistance.models.Technologies;

public interface TechnologiesServiceI {
    
    // servicio para crear tech
    Technologies createTechnologies(TechnologiesDTO technologies);
    // servicio para guardar tech
    Technologies saveTechnologies(TechnologiesDTO technologies);
    // servicio para borrar tech
    Technologies deleteTechnologies(Integer id);
    // servicio para agregar un tech en un project
    Project addTechToProject(Integer projectId, Integer techId);

}
