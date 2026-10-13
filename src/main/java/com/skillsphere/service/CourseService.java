package com.skillsphere.service;

import com.skillsphere.dto.CourseCreateRequest;
import com.skillsphere.dto.CourseDto;
import com.skillsphere.entity.*;
import com.skillsphere.exception.ForbiddenException;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final QuizRepository quizRepository;

    public CourseService(CourseRepository courseRepository, InstructorRepository instructorRepository, EnrollmentRepository enrollmentRepository, QuizRepository quizRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.quizRepository = quizRepository;
    }

    public List<CourseDto> getAllCourses(String search, String category, CourseLevel level, Boolean isFree) {
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .filter(c -> c.getStatus() == CourseStatus.PUBLISHED)
                .filter(c -> search == null || search.isBlank() ||
                        c.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                        c.getDescription().toLowerCase().contains(search.toLowerCase()) ||
                        c.getCategory().toLowerCase().contains(search.toLowerCase()))
                .filter(c -> category == null || category.isBlank() || c.getCategory().equalsIgnoreCase(category))
                .filter(c -> level == null || c.getLevel() == level)
                .filter(c -> isFree == null || c.getIsFree().equals(isFree))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CourseDto> getInstructorCourses(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return mapToDto(course);
    }

    @Transactional
    public CourseDto createCourse(CourseCreateRequest request, Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor profile not found with id: " + instructorId));

        boolean free = request.getIsFree() != null ? request.getIsFree() : (request.getPrice() == null || request.getPrice() == 0);

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .level(request.getLevel())
                .duration(request.getDuration() != null ? request.getDuration() : "10 Hours")
                .price(free ? 0.0 : request.getPrice())
                .isFree(free)
                .imageUrl(request.getImageUrl() != null && !request.getImageUrl().isBlank()
                        ? request.getImageUrl()
                        : "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=800&q=80")
                .learningObjectives(request.getLearningObjectives())
                .status(request.getStatus() != null ? request.getStatus() : CourseStatus.PUBLISHED)
                .instructor(instructor)
                .build();

        Course savedCourse = courseRepository.save(course);
        return mapToDto(savedCourse);
    }

    @Transactional
    public CourseDto updateCourse(Long id, CourseCreateRequest request, Long instructorId, boolean isAdmin) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        if (!isAdmin && !course.getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not have permission to modify this course");
        }

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCategory(request.getCategory());
        course.setLevel(request.getLevel());
        if (request.getDuration() != null) course.setDuration(request.getDuration());
        if (request.getPrice() != null) course.setPrice(request.getPrice());
        if (request.getIsFree() != null) course.setIsFree(request.getIsFree());
        if (request.getImageUrl() != null) course.setImageUrl(request.getImageUrl());
        if (request.getLearningObjectives() != null) course.setLearningObjectives(request.getLearningObjectives());
        if (request.getStatus() != null) course.setStatus(request.getStatus());

        Course updated = courseRepository.save(course);
        return mapToDto(updated);
    }

    @Transactional
    public void deleteCourse(Long id, Long instructorId, boolean isAdmin) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        if (!isAdmin && !course.getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not have permission to delete this course");
        }

        courseRepository.delete(course);
    }

    public CourseDto mapToDto(Course course) {
        int moduleCount = course.getModules() != null ? course.getModules().size() : 0;
        int enrolledCount = enrollmentRepository.findByCourseId(course.getId()).size();
        Long quizId = quizRepository.findFirstByCourseId(course.getId()).map(Quiz::getId).orElse(null);

        return CourseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .category(course.getCategory())
                .level(course.getLevel())
                .duration(course.getDuration())
                .price(course.getPrice())
                .isFree(course.getIsFree())
                .imageUrl(course.getImageUrl())
                .learningObjectives(course.getLearningObjectives())
                .status(course.getStatus())
                .createdAt(course.getCreatedAt())
                .instructorId(course.getInstructor().getId())
                .instructorName(course.getInstructor().getUser().getFullName())
                .instructorTitle(course.getInstructor().getTitle())
                .instructorAvatarUrl(course.getInstructor().getAvatarUrl())
                .moduleCount(moduleCount)
                .enrolledStudentCount(enrolledCount)
                .quizId(quizId)
                .build();
    }
}
