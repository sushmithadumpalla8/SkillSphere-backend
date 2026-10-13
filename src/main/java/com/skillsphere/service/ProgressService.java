package com.skillsphere.service;

import com.skillsphere.dto.EnrollmentDto;
import com.skillsphere.entity.*;
import com.skillsphere.exception.ForbiddenException;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    private final EnrollmentRepository enrollmentRepository;
    private final LearningMaterialRepository materialRepository;
    private final MaterialProgressRepository progressRepository;
    private final QuizRepository quizRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final CertificateService certificateService;
    private final EnrollmentService enrollmentService;

    public ProgressService(
            EnrollmentRepository enrollmentRepository,
            LearningMaterialRepository materialRepository,
            MaterialProgressRepository progressRepository,
            QuizRepository quizRepository,
            QuizAttemptRepository quizAttemptRepository,
            CertificateService certificateService,
            EnrollmentService enrollmentService
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.materialRepository = materialRepository;
        this.progressRepository = progressRepository;
        this.quizRepository = quizRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.certificateService = certificateService;
        this.enrollmentService = enrollmentService;
    }

    @Transactional
    public EnrollmentDto markMaterialComplete(Long enrollmentId, Long materialId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + enrollmentId));

        LearningMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found with id: " + materialId));

        if (!progressRepository.existsByEnrollmentIdAndMaterialId(enrollmentId, materialId)) {
            MaterialProgress mp = MaterialProgress.builder()
                    .enrollment(enrollment)
                    .material(material)
                    .completedAt(LocalDateTime.now())
                    .build();
            progressRepository.save(mp);
        }

        Long courseId = enrollment.getCourse().getId();
        long totalMaterials = materialRepository.countByModuleCourseId(courseId);
        long completedMaterials = progressRepository.countByEnrollmentId(enrollmentId);

        int progressPercent = 100;
        if (totalMaterials > 0) {
            progressPercent = (int) Math.min(100, Math.round(((double) completedMaterials / totalMaterials) * 100));
        }

        enrollment.setProgressPercentage(progressPercent);

        if (progressPercent >= 100) {
            boolean quizPassedOrNotRequired = checkQuizPassed(enrollment.getStudent().getId(), courseId);
            if (quizPassedOrNotRequired && !enrollment.getCompleted()) {
                enrollment.setCompleted(true);
                enrollment.setCompletedAt(LocalDateTime.now());
                enrollmentRepository.save(enrollment);
                certificateService.generateCertificateForEnrollment(enrollment);
            }
        }

        enrollmentRepository.save(enrollment);
        return enrollmentService.mapToDto(enrollment);
    }

    @Transactional
    public EnrollmentDto markMaterialCompleteByStudent(Long studentId, Long materialId) {
        LearningMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found with id: " + materialId));

        Long courseId = material.getModule().getCourse().getId();
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ForbiddenException("Student is not enrolled in this course"));

        return markMaterialComplete(enrollment.getId(), materialId);
    }

    public List<EnrollmentDto> getMyProgress(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(enrollmentService::mapToDto)
                .collect(Collectors.toList());
    }

    public EnrollmentDto getCourseProgress(Long studentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("No enrollment found for student in course id: " + courseId));
        return enrollmentService.mapToDto(enrollment);
    }

    public boolean checkQuizPassed(Long studentId, Long courseId) {
        var quizOpt = quizRepository.findFirstByCourseId(courseId);
        if (quizOpt.isEmpty()) {
            return true;
        }
        Quiz quiz = quizOpt.get();
        var attempts = quizAttemptRepository.findByStudentIdAndQuizId(studentId, quiz.getId());
        return attempts.stream().anyMatch(QuizAttempt::getPassed);
    }
}
