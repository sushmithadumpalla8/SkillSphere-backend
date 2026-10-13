package com.skillsphere.security;

import com.skillsphere.entity.Instructor;
import com.skillsphere.entity.Student;
import com.skillsphere.entity.User;
import com.skillsphere.exception.UnauthorizedException;
import com.skillsphere.repository.InstructorRepository;
import com.skillsphere.repository.StudentRepository;
import com.skillsphere.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    public CustomUserDetailsService(UserRepository userRepository, StudentRepository studentRepository, InstructorRepository instructorRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (!user.isActive()) {
            throw new UnauthorizedException("User account is deactivated");
        }

        Long studentId = null;
        Long instructorId = null;

        if (user.getRole().name().equals("STUDENT")) {
            studentId = studentRepository.findByUserId(user.getId())
                    .map(Student::getId)
                    .orElse(null);
        } else if (user.getRole().name().equals("INSTRUCTOR")) {
            instructorId = instructorRepository.findByUserId(user.getId())
                    .map(Instructor::getId)
                    .orElse(null);
        }

        return new CustomUserDetails(user, studentId, instructorId);
    }
}
