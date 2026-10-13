package com.skillsphere.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String certificateNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enrollment_id", nullable = false, unique = true)
    private Enrollment enrollment;

    @Column(nullable = false, updatable = false)
    private LocalDateTime issueDate;

    public Certificate() {}

    public Certificate(Long id, String certificateNumber, Student student, Course course, Instructor instructor, Enrollment enrollment, LocalDateTime issueDate) {
        this.id = id;
        this.certificateNumber = certificateNumber;
        this.student = student;
        this.course = course;
        this.instructor = instructor;
        this.enrollment = enrollment;
        this.issueDate = issueDate;
    }

    @PrePersist
    protected void onCreate() {
        this.issueDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCertificateNumber() { return certificateNumber; }
    public void setCertificateNumber(String certificateNumber) { this.certificateNumber = certificateNumber; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    public Enrollment getEnrollment() { return enrollment; }
    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }

    public LocalDateTime getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String certificateNumber;
        private Student student;
        private Course course;
        private Instructor instructor;
        private Enrollment enrollment;
        private LocalDateTime issueDate;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder certificateNumber(String certificateNumber) { this.certificateNumber = certificateNumber; return this; }
        public Builder student(Student student) { this.student = student; return this; }
        public Builder course(Course course) { this.course = course; return this; }
        public Builder instructor(Instructor instructor) { this.instructor = instructor; return this; }
        public Builder enrollment(Enrollment enrollment) { this.enrollment = enrollment; return this; }
        public Builder issueDate(LocalDateTime issueDate) { this.issueDate = issueDate; return this; }

        public Certificate build() {
            return new Certificate(id, certificateNumber, student, course, instructor, enrollment, issueDate);
        }
    }
}
