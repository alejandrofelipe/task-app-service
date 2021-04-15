package com.example.task.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AuthResponse {
    @Getter
    private final String jwt;
}
