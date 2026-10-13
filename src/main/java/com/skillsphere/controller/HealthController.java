package com.skillsphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "SkillSphere Backend API");
        response.put("status", "UP");
        response.put("timestamp", Instant.now().toString());
        response.put("message", "Welcome to SkillSphere Learning Platform API");
        return ResponseEntity.ok(response);
    }

    @GetMapping({"/health", "/api/health"})
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "skillsphere-backend");
        response.put("timestamp", Instant.now().toString());
        return ResponseEntity.ok(response);
    }
}
