package com.skillsphere.dto;

import java.time.LocalDateTime;

public class QuizAttemptDto {
    private Long id;
    private Long quizId;
    private String quizTitle;
    private Long courseId;
    private String courseTitle;
    private Long studentId;
    private String studentName;
    private Integer score;
    private Integer totalPossibleScore;
    private Double percentage;
    private Boolean passed;
    private LocalDateTime attemptedAt;

    public QuizAttemptDto() {}

    public QuizAttemptDto(Long id, Long quizId, String quizTitle, Long courseId, String courseTitle, Long studentId, String studentName, Integer score, Integer totalPossibleScore, Double percentage, Boolean passed, LocalDateTime attemptedAt) {
        this.id = id;
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.studentId = studentId;
        this.studentName = studentName;
        this.score = score;
        this.totalPossibleScore = totalPossibleScore;
        this.percentage = percentage;
        this.passed = passed;
        this.attemptedAt = attemptedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }

    public String getQuizTitle() { return quizTitle; }
    public void setQuizTitle(String quizTitle) { this.quizTitle = quizTitle; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

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
        private Long quizId;
        private String quizTitle;
        private Long courseId;
        private String courseTitle;
        private Long studentId;
        private String studentName;
        private Integer score;
        private Integer totalPossibleScore;
        private Double percentage;
        private Boolean passed;
        private LocalDateTime attemptedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder quizId(Long quizId) { this.quizId = quizId; return this; }
        public Builder quizTitle(String quizTitle) { this.quizTitle = quizTitle; return this; }
        public Builder courseId(Long courseId) { this.courseId = courseId; return this; }
        public Builder courseTitle(String courseTitle) { this.courseTitle = courseTitle; return this; }
        public Builder studentId(Long studentId) { this.studentId = studentId; return this; }
        public Builder studentName(String studentName) { this.studentName = studentName; return this; }
        public Builder score(Integer score) { this.score = score; return this; }
        public Builder totalPossibleScore(Integer totalPossibleScore) { this.totalPossibleScore = totalPossibleScore; return this; }
        public Builder percentage(Double percentage) { this.percentage = percentage; return this; }
        public Builder passed(Boolean passed) { this.passed = passed; return this; }
        public Builder attemptedAt(LocalDateTime attemptedAt) { this.attemptedAt = attemptedAt; return this; }

        public QuizAttemptDto build() {
            return new QuizAttemptDto(id, quizId, quizTitle, courseId, courseTitle, studentId, studentName, score, totalPossibleScore, percentage, passed, attemptedAt);
        }
    }
}
