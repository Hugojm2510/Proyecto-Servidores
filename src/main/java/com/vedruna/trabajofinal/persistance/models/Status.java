package com.vedruna.trabajofinal.persistance.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name="status")
public class Status {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="status_id", nullable = false)
    private Integer statusId;

    @Column(name="status_name", nullable = false, unique = true)
    private String statusName;

    /*
    Crear relacion con project
    Esta mapeada con status, que es la variable que hemos inicializado en Project
    Devuelve una lista, porque asi es mas facil navegar, ya que navegas por el estado que pones
    */
    @OneToMany(mappedBy = "status", fetch= FetchType.LAZY)
    private List<Project> projects;
    
}
