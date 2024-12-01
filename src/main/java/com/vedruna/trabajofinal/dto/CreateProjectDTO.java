package com.vedruna.trabajofinal.dto;

import java.time.LocalDate;
import com.vedruna.trabajofinal.persistance.models.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectDTO {
    
    private Integer projectId;
    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String repositoryUrl;
    private String demoUrl;
    private String picture;
    private Integer statusId;

    // Estructura que va tener que insertar para crear el project
    public CreateProjectDTO(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.description = project.getDescription();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.repositoryUrl = project.getRepositoryUrl();
        this.demoUrl = project.getDemoUrl();
        this.picture = project.getPicture();
        this.statusId = project.getStatus().getStatusId();
    }
}
