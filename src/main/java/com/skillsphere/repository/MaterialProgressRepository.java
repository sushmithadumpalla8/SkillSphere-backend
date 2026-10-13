package com.skillsphere.repository;

import com.skillsphere.entity.MaterialProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialProgressRepository extends JpaRepository<MaterialProgress, Long> {
    Optional<MaterialProgress> findByEnrollmentIdAndMaterialId(Long enrollmentId, Long materialId);
    boolean existsByEnrollmentIdAndMaterialId(Long enrollmentId, Long materialId);
    List<MaterialProgress> findByEnrollmentId(Long enrollmentId);
    long countByEnrollmentId(Long enrollmentId);
}
