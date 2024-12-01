package com.vedruna.trabajofinal.persistance.models;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="developers")
public class Developer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dev_id", nullable = false)
    private Integer devId;

    @Column(name="dev_name", nullable = false)
    private String devName;

    @Column(name="dev_surname", nullable = false)
    private String devSurname;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="github_url", nullable = false, unique = true)
    private String githubUrl;

    @Column(name="linkedin_url", nullable = false, unique = true)
    private String linkedinUrl;


    @ManyToMany(mappedBy = "developers", fetch= FetchType.LAZY)
    private List<Project> projects;


}
