package com.skillsphere.dto;

import com.skillsphere.entity.CourseLevel;
import com.skillsphere.entity.CourseStatus;
import java.time.LocalDateTime;

public class CourseDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private CourseLevel level;
    private String duration;
    private Double price;
    private Boolean isFree;
    private String imageUrl;
    private String learningObjectives;
    private CourseStatus status;
    private LocalDateTime createdAt;
    private Long instructorId;
    private String instructorName;
    private String instructorTitle;
    private String instructorAvatarUrl;
    private int moduleCount;
    private int enrolledStudentCount;
    private Long quizId;

    public CourseDto() {}

    public CourseDto(Long id, String title, String description, String category, CourseLevel level, String duration, Double price, Boolean isFree, String imageUrl, String learningObjectives, CourseStatus status, LocalDateTime createdAt, Long instructorId, String instructorName, String instructorTitle, String instructorAvatarUrl, int moduleCount, int enrolledStudentCount, Long quizId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.level = level;
        this.duration = duration;
        this.price = price;
        this.isFree = isFree;
        this.imageUrl = imageUrl;
        this.learningObjectives = learningObjectives;
        this.status = status;
        this.createdAt = createdAt;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.instructorTitle = instructorTitle;
        this.instructorAvatarUrl = instructorAvatarUrl;
        this.moduleCount = moduleCount;
        this.enrolledStudentCount = enrolledStudentCount;
        this.quizId = quizId;
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

    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public String getInstructorTitle() { return instructorTitle; }
    public void setInstructorTitle(String instructorTitle) { this.instructorTitle = instructorTitle; }

    public String getInstructorAvatarUrl() { return instructorAvatarUrl; }
    public void setInstructorAvatarUrl(String instructorAvatarUrl) { this.instructorAvatarUrl = instructorAvatarUrl; }

    public int getModuleCount() { return moduleCount; }
    public void setModuleCount(int moduleCount) { this.moduleCount = moduleCount; }

    public int getEnrolledStudentCount() { return enrolledStudentCount; }
    public void setEnrolledStudentCount(int enrolledStudentCount) { this.enrolledStudentCount = enrolledStudentCount; }

    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private String category;
        private CourseLevel level;
        private String duration;
        private Double price;
        private Boolean isFree;
        private String imageUrl;
        private String learningObjectives;
        private CourseStatus status;
        private LocalDateTime createdAt;
        private Long instructorId;
        private String instructorName;
        private String instructorTitle;
        private String instructorAvatarUrl;
        private int moduleCount;
        private int enrolledStudentCount;
        private Long quizId;

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
        public Builder instructorId(Long instructorId) { this.instructorId = instructorId; return this; }
        public Builder instructorName(String instructorName) { this.instructorName = instructorName; return this; }
        public Builder instructorTitle(String instructorTitle) { this.instructorTitle = instructorTitle; return this; }
        public Builder instructorAvatarUrl(String instructorAvatarUrl) { this.instructorAvatarUrl = instructorAvatarUrl; return this; }
        public Builder moduleCount(int moduleCount) { this.moduleCount = moduleCount; return this; }
        public Builder enrolledStudentCount(int enrolledStudentCount) { this.enrolledStudentCount = enrolledStudentCount; return this; }
        public Builder quizId(Long quizId) { this.quizId = quizId; return this; }

        public CourseDto build() {
            return new CourseDto(id, title, description, category, level, duration, price, isFree, imageUrl, learningObjectives, status, createdAt, instructorId, instructorName, instructorTitle, instructorAvatarUrl, moduleCount, enrolledStudentCount, quizId);
        }
    }
}
