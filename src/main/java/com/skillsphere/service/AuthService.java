package com.skillsphere.service;

import com.skillsphere.dto.AuthRequest;
import com.skillsphere.dto.AuthResponse;
import com.skillsphere.dto.RegisterRequest;
import com.skillsphere.entity.Instructor;
import com.skillsphere.entity.Role;
import com.skillsphere.entity.Student;
import com.skillsphere.entity.User;
import com.skillsphere.exception.BadRequestException;
import com.skillsphere.exception.ResourceAlreadyExistsException;
import com.skillsphere.exception.UnauthorizedException;
import com.skillsphere.repository.InstructorRepository;
import com.skillsphere.repository.StudentRepository;
import com.skillsphere.repository.UserRepository;
import com.skillsphere.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, StudentRepository studentRepository, InstructorRepository instructorRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email is already registered: " + request.getEmail());
        }

        if (request.getRole() == Role.ADMIN) {
            throw new BadRequestException("Admin registration is not allowed via public API");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(request.getRole())
                .active(true)
                .build();

        userRepository.save(user);

        Long studentId = null;
        Long instructorId = null;

        if (request.getRole() == Role.STUDENT) {
            Student student = Student.builder()
                    .user(user)
                    .bio(request.getBio() != null ? request.getBio() : "")
                    .qualification(request.getQualification() != null ? request.getQualification() : "")
                    .build();
            studentRepository.save(student);
            studentId = student.getId();
        } else if (request.getRole() == Role.INSTRUCTOR) {
            Instructor instructor = Instructor.builder()
                    .user(user)
                    .title(request.getTitle() != null ? request.getTitle() : "Instructor")
                    .bio(request.getBio() != null ? request.getBio() : "")
                    .expertise(request.getExpertise() != null ? request.getExpertise() : "")
                    .organization(request.getOrganization() != null ? request.getOrganization() : "")
                    .avatarUrl(request.getAvatarUrl())
                    .build();
            instructorRepository.save(instructor);
            instructorId = instructor.getId();
        }

        String token = jwtUtils.generateToken(
                user.getEmail(),
                user.getId(),
                user.getRole().name(),
                studentId,
                instructorId
        );

        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .studentId(studentId)
                .instructorId(instructorId)
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!user.isActive()) {
            throw new UnauthorizedException("Your account has been deactivated. Please contact administrator.");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Invalid email or password");
        }

        Long studentId = null;
        Long instructorId = null;

        if (user.getRole() == Role.STUDENT) {
            studentId = studentRepository.findByUserId(user.getId())
                    .map(Student::getId)
                    .orElse(null);
        } else if (user.getRole() == Role.INSTRUCTOR) {
            instructorId = instructorRepository.findByUserId(user.getId())
                    .map(Instructor::getId)
                    .orElse(null);
        }

        String token = jwtUtils.generateToken(
                user.getEmail(),
                user.getId(),
                user.getRole().name(),
                studentId,
                instructorId
        );

        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .studentId(studentId)
                .instructorId(instructorId)
                .build();
    }
}
