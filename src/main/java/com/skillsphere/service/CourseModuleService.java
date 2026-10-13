package com.skillsphere.service;

import com.skillsphere.dto.CourseModuleDto;
import com.skillsphere.dto.CourseModuleRequest;
import com.skillsphere.dto.LearningMaterialDto;
import com.skillsphere.entity.Course;
import com.skillsphere.entity.CourseModule;
import com.skillsphere.exception.ForbiddenException;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.CourseModuleRepository;
import com.skillsphere.repository.CourseRepository;
import com.skillsphere.repository.EnrollmentRepository;
import com.skillsphere.repository.MaterialProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseModuleService {

    private final CourseModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final MaterialProgressRepository progressRepository;

    public CourseModuleService(CourseModuleRepository moduleRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, MaterialProgressRepository progressRepository) {
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.progressRepository = progressRepository;
    }

    public List<CourseModuleDto> getModulesByCourseId(Long courseId, Long studentId, Long instructorId, boolean isAdmin) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        Long currentEnrollmentId = null;
        if (studentId != null && !isAdmin) {
            boolean isInstructor = instructorId != null && course.getInstructor().getId().equals(instructorId);
            if (!isInstructor) {
                var enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                        .orElseThrow(() -> new ForbiddenException("Access Denied: You must be enrolled in this course to view learning materials."));
                currentEnrollmentId = enrollment.getId();
            }
        }

        final Long finalEnrollmentId = currentEnrollmentId;

        return moduleRepository.findByCourseIdOrderByModuleNumberAsc(courseId).stream()
                .map(mod -> mapToDto(mod, finalEnrollmentId))
                .collect(Collectors.toList());
    }

    @Transactional
    public CourseModuleDto addModule(Long courseId, CourseModuleRequest request, Long instructorId, boolean isAdmin) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        if (!isAdmin && !course.getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not have permission to add modules to this course");
        }

        CourseModule module = CourseModule.builder()
                .course(course)
                .moduleNumber(request.getModuleNumber())
                .title(request.getTitle())
                .description(request.getDescription())
                .build();

        CourseModule saved = moduleRepository.save(module);
        return mapToDto(saved, null);
    }

    @Transactional
    public CourseModuleDto updateModule(Long id, CourseModuleRequest request, Long instructorId, boolean isAdmin) {
        CourseModule module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + id));

        if (!isAdmin && !module.getCourse().getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not have permission to update this module");
        }

        module.setModuleNumber(request.getModuleNumber());
        module.setTitle(request.getTitle());
        if (request.getDescription() != null) module.setDescription(request.getDescription());

        CourseModule updated = moduleRepository.save(module);
        return mapToDto(updated, null);
    }

    @Transactional
    public void deleteModule(Long id, Long instructorId, boolean isAdmin) {
        CourseModule module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + id));

        if (!isAdmin && !module.getCourse().getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not have permission to delete this module");
        }

        moduleRepository.delete(module);
    }

    public CourseModuleDto mapToDto(CourseModule module, Long enrollmentId) {
        List<LearningMaterialDto> materials = module.getMaterials() != null
                ? module.getMaterials().stream().map(mat -> {
                    boolean completed = false;
                    if (enrollmentId != null) {
                        completed = progressRepository.existsByEnrollmentIdAndMaterialId(enrollmentId, mat.getId());
                    }
                    return LearningMaterialDto.builder()
                            .id(mat.getId())
                            .moduleId(module.getId())
                            .type(mat.getType())
                            .title(mat.getTitle())
                            .description(mat.getDescription())
                            .contentUrlOrText(mat.getContentUrlOrText())
                            .orderIndex(mat.getOrderIndex())
                            .completed(completed)
                            .build();
                }).collect(Collectors.toList())
                : List.of();

        return CourseModuleDto.builder()
                .id(module.getId())
                .courseId(module.getCourse().getId())
                .moduleNumber(module.getModuleNumber())
                .title(module.getTitle())
                .description(module.getDescription())
                .materials(materials)
                .build();
    }
}
