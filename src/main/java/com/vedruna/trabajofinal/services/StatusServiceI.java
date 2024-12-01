package com.vedruna.trabajofinal.services;

import java.util.List;

import com.vedruna.trabajofinal.persistance.models.Project;
import com.vedruna.trabajofinal.persistance.models.Status;

public interface StatusServiceI {
    // Servicio para guardar status en project
    void saveStatus(Status status);
}
