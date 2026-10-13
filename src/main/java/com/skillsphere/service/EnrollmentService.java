package com.skillsphere.service;

import com.skillsphere.dto.EnrollmentDto;
import com.skillsphere.entity.Certificate;
import com.skillsphere.entity.Course;
import com.skillsphere.entity.Enrollment;
import com.skillsphere.entity.Student;
import com.skillsphere.exception.ResourceAlreadyExistsException;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CertificateRepository certificateRepository;
    private final MaterialProgressRepository progressRepository;
    private final LearningMaterialRepository materialRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            CertificateRepository certificateRepository,
            MaterialProgressRepository progressRepository,
            LearningMaterialRepository materialRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.certificateRepository = certificateRepository;
        this.progressRepository = progressRepository;
        this.materialRepository = materialRepository;
    }

    @Transactional
    public EnrollmentDto enrollStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new ResourceAlreadyExistsException("Student is already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .progressPercentage(0)
                .completed(false)
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);
        return mapToDto(saved);
    }

    public List<EnrollmentDto> getStudentEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<EnrollmentDto> getCourseEnrollments(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public EnrollmentDto getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
        return mapToDto(enrollment);
    }

    public Optional<Enrollment> getStudentEnrollmentInCourse(Long studentId, Long courseId) {
        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);
    }

    public EnrollmentDto mapToDto(Enrollment enrollment) {
        Optional<Certificate> certOpt = certificateRepository.findByEnrollmentId(enrollment.getId());

        Long courseId = enrollment.getCourse().getId();
        long completedMaterials = progressRepository.countByEnrollmentId(enrollment.getId());
        long totalMaterials = materialRepository.countByModuleCourseId(courseId);

        String status = (enrollment.getCompleted() != null && enrollment.getCompleted()) ||
                (enrollment.getProgressPercentage() != null && enrollment.getProgressPercentage() >= 100)
                ? "COMPLETED" : "IN_PROGRESS";

        return EnrollmentDto.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudent().getId())
                .studentName(enrollment.getStudent().getUser().getFullName())
                .courseId(courseId)
                .courseTitle(enrollment.getCourse().getTitle())
                .courseCategory(enrollment.getCourse().getCategory())
                .courseImageUrl(enrollment.getCourse().getImageUrl())
                .instructorName(enrollment.getCourse().getInstructor().getUser().getFullName())
                .enrolledAt(enrollment.getEnrolledAt())
                .progressPercentage(enrollment.getProgressPercentage())
                .completed(enrollment.getCompleted())
                .completedAt(enrollment.getCompletedAt())
                .certificateId(certOpt.map(Certificate::getId).orElse(null))
                .certificateNumber(certOpt.map(Certificate::getCertificateNumber).orElse(null))
                .status(status)
                .completedMaterialsCount(completedMaterials)
                .totalMaterialsCount(totalMaterials)
                .build();
    }
}
