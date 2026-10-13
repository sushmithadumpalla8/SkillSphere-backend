package com.skillsphere.controller;

import com.skillsphere.dto.EnrollmentDto;
import com.skillsphere.security.CustomUserDetails;
import com.skillsphere.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping("/complete")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<EnrollmentDto> markMaterialComplete(
            @RequestParam Long enrollmentId,
            @RequestParam Long materialId
    ) {
        EnrollmentDto updated = progressService.markMaterialComplete(enrollmentId, materialId);
        return ResponseEntity.ok(updated);
    }

    @GetMapping({"/my", "/my-progress"})
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<EnrollmentDto>> getMyProgress(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(progressService.getMyProgress(userDetails.getStudentId()));
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<EnrollmentDto> getCourseProgress(
            @PathVariable Long courseId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(progressService.getCourseProgress(userDetails.getStudentId(), courseId));
    }
}
