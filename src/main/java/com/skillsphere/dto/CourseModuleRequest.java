package com.skillsphere.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseModuleRequest {
    @NotNull(message = "Module number is required")
    private Integer moduleNumber;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    public CourseModuleRequest() {}

    public CourseModuleRequest(Integer moduleNumber, String title, String description) {
        this.moduleNumber = moduleNumber;
        this.title = title;
        this.description = description;
    }

    public Integer getModuleNumber() { return moduleNumber; }
    public void setModuleNumber(Integer moduleNumber) { this.moduleNumber = moduleNumber; }

    public void setOrderIndex(Integer orderIndex) {
        if (this.moduleNumber == null) {
            this.moduleNumber = orderIndex;
        }
    }

    public Integer getOrderIndex() {
        return this.moduleNumber;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
