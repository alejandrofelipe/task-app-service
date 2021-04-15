package com.example.task.rest;

import com.example.task.model.Priority;
import com.example.task.service.PriorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/priority")
@PreAuthorize("isAuthenticated()")
public class PriorityResource {
    private final PriorityService priorityService;

    public PriorityResource(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping
    public ResponseEntity<List<Priority>> getAllPriorities() {
        List<Priority> priorities = priorityService.list();
        return ResponseEntity.ok(priorities);
    }

    @PostMapping
    public ResponseEntity<Priority> addPriority(@Valid @RequestBody Priority priority) {
        Priority newPriority = priorityService.add(priority);
        return new ResponseEntity<>(newPriority, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Priority> updatePriority(@Valid @RequestBody Priority priority) {
        Priority updatePriority = priorityService.update(priority);
        return new ResponseEntity<>(updatePriority, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Priority> getPriority(@PathVariable Long id) {
        Priority priority = priorityService.get(id);
        return ResponseEntity.ok(priority);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePriority(@PathVariable Long id) {
        priorityService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
