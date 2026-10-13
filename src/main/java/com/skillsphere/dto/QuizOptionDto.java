package com.skillsphere.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizOptionDto {
    private Long id;
    private String optionText;
    private Boolean isCorrect;

    public QuizOptionDto() {}

    public QuizOptionDto(Long id, String optionText, Boolean isCorrect) {
        this.id = id;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }

    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String optionText;
        private Boolean isCorrect;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder optionText(String optionText) { this.optionText = optionText; return this; }
        public Builder isCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; return this; }

        public QuizOptionDto build() {
            return new QuizOptionDto(id, optionText, isCorrect);
        }
    }
}
