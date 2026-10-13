package com.skillsphere.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class QuizRequest {
    @NotBlank(message = "Quiz title is required")
    private String title;

    private String description;

    @NotNull(message = "Passing score percentage is required")
    @Min(value = 1, message = "Passing score percentage must be at least 1")
    private Integer passingScorePercentage;

    private Integer timeLimitMinutes;

    @NotEmpty(message = "Quiz must contain at least one question")
    @Valid
    private List<QuizQuestionRequest> questions;

    public QuizRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getPassingScorePercentage() { return passingScorePercentage; }
    public void setPassingScorePercentage(Integer passingScorePercentage) { this.passingScorePercentage = passingScorePercentage; }

    public Integer getTimeLimitMinutes() { return timeLimitMinutes; }
    public void setTimeLimitMinutes(Integer timeLimitMinutes) { this.timeLimitMinutes = timeLimitMinutes; }

    public List<QuizQuestionRequest> getQuestions() { return questions; }
    public void setQuestions(List<QuizQuestionRequest> questions) { this.questions = questions; }
}
