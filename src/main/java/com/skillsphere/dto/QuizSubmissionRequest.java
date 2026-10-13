package com.skillsphere.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

public class QuizSubmissionRequest {
    private Long quizId;

    @NotNull(message = "Answers are required")
    private Map<Long, Long> answers;

    public QuizSubmissionRequest() {}

    public QuizSubmissionRequest(Long quizId, Map<Long, Long> answers) {
        this.quizId = quizId;
        this.answers = answers;
    }

    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }

    public Map<Long, Long> getAnswers() { return answers; }
    public void setAnswers(Map<Long, Long> answers) { this.answers = answers; }
}
