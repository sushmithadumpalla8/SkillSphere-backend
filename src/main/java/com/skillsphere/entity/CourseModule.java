package com.skillsphere.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course_modules")
public class CourseModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private Integer moduleNumber;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<LearningMaterial> materials = new ArrayList<>();

    public CourseModule() {}

    public CourseModule(Long id, Course course, Integer moduleNumber, String title, String description, List<LearningMaterial> materials) {
        this.id = id;
        this.course = course;
        this.moduleNumber = moduleNumber;
        this.title = title;
        this.description = description;
        if (materials != null) this.materials = materials;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Integer getModuleNumber() { return moduleNumber; }
    public void setModuleNumber(Integer moduleNumber) { this.moduleNumber = moduleNumber; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<LearningMaterial> getMaterials() { return materials; }
    public void setMaterials(List<LearningMaterial> materials) { this.materials = materials; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Course course;
        private Integer moduleNumber;
        private String title;
        private String description;
        private List<LearningMaterial> materials = new ArrayList<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder course(Course course) { this.course = course; return this; }
        public Builder moduleNumber(Integer moduleNumber) { this.moduleNumber = moduleNumber; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder materials(List<LearningMaterial> materials) { this.materials = materials; return this; }

        public CourseModule build() {
            return new CourseModule(id, course, moduleNumber, title, description, materials);
        }
    }
}
