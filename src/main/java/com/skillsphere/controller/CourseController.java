package com.skillsphere.controller;

import com.skillsphere.dto.CourseCreateRequest;
import com.skillsphere.dto.CourseDto;
import com.skillsphere.dto.CourseModuleDto;
import com.skillsphere.dto.CourseModuleRequest;
import com.skillsphere.entity.CourseLevel;
import com.skillsphere.security.CustomUserDetails;
import com.skillsphere.service.CourseModuleService;
import com.skillsphere.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final CourseModuleService moduleService;

    public CourseController(CourseService courseService, CourseModuleService moduleService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) CourseLevel level,
            @RequestParam(required = false) Boolean isFree
    ) {
        return ResponseEntity.ok(courseService.getAllCourses(search, category, level, isFree));
    }

    @GetMapping({"/instructor/my", "/instructor/my-courses"})
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<List<CourseDto>> getMyCourses(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(courseService.getInstructorCourses(userDetails.getInstructorId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<CourseDto> createCourse(
            @Valid @RequestBody CourseCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CourseDto created = courseService.createCourse(request, userDetails.getInstructorId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<CourseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isAdmin = userDetails.getUser().getRole().name().equals("ADMIN");
        CourseDto updated = courseService.updateCourse(id, request, userDetails.getInstructorId(), isAdmin);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isAdmin = userDetails.getUser().getRole().name().equals("ADMIN");
        courseService.deleteCourse(id, userDetails.getInstructorId(), isAdmin);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{courseId}/modules")
    public ResponseEntity<List<CourseModuleDto>> getCourseModules(
            @PathVariable Long courseId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long studentId = userDetails != null ? userDetails.getStudentId() : null;
        Long instructorId = userDetails != null ? userDetails.getInstructorId() : null;
        boolean isAdmin = userDetails != null && userDetails.getUser().getRole().name().equals("ADMIN");

        return ResponseEntity.ok(moduleService.getModulesByCourseId(courseId, studentId, instructorId, isAdmin));
    }

    @PostMapping("/{courseId}/modules")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<CourseModuleDto> addModule(
            @PathVariable Long courseId,
            @Valid @RequestBody CourseModuleRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isAdmin = userDetails.getUser().getRole().name().equals("ADMIN");
        CourseModuleDto created = moduleService.addModule(courseId, request, userDetails.getInstructorId(), isAdmin);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
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
