package com.skillsphere.dto;

import com.skillsphere.entity.MaterialType;
import jakarta.validation.constraints.NotBlank;

public class LearningMaterialRequest {
    private MaterialType type;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String contentUrlOrText;

    private Integer orderIndex;

    public LearningMaterialRequest() {}

    public MaterialType getType() { return type != null ? type : MaterialType.TEXT; }
    public void setType(MaterialType type) { this.type = type; }
    public void setMaterialType(MaterialType materialType) {
        if (this.type == null) this.type = materialType;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContentUrlOrText() {
        if (contentUrlOrText != null && !contentUrlOrText.isBlank()) return contentUrlOrText;
        return "";
    }
    public void setContentUrlOrText(String contentUrlOrText) { this.contentUrlOrText = contentUrlOrText; }
    public void setTextContent(String textContent) {
        if (textContent != null && !textContent.isBlank()) this.contentUrlOrText = textContent;
    }
    public void setContentUrl(String contentUrl) {
        if (contentUrl != null && !contentUrl.isBlank()) this.contentUrlOrText = contentUrl;
    }
    public void setContent(String content) {
        if (content != null && !content.isBlank()) this.contentUrlOrText = content;
    }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
}
