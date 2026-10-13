package com.skillsphere.dto;

public class AdminStatsDto {
    private long totalUsers;
    private long totalStudents;
    private long totalInstructors;
    private long totalCourses;
    private long totalEnrollments;
    private long completedCourses;
    private long totalCertificates;

    public AdminStatsDto() {}

    public AdminStatsDto(long totalUsers, long totalStudents, long totalInstructors, long totalCourses, long totalEnrollments, long completedCourses, long totalCertificates) {
        this.totalUsers = totalUsers;
        this.totalStudents = totalStudents;
        this.totalInstructors = totalInstructors;
        this.totalCourses = totalCourses;
        this.totalEnrollments = totalEnrollments;
        this.completedCourses = completedCourses;
        this.totalCertificates = totalCertificates;
    }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public long getTotalInstructors() { return totalInstructors; }
    public void setTotalInstructors(long totalInstructors) { this.totalInstructors = totalInstructors; }

    public long getTotalCourses() { return totalCourses; }
    public void setTotalCourses(long totalCourses) { this.totalCourses = totalCourses; }

    public long getTotalEnrollments() { return totalEnrollments; }
    public void setTotalEnrollments(long totalEnrollments) { this.totalEnrollments = totalEnrollments; }

    public long getCompletedCourses() { return completedCourses; }
    public void setCompletedCourses(long completedCourses) { this.completedCourses = completedCourses; }

    public long getTotalCertificates() { return totalCertificates; }
    public void setTotalCertificates(long totalCertificates) { this.totalCertificates = totalCertificates; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long totalUsers;
        private long totalStudents;
        private long totalInstructors;
        private long totalCourses;
        private long totalEnrollments;
        private long completedCourses;
        private long totalCertificates;

        public Builder totalUsers(long totalUsers) { this.totalUsers = totalUsers; return this; }
        public Builder totalStudents(long totalStudents) { this.totalStudents = totalStudents; return this; }
        public Builder totalInstructors(long totalInstructors) { this.totalInstructors = totalInstructors; return this; }
        public Builder totalCourses(long totalCourses) { this.totalCourses = totalCourses; return this; }
        public Builder totalEnrollments(long totalEnrollments) { this.totalEnrollments = totalEnrollments; return this; }
        public Builder completedCourses(long completedCourses) { this.completedCourses = completedCourses; return this; }
        public Builder totalCertificates(long totalCertificates) { this.totalCertificates = totalCertificates; return this; }

        public AdminStatsDto build() {
            return new AdminStatsDto(totalUsers, totalStudents, totalInstructors, totalCourses, totalEnrollments, completedCourses, totalCertificates);
        }
    }
}
