package com.skillsphere.repository;

import com.skillsphere.entity.LearningMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, Long> {
    List<LearningMaterial> findByModuleIdOrderByOrderIndexAsc(Long moduleId);
    long countByModuleCourseId(Long courseId);
}
