package com.example.task.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Task {

    public Task() {
        this.created = this.updated = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title cannot be blank.")
    @NotNull(message = "Title is required.")
    private String title;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Description cannot be blank.")
    @NotNull(message = "Description is required.")
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private Employee employee;

    @Column(name = "employee_id")
    @NotNull(message = "Responsible/Employee is required.")
    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "priority_id", insertable = false, updatable = false)
    private Priority priority;

    @Column(name = "priority_id")
    @NotNull(message = "Priority is required.")
    private Long priorityId;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date created;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updated;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Deadline is required.")
    private Date deadline;

    @Column(nullable = false)
    private Boolean completed;

    public Boolean isCompleted() {
        return completed;
    }
}
