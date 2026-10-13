package com.skillsphere.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer passingScorePercentage = 50;

    private Integer timeLimitMinutes = 30;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<QuizQuestion> questions = new ArrayList<>();

    public Quiz() {}

    public Quiz(Long id, Course course, String title, String description, Integer passingScorePercentage, Integer timeLimitMinutes, List<QuizQuestion> questions) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.description = description;
        this.passingScorePercentage = passingScorePercentage != null ? passingScorePercentage : 50;
        this.timeLimitMinutes = timeLimitMinutes != null ? timeLimitMinutes : 30;
        if (questions != null) this.questions = questions;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getPassingScorePercentage() { return passingScorePercentage; }
    public void setPassingScorePercentage(Integer passingScorePercentage) { this.passingScorePercentage = passingScorePercentage; }

    public Integer getTimeLimitMinutes() { return timeLimitMinutes; }
    public void setTimeLimitMinutes(Integer timeLimitMinutes) { this.timeLimitMinutes = timeLimitMinutes; }

    public List<QuizQuestion> getQuestions() { return questions; }
    public void setQuestions(List<QuizQuestion> questions) { this.questions = questions; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Course course;
        private String title;
        private String description;
        private Integer passingScorePercentage = 50;
        private Integer timeLimitMinutes = 30;
        private List<QuizQuestion> questions = new ArrayList<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder course(Course course) { this.course = course; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder passingScorePercentage(Integer passingScorePercentage) { this.passingScorePercentage = passingScorePercentage; return this; }
        public Builder timeLimitMinutes(Integer timeLimitMinutes) { this.timeLimitMinutes = timeLimitMinutes; return this; }
        public Builder questions(List<QuizQuestion> questions) { this.questions = questions; return this; }

        public Quiz build() {
            return new Quiz(id, course, title, description, passingScorePercentage, timeLimitMinutes, questions);
        }
    }
}
