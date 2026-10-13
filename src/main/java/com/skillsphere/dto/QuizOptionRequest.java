package com.skillsphere.dto;

import jakarta.validation.constraints.NotBlank;

public class QuizOptionRequest {
    @NotBlank(message = "Option text is required")
    private String optionText;

    private Boolean isCorrect = false;

    public QuizOptionRequest() {}

    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }

    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }
}
