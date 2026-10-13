package com.skillsphere.dto;

import java.util.List;

public class QuizQuestionDto {
    private Long id;
    private String questionText;
    private Integer marks;
    private Integer orderIndex;
    private List<QuizOptionDto> options;

    public QuizQuestionDto() {}

    public QuizQuestionDto(Long id, String questionText, Integer marks, Integer orderIndex, List<QuizOptionDto> options) {
        this.id = id;
        this.questionText = questionText;
        this.marks = marks;
        this.orderIndex = orderIndex;
        this.options = options;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public List<QuizOptionDto> getOptions() { return options; }
    public void setOptions(List<QuizOptionDto> options) { this.options = options; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String questionText;
        private Integer marks;
        private Integer orderIndex;
        private List<QuizOptionDto> options;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder questionText(String questionText) { this.questionText = questionText; return this; }
        public Builder marks(Integer marks) { this.marks = marks; return this; }
        public Builder orderIndex(Integer orderIndex) { this.orderIndex = orderIndex; return this; }
        public Builder options(List<QuizOptionDto> options) { this.options = options; return this; }

        public QuizQuestionDto build() {
            return new QuizQuestionDto(id, questionText, marks, orderIndex, options);
        }
    }
}
