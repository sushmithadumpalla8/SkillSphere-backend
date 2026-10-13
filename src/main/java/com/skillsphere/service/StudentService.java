package com.skillsphere.service;

import com.skillsphere.dto.StudentDto;
import com.skillsphere.entity.Student;
import com.skillsphere.entity.User;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.StudentRepository;
import com.skillsphere.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return mapToDto(student);
    }

    public StudentDto getStudentByUserId(Long userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for user id: " + userId));
        return mapToDto(student);
    }

    @Transactional
    public StudentDto updateStudentProfile(Long id, StudentDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        if (dto.getBio() != null) student.setBio(dto.getBio());
        if (dto.getQualification() != null) student.setQualification(dto.getQualification());
        if (dto.getAvatarUrl() != null) student.setAvatarUrl(dto.getAvatarUrl());

        if (dto.getFullName() != null) {
            User user = student.getUser();
            user.setFullName(dto.getFullName());
            userRepository.save(user);
        }

        studentRepository.save(student);
        return mapToDto(student);
    }

    public StudentDto mapToDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .userId(student.getUser().getId())
                .fullName(student.getUser().getFullName())
                .email(student.getUser().getEmail())
                .bio(student.getBio())
                .qualification(student.getQualification())
                .avatarUrl(student.getAvatarUrl())
                .build();
    }
}
