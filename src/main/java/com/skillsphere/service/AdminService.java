package com.skillsphere.service;

import com.skillsphere.dto.AdminReportsDto;
import com.skillsphere.dto.AdminStatsDto;
import com.skillsphere.dto.CourseDto;
import com.skillsphere.entity.Course;
import com.skillsphere.entity.Enrollment;
import com.skillsphere.entity.Role;
import com.skillsphere.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CertificateRepository certificateRepository;
    private final CourseService courseService;

    public AdminService(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, CertificateRepository certificateRepository, CourseService courseService) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.certificateRepository = certificateRepository;
        this.courseService = courseService;
    }

    public AdminStatsDto getStats() {
        long totalUsers = userRepository.count();
        long totalStudents = userRepository.countByRole(Role.STUDENT);
        long totalInstructors = userRepository.countByRole(Role.INSTRUCTOR);
        long totalCourses = courseRepository.count();
        long totalEnrollments = enrollmentRepository.count();
        long completedCourses = enrollmentRepository.countByCompletedTrue();
        long totalCertificates = certificateRepository.count();

        return AdminStatsDto.builder()
                .totalUsers(totalUsers)
                .totalStudents(totalStudents)
                .totalInstructors(totalInstructors)
                .totalCourses(totalCourses)
                .totalEnrollments(totalEnrollments)
                .completedCourses(completedCourses)
                .totalCertificates(totalCertificates)
                .build();
    }

    public AdminReportsDto getReports() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        List<Course> courses = courseRepository.findAll();

        Map<String, Long> enrollmentsPerCourse = new HashMap<>();
        for (Course c : courses) {
            long count = enrollments.stream().filter(e -> e.getCourse().getId().equals(c.getId())).count();
            enrollmentsPerCourse.put(c.getTitle(), count);
        }

        Map<String, Long> categoryWiseCourses = courses.stream()
                .collect(Collectors.groupingBy(Course::getCategory, Collectors.counting()));

        long totalEnrollments = enrollments.size();
        long completedEnrollments = enrollments.stream().filter(Enrollment::getCompleted).count();
        double completionRate = totalEnrollments > 0
                ? Math.round(((double) completedEnrollments / totalEnrollments) * 100.0 * 10.0) / 10.0
                : 0.0;

        List<CourseDto> topCourses = courses.stream()
                .map(courseService::mapToDto)
                .sorted((c1, c2) -> Integer.compare(c2.getEnrolledStudentCount(), c1.getEnrolledStudentCount()))
                .limit(5)
                .collect(Collectors.toList());

        return AdminReportsDto.builder()
                .enrollmentsPerCourse(enrollmentsPerCourse)
                .categoryWiseCourses(categoryWiseCourses)
                .completionRatePercentage(completionRate)
                .topCourses(topCourses)
                .build();
    }
}
