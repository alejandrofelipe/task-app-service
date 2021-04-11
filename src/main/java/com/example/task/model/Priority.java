package com.example.task.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Priority {

    public Priority(){
        this.color = "#000000";
        this.value = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Integer value;
}
