package com.authentication.login.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @Column(name = "task_id")
    private String taskId;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private int status;
}
