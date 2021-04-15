package com.example.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
public class Priority {
    public Priority(){
        this.color = "#ffffff";
        this.textColor = "#000000";
        this.value = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be empty.")
    @NotNull(message = "Name is required.")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Color cannot be empty.")
    private String color;

    @Column(nullable = false)
    @NotBlank(message = "Text color cannot be empty.")
    private String textColor;

    @Column(nullable = false)
    private Integer value;
}
