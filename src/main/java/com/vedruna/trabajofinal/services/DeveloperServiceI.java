package com.vedruna.trabajofinal.services;

import com.vedruna.trabajofinal.dto.CreateDeveloperDTO;
import com.vedruna.trabajofinal.persistance.models.Developer;
import com.vedruna.trabajofinal.persistance.models.Project;

public interface DeveloperServiceI {

    // servicio para crear el dev
    Developer createDeveloper(CreateDeveloperDTO developer);
    // servicio para guardar el dev
    Developer saveDeveloper (CreateDeveloperDTO developer);
    // servicio para borrar el dev
    Developer deleteDeveloper(Integer id);
    // servicio para agregar el dev a un project
    Project addDeveloperToProject(Integer projectId, Integer devId);
}
