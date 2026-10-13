package com.skillsphere.dto;

import java.time.LocalDateTime;

public class EnrollmentDto {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseTitle;
    private String courseCategory;
    private String courseImageUrl;
    private String instructorName;
    private LocalDateTime enrolledAt;
    private Integer progressPercentage;
    private Boolean completed;
    private LocalDateTime completedAt;
    private Long certificateId;
    private String certificateNumber;
    private String status;
    private Long completedMaterialsCount;
    private Long totalMaterialsCount;

    public EnrollmentDto() {}

    public EnrollmentDto(Long id, Long studentId, String studentName, Long courseId, String courseTitle, String courseCategory, String courseImageUrl, String instructorName, LocalDateTime enrolledAt, Integer progressPercentage, Boolean completed, LocalDateTime completedAt, Long certificateId, String certificateNumber) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseCategory = courseCategory;
        this.courseImageUrl = courseImageUrl;
        this.instructorName = instructorName;
        this.enrolledAt = enrolledAt;
        this.progressPercentage = progressPercentage;
        this.completed = completed;
        this.completedAt = completedAt;
        this.certificateId = certificateId;
        this.certificateNumber = certificateNumber;
        this.status = (completed != null && completed) || (progressPercentage != null && progressPercentage >= 100) ? "COMPLETED" : "IN_PROGRESS";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getCourseCategory() { return courseCategory; }
    public void setCourseCategory(String courseCategory) { this.courseCategory = courseCategory; }

    public String getCourseImageUrl() { return courseImageUrl; }
    public void setCourseImageUrl(String courseImageUrl) { this.courseImageUrl = courseImageUrl; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }

    public Integer getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(Integer progressPercentage) { this.progressPercentage = progressPercentage; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { 
        this.completed = completed; 
        this.status = (completed != null && completed) || (this.progressPercentage != null && this.progressPercentage >= 100) ? "COMPLETED" : "IN_PROGRESS";
    }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public Long getCertificateId() { return certificateId; }
    public void setCertificateId(Long certificateId) { this.certificateId = certificateId; }

    public String getCertificateNumber() { return certificateNumber; }
    public void setCertificateNumber(String certificateNumber) { this.certificateNumber = certificateNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getCompletedMaterialsCount() { return completedMaterialsCount; }
    public void setCompletedMaterialsCount(Long completedMaterialsCount) { this.completedMaterialsCount = completedMaterialsCount; }

    public Long getTotalMaterialsCount() { return totalMaterialsCount; }
    public void setTotalMaterialsCount(Long totalMaterialsCount) { this.totalMaterialsCount = totalMaterialsCount; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long studentId;
        private String studentName;
        private Long courseId;
        private String courseTitle;
        private String courseCategory;
        private String courseImageUrl;
        private String instructorName;
        private LocalDateTime enrolledAt;
        private Integer progressPercentage;
        private Boolean completed;
        private LocalDateTime completedAt;
        private Long certificateId;
        private String certificateNumber;
        private String status;
        private Long completedMaterialsCount;
        private Long totalMaterialsCount;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder studentId(Long studentId) { this.studentId = studentId; return this; }
        public Builder studentName(String studentName) { this.studentName = studentName; return this; }
        public Builder courseId(Long courseId) { this.courseId = courseId; return this; }
        public Builder courseTitle(String courseTitle) { this.courseTitle = courseTitle; return this; }
        public Builder courseCategory(String courseCategory) { this.courseCategory = courseCategory; return this; }
        public Builder courseImageUrl(String courseImageUrl) { this.courseImageUrl = courseImageUrl; return this; }
        public Builder instructorName(String instructorName) { this.instructorName = instructorName; return this; }
        public Builder enrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; return this; }
        public Builder progressPercentage(Integer progressPercentage) { this.progressPercentage = progressPercentage; return this; }
        public Builder completed(Boolean completed) { this.completed = completed; return this; }
        public Builder completedAt(LocalDateTime completedAt) { this.completedAt = completedAt; return this; }
        public Builder certificateId(Long certificateId) { this.certificateId = certificateId; return this; }
        public Builder certificateNumber(String certificateNumber) { this.certificateNumber = certificateNumber; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder completedMaterialsCount(Long count) { this.completedMaterialsCount = count; return this; }
        public Builder totalMaterialsCount(Long count) { this.totalMaterialsCount = count; return this; }

        public EnrollmentDto build() {
            EnrollmentDto dto = new EnrollmentDto(id, studentId, studentName, courseId, courseTitle, courseCategory, courseImageUrl, instructorName, enrolledAt, progressPercentage, completed, completedAt, certificateId, certificateNumber);
            if (this.status != null) dto.setStatus(this.status);
            dto.setCompletedMaterialsCount(this.completedMaterialsCount);
            dto.setTotalMaterialsCount(this.totalMaterialsCount);
            return dto;
        }
    }
}
