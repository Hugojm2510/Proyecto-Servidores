package com.vedruna.trabajofinal.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.vedruna.trabajofinal.persistance.models.Developer;
import com.vedruna.trabajofinal.persistance.models.Project;
import com.vedruna.trabajofinal.persistance.models.Technologies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    
    private Integer projectId;
    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String repositoryUrl;
    private String demoUrl;
    private String picture;
    private String status;
    private List<DeveloperDTO> developers;
    private List<TechnologiesDTO> technologies;
    // se añade ArrayList, para agregar elementos

    // Estructura que se va a mostrar de cada project
    public ProjectDTO(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.description = project.getDescription();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.repositoryUrl = project.getRepositoryUrl();
        this.demoUrl = project.getDemoUrl();
        this.picture = project.getPicture();
        this.status = project.getStatus().getStatusName();

        this.developers = new ArrayList<>();
        for (Developer developer : project.getDevelopers()) {
            DeveloperDTO developerdto = new DeveloperDTO(developer);
            this.developers.add(developerdto);
        }
        /*
        Por cada Developer guarda una lista de developers
        y por cada lista de developer, la convierte en un developerdto,
        para utilizar el constructor. Los añadimos a los developerdto
        para utilizarlo en este constructor. 
        */

        this.technologies = new ArrayList<>();
        for (Technologies technologie : project.getTechnologies()) {
            TechnologiesDTO techdto = new TechnologiesDTO(technologie);
            this.technologies.add(techdto);
        }
    }
}