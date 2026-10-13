package com.skillsphere.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public class QuizQuestionRequest {

    @NotBlank(message = "Question text is required")
    private String questionText;

    @Min(value = 1, message = "Marks must be at least 1")
    private Integer marks = 1;

    private Integer orderIndex = 1;

    @NotEmpty(message = "Options are required")
    @Size(min = 2, message = "Question must have at least 2 options")
    @Valid
    private List<QuizOptionRequest> options;

    public QuizQuestionRequest() {}

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public List<QuizOptionRequest> getOptions() { return options; }
    public void setOptions(List<QuizOptionRequest> options) { this.options = options; }
}
