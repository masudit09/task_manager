package com.ovalhr.taskmanager.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ovalhr.taskmanager.enumeration.TaskStatus;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by rana on 8/27/21.
 */
@Entity
@Table(name = "TASKS")
public class Task extends Audit {

    @SequenceGenerator(name = "TASK_SEQUENCE_GENERATOR", sequenceName = "TASK_ID_SEQUENCE")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TASK_SEQUENCE_GENERATOR")
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, precision = 38, scale = 0)
    private Long id;

    @Column(name = "DESCRIPTION", length = 1024)
    @NotEmpty(message = "Description is required.")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT+6")
    @Column(name = "DUE_Date")
    private Date dueDate = new Date();

    @Column(name = "PROJECT_ID")
    @NotNull(message = "Project is required.")
    private Long project;

    @Column(name = "APPOINT_STAT")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus = TaskStatus.OPEN;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
