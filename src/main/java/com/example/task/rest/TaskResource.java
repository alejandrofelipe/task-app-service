package com.example.task.rest;

import com.example.task.exception.ValidationErrorException;
import com.example.task.model.Task;
import com.example.task.service.TaskService;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.Validation;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@PreAuthorize("isAuthenticated()")
public class TaskResource {
    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.list();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> search(Task task) {
        System.out.println(task);
        List<Task> tasks = taskService.search(task);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@Valid @RequestBody Task task, Errors errors) {
        if(errors.hasErrors())
            throw new ValidationErrorException(errors.getAllErrors());
        Task newTask = taskService.add(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task, Errors errors) {
        if(errors.hasErrors())
            throw new ValidationErrorException(errors.getAllErrors());
        Task updateTask = taskService.update(task);
        return new ResponseEntity<>(updateTask, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task task = taskService.get(id);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
