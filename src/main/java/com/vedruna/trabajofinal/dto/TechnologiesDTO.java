package com.vedruna.trabajofinal.dto;

import com.vedruna.trabajofinal.persistance.models.Technologies;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnologiesDTO {
    
    private Integer techId;    
    private String techName;

    // Estructura al insertar para crear y para mostrar en la parte de Project
    public TechnologiesDTO(Technologies technologies) {
        this.techId = technologies.getTechId();
        this.techName = technologies.getTechName();
    }
}
