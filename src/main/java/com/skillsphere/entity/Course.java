package com.skillsphere.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseLevel level;

    private String duration;

    private Double price = 0.0;

    private Boolean isFree = true;

    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String learningObjectives;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseStatus status = CourseStatus.PUBLISHED;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("moduleNumber ASC")
    private List<CourseModule> modules = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> quizzes = new ArrayList<>();

    public Course() {}

    public Course(Long id, String title, String description, String category, CourseLevel level, String duration, Double price, Boolean isFree, String imageUrl, String learningObjectives, CourseStatus status, LocalDateTime createdAt, Instructor instructor, List<CourseModule> modules, List<Quiz> quizzes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.level = level;
        this.duration = duration;
        this.price = price != null ? price : 0.0;
        this.isFree = isFree != null ? isFree : true;
        this.imageUrl = imageUrl;
        this.learningObjectives = learningObjectives;
        this.status = status != null ? status : CourseStatus.PUBLISHED;
        this.createdAt = createdAt;
        this.instructor = instructor;
        if (modules != null) this.modules = modules;
        if (quizzes != null) this.quizzes = quizzes;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public CourseLevel getLevel() { return level; }
    public void setLevel(CourseLevel level) { this.level = level; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Boolean getIsFree() { return isFree; }
    public void setIsFree(Boolean isFree) { this.isFree = isFree; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getLearningObjectives() { return learningObjectives; }
    public void setLearningObjectives(String learningObjectives) { this.learningObjectives = learningObjectives; }

    public CourseStatus getStatus() { return status; }
    public void setStatus(CourseStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    public List<CourseModule> getModules() { return modules; }
    public void setModules(List<CourseModule> modules) { this.modules = modules; }

    public List<Quiz> getQuizzes() { return quizzes; }
    public void setQuizzes(List<Quiz> quizzes) { this.quizzes = quizzes; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private String category;
        private CourseLevel level;
        private String duration;
        private Double price = 0.0;
        private Boolean isFree = true;
        private String imageUrl;
        private String learningObjectives;
        private CourseStatus status = CourseStatus.PUBLISHED;
        private LocalDateTime createdAt;
        private Instructor instructor;
        private List<CourseModule> modules = new ArrayList<>();
        private List<Quiz> quizzes = new ArrayList<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder category(String category) { this.category = category; return this; }
        public Builder level(CourseLevel level) { this.level = level; return this; }
        public Builder duration(String duration) { this.duration = duration; return this; }
        public Builder price(Double price) { this.price = price; return this; }
        public Builder isFree(Boolean isFree) { this.isFree = isFree; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder learningObjectives(String learningObjectives) { this.learningObjectives = learningObjectives; return this; }
        public Builder status(CourseStatus status) { this.status = status; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder instructor(Instructor instructor) { this.instructor = instructor; return this; }
        public Builder modules(List<CourseModule> modules) { this.modules = modules; return this; }
        public Builder quizzes(List<Quiz> quizzes) { this.quizzes = quizzes; return this; }

        public Course build() {
            return new Course(id, title, description, category, level, duration, price, isFree, imageUrl, learningObjectives, status, createdAt, instructor, modules, quizzes);
        }
    }
}
