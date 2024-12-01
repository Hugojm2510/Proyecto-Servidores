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
@Table(name="technologies")
public class Technologies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tech_id", nullable = false)
    private Integer techId;
    
    @Column(name="tech_name", unique = true)
    private String techName;

    @ManyToMany(mappedBy = "technologies", fetch= FetchType.LAZY)
    private List<Project> projects;

}