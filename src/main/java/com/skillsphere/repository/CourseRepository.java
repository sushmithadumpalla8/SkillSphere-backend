package com.skillsphere.repository;

import com.skillsphere.entity.Course;
import com.skillsphere.entity.CourseLevel;
import com.skillsphere.entity.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    List<Course> findByInstructorId(Long instructorId);
    List<Course> findByStatus(CourseStatus status);
    List<Course> findByCategoryContainingIgnoreCase(String category);
    List<Course> findByLevel(CourseLevel level);
}
