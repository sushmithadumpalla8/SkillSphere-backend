package com.skillsphere.dto;

import java.util.List;
import java.util.Map;

public class AdminReportsDto {
    private Map<String, Long> enrollmentsPerCourse;
    private Map<String, Long> categoryWiseCourses;
    private Double completionRatePercentage;
    private List<CourseDto> topCourses;

    public AdminReportsDto() {}

    public AdminReportsDto(Map<String, Long> enrollmentsPerCourse, Map<String, Long> categoryWiseCourses, Double completionRatePercentage, List<CourseDto> topCourses) {
        this.enrollmentsPerCourse = enrollmentsPerCourse;
        this.categoryWiseCourses = categoryWiseCourses;
        this.completionRatePercentage = completionRatePercentage;
        this.topCourses = topCourses;
    }

    public Map<String, Long> getEnrollmentsPerCourse() { return enrollmentsPerCourse; }
    public void setEnrollmentsPerCourse(Map<String, Long> enrollmentsPerCourse) { this.enrollmentsPerCourse = enrollmentsPerCourse; }

    public Map<String, Long> getCategoryWiseCourses() { return categoryWiseCourses; }
    public void setCategoryWiseCourses(Map<String, Long> categoryWiseCourses) { this.categoryWiseCourses = categoryWiseCourses; }

    public Double getCompletionRatePercentage() { return completionRatePercentage; }
    public void setCompletionRatePercentage(Double completionRatePercentage) { this.completionRatePercentage = completionRatePercentage; }

    public List<CourseDto> getTopCourses() { return topCourses; }
    public void setTopCourses(List<CourseDto> topCourses) { this.topCourses = topCourses; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Map<String, Long> enrollmentsPerCourse;
        private Map<String, Long> categoryWiseCourses;
        private Double completionRatePercentage;
        private List<CourseDto> topCourses;

        public Builder enrollmentsPerCourse(Map<String, Long> enrollmentsPerCourse) { this.enrollmentsPerCourse = enrollmentsPerCourse; return this; }
        public Builder categoryWiseCourses(Map<String, Long> categoryWiseCourses) { this.categoryWiseCourses = categoryWiseCourses; return this; }
        public Builder completionRatePercentage(Double completionRatePercentage) { this.completionRatePercentage = completionRatePercentage; return this; }
        public Builder topCourses(List<CourseDto> topCourses) { this.topCourses = topCourses; return this; }

        public AdminReportsDto build() {
            return new AdminReportsDto(enrollmentsPerCourse, categoryWiseCourses, completionRatePercentage, topCourses);
        }
    }
}
