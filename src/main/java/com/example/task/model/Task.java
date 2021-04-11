package com.example.task.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Task {

    public Task() {
        this.created = this.updated = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Priority priority;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date updated;

    @Column(nullable = false)
    private Date deadline;
}
