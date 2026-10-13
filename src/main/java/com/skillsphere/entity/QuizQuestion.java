package com.skillsphere.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String questionText;

    private Integer marks = 1;

    private Integer orderIndex = 1;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizOption> options = new ArrayList<>();

    public QuizQuestion() {}

    public QuizQuestion(Long id, Quiz quiz, String questionText, Integer marks, Integer orderIndex, List<QuizOption> options) {
        this.id = id;
        this.quiz = quiz;
        this.questionText = questionText;
        this.marks = marks != null ? marks : 1;
        this.orderIndex = orderIndex != null ? orderIndex : 1;
        if (options != null) this.options = options;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public List<QuizOption> getOptions() { return options; }
    public void setOptions(List<QuizOption> options) { this.options = options; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Quiz quiz;
        private String questionText;
        private Integer marks = 1;
        private Integer orderIndex = 1;
        private List<QuizOption> options = new ArrayList<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder quiz(Quiz quiz) { this.quiz = quiz; return this; }
        public Builder questionText(String questionText) { this.questionText = questionText; return this; }
        public Builder marks(Integer marks) { this.marks = marks; return this; }
        public Builder orderIndex(Integer orderIndex) { this.orderIndex = orderIndex; return this; }
        public Builder options(List<QuizOption> options) { this.options = options; return this; }

        public QuizQuestion build() {
            return new QuizQuestion(id, quiz, questionText, marks, orderIndex, options);
        }
    }
}
