package com.skillsphere.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_attempts")
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private Integer totalPossibleScore;

    @Column(nullable = false)
    private Double percentage;

    @Column(nullable = false)
    private Boolean passed;

    @Column(nullable = false, updatable = false)
    private LocalDateTime attemptedAt;

    public QuizAttempt() {}

    public QuizAttempt(Long id, Student student, Quiz quiz, Integer score, Integer totalPossibleScore, Double percentage, Boolean passed, LocalDateTime attemptedAt) {
        this.id = id;
        this.student = student;
        this.quiz = quiz;
        this.score = score;
        this.totalPossibleScore = totalPossibleScore;
        this.percentage = percentage;
        this.passed = passed;
        this.attemptedAt = attemptedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.attemptedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Integer getTotalPossibleScore() { return totalPossibleScore; }
    public void setTotalPossibleScore(Integer totalPossibleScore) { this.totalPossibleScore = totalPossibleScore; }

    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }

    public Boolean getPassed() { return passed; }
    public void setPassed(Boolean passed) { this.passed = passed; }

    public LocalDateTime getAttemptedAt() { return attemptedAt; }
    public void setAttemptedAt(LocalDateTime attemptedAt) { this.attemptedAt = attemptedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Student student;
        private Quiz quiz;
        private Integer score;
        private Integer totalPossibleScore;
        private Double percentage;
        private Boolean passed;
        private LocalDateTime attemptedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder student(Student student) { this.student = student; return this; }
        public Builder quiz(Quiz quiz) { this.quiz = quiz; return this; }
        public Builder score(Integer score) { this.score = score; return this; }
        public Builder totalPossibleScore(Integer totalPossibleScore) { this.totalPossibleScore = totalPossibleScore; return this; }
        public Builder percentage(Double percentage) { this.percentage = percentage; return this; }
        public Builder passed(Boolean passed) { this.passed = passed; return this; }
        public Builder attemptedAt(LocalDateTime attemptedAt) { this.attemptedAt = attemptedAt; return this; }

        public QuizAttempt build() {
            return new QuizAttempt(id, student, quiz, score, totalPossibleScore, percentage, passed, attemptedAt);
        }
    }
}
