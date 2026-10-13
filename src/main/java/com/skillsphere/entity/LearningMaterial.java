package com.skillsphere.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "learning_materials")
public class LearningMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private CourseModule module;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaterialType type;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contentUrlOrText;

    private Integer orderIndex = 1;

    public LearningMaterial() {}

    public LearningMaterial(Long id, CourseModule module, MaterialType type, String title, String description, String contentUrlOrText, Integer orderIndex) {
        this.id = id;
        this.module = module;
        this.type = type;
        this.title = title;
        this.description = description;
        this.contentUrlOrText = contentUrlOrText;
        this.orderIndex = orderIndex != null ? orderIndex : 1;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CourseModule getModule() { return module; }
    public void setModule(CourseModule module) { this.module = module; }

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

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private CourseModule module;
        private MaterialType type;
        private String title;
        private String description;
        private String contentUrlOrText;
        private Integer orderIndex = 1;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder module(CourseModule module) { this.module = module; return this; }
        public Builder type(MaterialType type) { this.type = type; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder contentUrlOrText(String contentUrlOrText) { this.contentUrlOrText = contentUrlOrText; return this; }
        public Builder orderIndex(Integer orderIndex) { this.orderIndex = orderIndex; return this; }

        public LearningMaterial build() {
            return new LearningMaterial(id, module, type, title, description, contentUrlOrText, orderIndex);
        }
    }
}
