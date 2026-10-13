package com.skillsphere.repository;

import com.skillsphere.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findByCertificateNumber(String certificateNumber);
    Optional<Certificate> findByEnrollmentId(Long enrollmentId);
    List<Certificate> findByStudentId(Long studentId);
    List<Certificate> findByInstructorId(Long instructorId);
    List<Certificate> findByCourseId(Long courseId);
    boolean existsByEnrollmentId(Long enrollmentId);
}
