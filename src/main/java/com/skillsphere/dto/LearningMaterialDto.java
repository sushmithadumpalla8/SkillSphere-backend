package com.skillsphere.dto;

import com.skillsphere.entity.MaterialType;

public class LearningMaterialDto {
    private Long id;
    private Long moduleId;
    private MaterialType type;
    private String title;
    private String description;
    private String contentUrlOrText;
    private Integer orderIndex;
    private Boolean completed;

    public LearningMaterialDto() {}

    public LearningMaterialDto(Long id, Long moduleId, MaterialType type, String title, String description, String contentUrlOrText, Integer orderIndex, Boolean completed) {
        this.id = id;
        this.moduleId = moduleId;
        this.type = type;
        this.title = title;
        this.description = description;
        this.contentUrlOrText = contentUrlOrText;
        this.orderIndex = orderIndex;
        this.completed = completed;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public MaterialType getType() { return type; }
    public void setType(MaterialType type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContentUrlOrText() { return contentUrlOrText; }
    public void setContentUrlOrText(String contentUrlOrText) { this.contentUrlOrText = contentUrlOrText; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long moduleId;
        private MaterialType type;
        private String title;
        private String description;
        private String contentUrlOrText;
        private Integer orderIndex;
        private Boolean completed;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder moduleId(Long moduleId) { this.moduleId = moduleId; return this; }
        public Builder type(MaterialType type) { this.type = type; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder contentUrlOrText(String contentUrlOrText) { this.contentUrlOrText = contentUrlOrText; return this; }
        public Builder orderIndex(Integer orderIndex) { this.orderIndex = orderIndex; return this; }
        public Builder completed(Boolean completed) { this.completed = completed; return this; }

        public LearningMaterialDto build() {
            return new LearningMaterialDto(id, moduleId, type, title, description, contentUrlOrText, orderIndex, completed);
        }
    }
}
