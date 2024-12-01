package com.vedruna.trabajofinal.dto;

import com.vedruna.trabajofinal.persistance.models.Developer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperDTO {
    
    private Integer devId;
    private String devName;
    private String devSurname;

    // Estructura que se va a mostrar en Project en la parte de Developer
    public DeveloperDTO (Developer developer) {
        this.devId = developer.getDevId();
        this.devName = developer.getDevName();
        this.devSurname = developer.getDevSurname();
    }
}
