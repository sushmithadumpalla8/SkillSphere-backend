package com.skillsphere.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_options")
public class QuizOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuizQuestion question;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String optionText;

    @Column(nullable = false)
    private Boolean isCorrect = false;

    public QuizOption() {}

    public QuizOption(Long id, QuizQuestion question, String optionText, Boolean isCorrect) {
        this.id = id;
        this.question = question;
        this.optionText = optionText;
        this.isCorrect = isCorrect != null ? isCorrect : false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public QuizQuestion getQuestion() { return question; }
    public void setQuestion(QuizQuestion question) { this.question = question; }

    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }

    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private QuizQuestion question;
        private String optionText;
        private Boolean isCorrect = false;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder question(QuizQuestion question) { this.question = question; return this; }
        public Builder optionText(String optionText) { this.optionText = optionText; return this; }
        public Builder isCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; return this; }

        public QuizOption build() {
            return new QuizOption(id, question, optionText, isCorrect);
        }
    }
}
