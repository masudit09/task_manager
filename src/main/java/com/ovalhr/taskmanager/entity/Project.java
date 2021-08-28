package com.ovalhr.taskmanager.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.persistence.*;

/**
 * Created by rana on 8/27/21.
 */
@Entity
@Table(name = "PROJECTS")
public class Project extends Audit {

    @SequenceGenerator(name = "PROJECT_SEQUENCE_GENERATOR", sequenceName = "PROJECT_ID_SEQUENCE")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PROJECT_SEQUENCE_GENERATOR")
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, precision = 38, scale = 0)
    private Long id;

    @Column(name = "NAME")
    @NotEmpty(message = "Porject Name is required.")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
