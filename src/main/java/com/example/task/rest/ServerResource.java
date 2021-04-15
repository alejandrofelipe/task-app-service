package com.example.task.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@PreAuthorize("permitAll()")
public class ServerResource {

    @GetMapping
    public ResponseEntity<?> status() {
        Map<String, String> map = new HashMap<>();
        map.put("status", "OK");
        return ResponseEntity.ok(map);
    }
}
