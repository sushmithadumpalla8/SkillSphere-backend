package com.skillsphere.dto;

import java.util.List;

public class QuizDto {
    private Long id;
    private Long courseId;
    private String courseTitle;
    private String title;
    private String description;
    private Integer passingScorePercentage;
    private Integer timeLimitMinutes;
    private List<QuizQuestionDto> questions;

    public QuizDto() {}

    public QuizDto(Long id, Long courseId, String courseTitle, String title, String description, Integer passingScorePercentage, Integer timeLimitMinutes, List<QuizQuestionDto> questions) {
        this.id = id;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.title = title;
        this.description = description;
        this.passingScorePercentage = passingScorePercentage;
        this.timeLimitMinutes = timeLimitMinutes;
        this.questions = questions;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getPassingScorePercentage() { return passingScorePercentage; }
    public void setPassingScorePercentage(Integer passingScorePercentage) { this.passingScorePercentage = passingScorePercentage; }

    public Integer getTimeLimitMinutes() { return timeLimitMinutes; }
    public void setTimeLimitMinutes(Integer timeLimitMinutes) { this.timeLimitMinutes = timeLimitMinutes; }

    public List<QuizQuestionDto> getQuestions() { return questions; }
    public void setQuestions(List<QuizQuestionDto> questions) { this.questions = questions; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long courseId;
        private String courseTitle;
        private String title;
        private String description;
        private Integer passingScorePercentage;
        private Integer timeLimitMinutes;
        private List<QuizQuestionDto> questions;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder courseId(Long courseId) { this.courseId = courseId; return this; }
        public Builder courseTitle(String courseTitle) { this.courseTitle = courseTitle; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder passingScorePercentage(Integer passingScorePercentage) { this.passingScorePercentage = passingScorePercentage; return this; }
        public Builder timeLimitMinutes(Integer timeLimitMinutes) { this.timeLimitMinutes = timeLimitMinutes; return this; }
        public Builder questions(List<QuizQuestionDto> questions) { this.questions = questions; return this; }

        public QuizDto build() {
            return new QuizDto(id, courseId, courseTitle, title, description, passingScorePercentage, timeLimitMinutes, questions);
        }
    }
}
