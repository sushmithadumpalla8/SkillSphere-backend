package com.skillsphere.repository;

import com.skillsphere.entity.Instructor;
import com.skillsphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByUser(User user);
    Optional<Instructor> findByUserId(Long userId);
    Optional<Instructor> findByUserEmail(String email);
}
