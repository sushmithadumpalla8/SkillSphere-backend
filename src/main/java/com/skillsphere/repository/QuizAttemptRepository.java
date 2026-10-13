package com.skillsphere.repository;

import com.skillsphere.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByStudentId(Long studentId);
    List<QuizAttempt> findByQuizId(Long quizId);
    List<QuizAttempt> findByStudentIdAndQuizId(Long studentId, Long quizId);
    List<QuizAttempt> findByQuizCourseInstructorId(Long instructorId);
    Optional<QuizAttempt> findFirstByStudentIdAndQuizIdOrderByPercentageDesc(Long studentId, Long quizId);
}
