package com.skillsphere.service;

import com.skillsphere.dto.InstructorDto;
import com.skillsphere.entity.Instructor;
import com.skillsphere.entity.User;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.InstructorRepository;
import com.skillsphere.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;

    public InstructorService(InstructorRepository instructorRepository, UserRepository userRepository) {
        this.instructorRepository = instructorRepository;
        this.userRepository = userRepository;
    }

    public List<InstructorDto> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public InstructorDto getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
        return mapToDto(instructor);
    }

    public InstructorDto getInstructorByUserId(Long userId) {
        Instructor instructor = instructorRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor profile not found for user id: " + userId));
        return mapToDto(instructor);
    }

    @Transactional
    public InstructorDto updateInstructorProfile(Long id, InstructorDto dto) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));

        if (dto.getTitle() != null) instructor.setTitle(dto.getTitle());
        if (dto.getBio() != null) instructor.setBio(dto.getBio());
        if (dto.getExpertise() != null) instructor.setExpertise(dto.getExpertise());
        if (dto.getOrganization() != null) instructor.setOrganization(dto.getOrganization());
        if (dto.getAvatarUrl() != null) instructor.setAvatarUrl(dto.getAvatarUrl());

        if (dto.getFullName() != null) {
            User user = instructor.getUser();
            user.setFullName(dto.getFullName());
            userRepository.save(user);
        }

        instructorRepository.save(instructor);
        return mapToDto(instructor);
    }

    public InstructorDto mapToDto(Instructor instructor) {
        return InstructorDto.builder()
                .id(instructor.getId())
                .userId(instructor.getUser().getId())
                .fullName(instructor.getUser().getFullName())
                .email(instructor.getUser().getEmail())
                .title(instructor.getTitle())
                .bio(instructor.getBio())
                .expertise(instructor.getExpertise())
                .organization(instructor.getOrganization())
                .avatarUrl(instructor.getAvatarUrl())
                .build();
    }
}
