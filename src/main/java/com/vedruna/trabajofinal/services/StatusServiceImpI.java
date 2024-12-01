package com.vedruna.trabajofinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vedruna.trabajofinal.persistance.models.Status;
import com.vedruna.trabajofinal.persistance.repository.StatusRepositoryI;

@Service
public class StatusServiceImpI implements StatusServiceI{
 
    @Autowired
    StatusRepositoryI sRepo;

    // Metodo utilizado para guardar el status en el Project
    @Override
    public void saveStatus(Status status) {
        sRepo.save(status);
    }
}
