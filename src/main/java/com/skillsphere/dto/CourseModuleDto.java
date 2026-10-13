package com.skillsphere.dto;

import java.util.List;

public class CourseModuleDto {
    private Long id;
    private Long courseId;
    private Integer moduleNumber;
    private String title;
    private String description;
    private List<LearningMaterialDto> materials;

    public CourseModuleDto() {}

    public CourseModuleDto(Long id, Long courseId, Integer moduleNumber, String title, String description, List<LearningMaterialDto> materials) {
        this.id = id;
        this.courseId = courseId;
        this.moduleNumber = moduleNumber;
        this.title = title;
        this.description = description;
        this.materials = materials;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public Integer getModuleNumber() { return moduleNumber; }
    public void setModuleNumber(Integer moduleNumber) { this.moduleNumber = moduleNumber; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<LearningMaterialDto> getMaterials() { return materials; }
    public void setMaterials(List<LearningMaterialDto> materials) { this.materials = materials; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long courseId;
        private Integer moduleNumber;
        private String title;
        private String description;
        private List<LearningMaterialDto> materials;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder courseId(Long courseId) { this.courseId = courseId; return this; }
        public Builder moduleNumber(Integer moduleNumber) { this.moduleNumber = moduleNumber; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder materials(List<LearningMaterialDto> materials) { this.materials = materials; return this; }

        public CourseModuleDto build() {
            return new CourseModuleDto(id, courseId, moduleNumber, title, description, materials);
        }
    }
}
