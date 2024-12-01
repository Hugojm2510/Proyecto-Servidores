package com.vedruna.trabajofinal.persistance.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="projects")
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id", nullable = false)
    private Integer projectId;

    @Column(name="project_name", nullable = false, unique = true)
    private String projectName;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    @Column(name="repository_url", nullable = false)
    private String repositoryUrl;

    @Column(name="demo_url", nullable = false)
    private String demoUrl;

    @Column(name="picture", nullable = false)
    private String picture;


    /*
    Relacion utilizada:
    @ManyToOne, el motivo es que un proyecto solo va a tener un estado,
    mientras que un estado puede tener multiples proyectos:
    - 3 trabajos "en progreso"
    - como esta la tarea, "terminada"
    - esta va a ser la relcion fuerte, por eso en Status esta mappedBy con el status de abajo
    */

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="status_status_id", referencedColumnName = "status_id")
    private Status status;
    

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "developers_worked_on_projects",
        joinColumns = @JoinColumn(name = "projects_project_id"),
        inverseJoinColumns = @JoinColumn(name = "developers_dev_id")
        /*
        Define la columna en la tabla intermedia que contiene la clave foránea de la
        entidad inversa, que en este caso es Developer. Esto significa que
        developers_dev_id es la columna que referencia a developers.
        */
    )
    private List<Developer> developers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "technologies_used_in_projects",
        joinColumns = @JoinColumn(name = "projects_project_id"),
        inverseJoinColumns = @JoinColumn(name = "technologies_tech_id")
    )
    private List<Technologies> technologies;

}
