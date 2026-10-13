package com.skillsphere.controller;

import com.skillsphere.dto.*;
import com.skillsphere.security.CustomUserDetails;
import com.skillsphere.service.CourseModuleService;
import com.skillsphere.service.LearningMaterialService;
import com.skillsphere.service.ProgressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final LearningMaterialService materialService;
    private final CourseModuleService moduleService;
    private final ProgressService progressService;

    public MaterialController(
            LearningMaterialService materialService,
            CourseModuleService moduleService,
            ProgressService progressService
    ) {
        this.materialService = materialService;
        this.moduleService = moduleService;
        this.progressService = progressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningMaterialDto> getMaterialById(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.getMaterialById(id));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Map<String, Object>> getCourseMaterials(
            @PathVariable Long courseId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long studentId = userDetails != null ? userDetails.getStudentId() : null;
        Long instructorId = userDetails != null ? userDetails.getInstructorId() : null;
        boolean isAdmin = userDetails != null && userDetails.getUser().getRole().name().equals("ADMIN");

        List<CourseModuleDto> modules = moduleService.getModulesByCourseId(courseId, studentId, instructorId, isAdmin);

        int progressPercentage = 0;
        if (studentId != null) {
            try {
                EnrollmentDto enrollment = progressService.getCourseProgress(studentId, courseId);
                if (enrollment != null && enrollment.getProgressPercentage() != null) {
                    progressPercentage = enrollment.getProgressPercentage();
                }
            } catch (Exception ignored) {
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("courseId", courseId);
        response.put("progressPercentage", progressPercentage);
        response.put("modules", modules);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/complete")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<EnrollmentDto> markMaterialComplete(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        EnrollmentDto updated = progressService.markMaterialCompleteByStudent(userDetails.getStudentId(), id);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/module/{moduleId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<LearningMaterialDto> addMaterial(
            @PathVariable Long moduleId,
            @Valid @RequestBody LearningMaterialRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isAdmin = userDetails.getUser().getRole().name().equals("ADMIN");
        LearningMaterialDto created = materialService.addMaterial(moduleId, request, userDetails.getInstructorId(), isAdmin);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<LearningMaterialDto> updateMaterial(
            @PathVariable Long id,
            @Valid @RequestBody LearningMaterialRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isAdmin = userDetails.getUser().getRole().name().equals("ADMIN");
        LearningMaterialDto updated = materialService.updateMaterial(id, request, userDetails.getInstructorId(), isAdmin);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMaterial(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isAdmin = userDetails.getUser().getRole().name().equals("ADMIN");
        materialService.deleteMaterial(id, userDetails.getInstructorId(), isAdmin);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/modules/{moduleId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<CourseModuleDto> updateModule(
            @PathVariable Long moduleId,
            @Valid @RequestBody CourseModuleRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isAdmin = userDetails.getUser().getRole().name().equals("ADMIN");
        CourseModuleDto updated = moduleService.updateModule(moduleId, request, userDetails.getInstructorId(), isAdmin);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/modules/{moduleId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteModule(
            @PathVariable Long moduleId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isAdmin = userDetails.getUser().getRole().name().equals("ADMIN");
        moduleService.deleteModule(moduleId, userDetails.getInstructorId(), isAdmin);
        return ResponseEntity.noContent().build();
    }
}
