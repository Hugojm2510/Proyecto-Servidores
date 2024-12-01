package com.vedruna.trabajofinal.dto;

import com.vedruna.trabajofinal.persistance.models.Developer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeveloperDTO {
    
    private Integer devId;
    private String devName;
    private String devSurname;
    private String email;
    private String linkedinUrl;
    private String githubUrl;

    // Estructura que hay que insertar para crear el Developer
    public CreateDeveloperDTO(Developer developer) {
        this.devId = developer.getDevId();
        this.devName = developer.getDevName();
        this.devSurname = developer.getDevSurname();
        this.email = developer.getEmail();
        this.linkedinUrl = developer.getLinkedinUrl();
        this.githubUrl = developer.getGithubUrl();
    }
}
