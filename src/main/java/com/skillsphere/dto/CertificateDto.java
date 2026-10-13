package com.skillsphere.dto;

import java.time.LocalDateTime;

public class CertificateDto {
    private Long id;
    private String certificateNumber;
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Long courseId;
    private String courseTitle;
    private String courseCategory;
    private Long instructorId;
    private String instructorName;
    private String instructorTitle;
    private Long enrollmentId;
    private LocalDateTime issueDate;

    public CertificateDto() {}

    public CertificateDto(Long id, String certificateNumber, Long studentId, String studentName, String studentEmail, Long courseId, String courseTitle, String courseCategory, Long instructorId, String instructorName, String instructorTitle, Long enrollmentId, LocalDateTime issueDate) {
        this.id = id;
        this.certificateNumber = certificateNumber;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseCategory = courseCategory;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.instructorTitle = instructorTitle;
        this.enrollmentId = enrollmentId;
        this.issueDate = issueDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCertificateNumber() { return certificateNumber; }
    public void setCertificateNumber(String certificateNumber) { this.certificateNumber = certificateNumber; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getCourseCategory() { return courseCategory; }
    public void setCourseCategory(String courseCategory) { this.courseCategory = courseCategory; }

    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public String getInstructorTitle() { return instructorTitle; }
    public void setInstructorTitle(String instructorTitle) { this.instructorTitle = instructorTitle; }

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }

    public LocalDateTime getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String certificateNumber;
        private Long studentId;
        private String studentName;
        private String studentEmail;
        private Long courseId;
        private String courseTitle;
        private String courseCategory;
        private Long instructorId;
        private String instructorName;
        private String instructorTitle;
        private Long enrollmentId;
        private LocalDateTime issueDate;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder certificateNumber(String certificateNumber) { this.certificateNumber = certificateNumber; return this; }
        public Builder studentId(Long studentId) { this.studentId = studentId; return this; }
        public Builder studentName(String studentName) { this.studentName = studentName; return this; }
        public Builder studentEmail(String studentEmail) { this.studentEmail = studentEmail; return this; }
        public Builder courseId(Long courseId) { this.courseId = courseId; return this; }
        public Builder courseTitle(String courseTitle) { this.courseTitle = courseTitle; return this; }
        public Builder courseCategory(String courseCategory) { this.courseCategory = courseCategory; return this; }
        public Builder instructorId(Long instructorId) { this.instructorId = instructorId; return this; }
        public Builder instructorName(String instructorName) { this.instructorName = instructorName; return this; }
        public Builder instructorTitle(String instructorTitle) { this.instructorTitle = instructorTitle; return this; }
        public Builder enrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; return this; }
        public Builder issueDate(LocalDateTime issueDate) { this.issueDate = issueDate; return this; }

        public CertificateDto build() {
            return new CertificateDto(id, certificateNumber, studentId, studentName, studentEmail, courseId, courseTitle, courseCategory, instructorId, instructorName, instructorTitle, enrollmentId, issueDate);
        }
    }
}
