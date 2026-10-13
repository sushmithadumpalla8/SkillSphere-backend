package com.skillsphere.controller;

import com.skillsphere.dto.EnrollmentDto;
import com.skillsphere.entity.Enrollment;
import com.skillsphere.security.CustomUserDetails;
import com.skillsphere.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<EnrollmentDto> enroll(
            @RequestParam(required = false) Long courseId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        EnrollmentDto dto = enrollmentService.enrollStudent(userDetails.getStudentId(), courseId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/course/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<EnrollmentDto> enrollByPath(
            @PathVariable Long courseId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        EnrollmentDto dto = enrollmentService.enrollStudent(userDetails.getStudentId(), courseId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping({"/my", "/my-courses"})
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<EnrollmentDto>> getMyEnrollments(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(enrollmentService.getStudentEnrollments(userDetails.getStudentId()));
    }

    @GetMapping({"/course/{courseId}", "/course/{courseId}/students"})
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<List<EnrollmentDto>> getCourseEnrollments(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.getCourseEnrollments(courseId));
    }

    @GetMapping("/course/{courseId}/status")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Map<String, Object>> getEnrollmentStatus(
            @PathVariable Long courseId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Optional<Enrollment> enrollmentOpt = enrollmentService.getStudentEnrollmentInCourse(userDetails.getStudentId(), courseId);
        Map<String, Object> response = new HashMap<>();
        response.put("enrolled", enrollmentOpt.isPresent());
        response.put("enrollmentId", enrollmentOpt.map(Enrollment::getId).orElse(null));
        response.put("progressPercentage", enrollmentOpt.map(Enrollment::getProgressPercentage).orElse(0));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDto> getEnrollmentById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }
}
