package com.skillsphere.service;

import com.skillsphere.dto.*;
import com.skillsphere.entity.*;
import com.skillsphere.exception.BadRequestException;
import com.skillsphere.exception.ForbiddenException;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizQuestionRepository questionRepository;
    private final QuizOptionRepository optionRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CertificateService certificateService;

    public QuizService(QuizRepository quizRepository,
                       QuizQuestionRepository questionRepository,
                       QuizOptionRepository optionRepository,
                       QuizAttemptRepository quizAttemptRepository,
                       CourseRepository courseRepository,
                       StudentRepository studentRepository,
                       EnrollmentRepository enrollmentRepository,
                       CertificateService certificateService) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.certificateService = certificateService;
    }

    public List<QuizDto> getQuizzesByCourseId(Long courseId, boolean showCorrectAnswers) {
        return quizRepository.findByCourseId(courseId).stream()
                .map(quiz -> mapToDto(quiz, showCorrectAnswers))
                .collect(Collectors.toList());
    }

    public QuizDto getQuizById(Long id, boolean showCorrectAnswers) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));
        return mapToDto(quiz, showCorrectAnswers);
    }

    @Transactional
    public QuizDto createQuiz(Long courseId, QuizRequest request, Long instructorId, boolean isAdmin) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        if (!isAdmin && !course.getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not have permission to add a quiz to this course");
        }

        validateQuizRequest(request);

        Quiz quiz = Quiz.builder()
                .course(course)
                .title(request.getTitle())
                .description(request.getDescription())
                .passingScorePercentage(request.getPassingScorePercentage() != null ? request.getPassingScorePercentage() : 50)
                .timeLimitMinutes(request.getTimeLimitMinutes() != null ? request.getTimeLimitMinutes() : 30)
                .build();

        Quiz savedQuiz = quizRepository.save(quiz);

        int qIndex = 1;
        for (QuizQuestionRequest qReq : request.getQuestions()) {
            QuizQuestion question = QuizQuestion.builder()
                    .quiz(savedQuiz)
                    .questionText(qReq.getQuestionText())
                    .marks(qReq.getMarks() != null ? qReq.getMarks() : 1)
                    .orderIndex(qReq.getOrderIndex() != null ? qReq.getOrderIndex() : qIndex++)
                    .build();
            QuizQuestion savedQuestion = questionRepository.save(question);

            List<QuizOption> options = new ArrayList<>();
            for (QuizOptionRequest oReq : qReq.getOptions()) {
                options.add(QuizOption.builder()
                        .question(savedQuestion)
                        .optionText(oReq.getOptionText())
                        .isCorrect(oReq.getIsCorrect() != null && oReq.getIsCorrect())
                        .build());
            }
            optionRepository.saveAll(options);
        }

        return mapToDto(savedQuiz, true);
    }

    @Transactional
    public QuizDto updateQuiz(Long id, QuizRequest request, Long instructorId, boolean isAdmin) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));

        if (!isAdmin && !quiz.getCourse().getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not have permission to update this quiz");
        }

        validateQuizRequest(request);

        quiz.setTitle(request.getTitle());
        if (request.getDescription() != null) quiz.setDescription(request.getDescription());
        if (request.getPassingScorePercentage() != null) quiz.setPassingScorePercentage(request.getPassingScorePercentage());
        if (request.getTimeLimitMinutes() != null) quiz.setTimeLimitMinutes(request.getTimeLimitMinutes());

        // Clear existing questions and recreate
        quiz.getQuestions().clear();
        quizRepository.save(quiz);

        int qIndex = 1;
        for (QuizQuestionRequest qReq : request.getQuestions()) {
            QuizQuestion question = QuizQuestion.builder()
                    .quiz(quiz)
                    .questionText(qReq.getQuestionText())
                    .marks(qReq.getMarks() != null ? qReq.getMarks() : 1)
                    .orderIndex(qReq.getOrderIndex() != null ? qReq.getOrderIndex() : qIndex++)
                    .build();
            QuizQuestion savedQuestion = questionRepository.save(question);

            List<QuizOption> options = new ArrayList<>();
            for (QuizOptionRequest oReq : qReq.getOptions()) {
                options.add(QuizOption.builder()
                        .question(savedQuestion)
                        .optionText(oReq.getOptionText())
                        .isCorrect(oReq.getIsCorrect() != null && oReq.getIsCorrect())
                        .build());
            }
            optionRepository.saveAll(options);
        }

        return mapToDto(quiz, true);
    }

    @Transactional
    public void deleteQuiz(Long id, Long instructorId, boolean isAdmin) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));

        if (!isAdmin && !quiz.getCourse().getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not have permission to delete this quiz");
        }

        quizRepository.delete(quiz);
    }

    @Transactional
    public QuizAttemptDto submitQuiz(QuizSubmissionRequest request, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + request.getQuizId()));

        Long courseId = quiz.getCourse().getId();

        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ForbiddenException("You must be enrolled in this course to take the quiz"));

        List<QuizQuestion> questions = questionRepository.findByQuizIdOrderByOrderIndexAsc(quiz.getId());
        if (questions.isEmpty()) {
            throw new BadRequestException("Quiz has no questions to evaluate");
        }

        int score = 0;
        int totalPossible = 0;

        for (QuizQuestion q : questions) {
            int qMarks = q.getMarks() != null ? q.getMarks() : 1;
            totalPossible += qMarks;

            Long selectedOptionId = request.getAnswers().get(q.getId());
            if (selectedOptionId != null) {
                QuizOption selectedOption = optionRepository.findById(selectedOptionId).orElse(null);
                if (selectedOption != null && selectedOption.getQuestion().getId().equals(q.getId()) && Boolean.TRUE.equals(selectedOption.getIsCorrect())) {
                    score += qMarks;
                }
            }
        }

        double percentage = totalPossible > 0 ? ((double) score / totalPossible) * 100.0 : 0.0;
        boolean passed = percentage >= quiz.getPassingScorePercentage();

        QuizAttempt attempt = QuizAttempt.builder()
                .student(student)
                .quiz(quiz)
                .score(score)
                .totalPossibleScore(totalPossible)
                .percentage(Math.round(percentage * 10.0) / 10.0)
                .passed(passed)
                .attemptedAt(LocalDateTime.now())
                .build();

        QuizAttempt savedAttempt = quizAttemptRepository.save(attempt);

        // If passed AND enrollment materials progress is 100%, mark enrollment as completed & generate certificate
        if (passed && enrollment.getProgressPercentage() >= 100 && !enrollment.getCompleted()) {
            enrollment.setCompleted(true);
            enrollment.setCompletedAt(LocalDateTime.now());
            enrollmentRepository.save(enrollment);
            certificateService.generateCertificateForEnrollment(enrollment);
        }

        return mapToAttemptDto(savedAttempt);
    }

    public List<QuizAttemptDto> getStudentQuizAttempts(Long studentId) {
        return quizAttemptRepository.findByStudentId(studentId).stream()
                .map(this::mapToAttemptDto)
                .collect(Collectors.toList());
    }

    public List<QuizAttemptDto> getInstructorQuizAttempts(Long instructorId) {
        return quizAttemptRepository.findByQuizCourseInstructorId(instructorId).stream()
                .map(this::mapToAttemptDto)
                .collect(Collectors.toList());
    }

    private void validateQuizRequest(QuizRequest request) {
        if (request.getQuestions() == null || request.getQuestions().isEmpty()) {
            throw new BadRequestException("Quiz must contain at least one question");
        }
        for (int i = 0; i < request.getQuestions().size(); i++) {
            QuizQuestionRequest q = request.getQuestions().get(i);
            if (q.getOptions() == null || q.getOptions().size() < 2) {
                throw new BadRequestException("Question " + (i + 1) + " must have at least 2 options");
            }
            long correctCount = q.getOptions().stream()
                    .filter(o -> Boolean.TRUE.equals(o.getIsCorrect()))
                    .count();
            if (correctCount != 1) {
                throw new BadRequestException("Question " + (i + 1) + " must have exactly ONE correct option (found " + correctCount + ")");
            }
        }
    }

    public QuizDto mapToDto(Quiz quiz, boolean showCorrectAnswers) {
        List<QuizQuestionDto> questions = quiz.getQuestions() != null
                ? quiz.getQuestions().stream().map(q -> QuizQuestionDto.builder()
                        .id(q.getId())
                        .questionText(q.getQuestionText())
                        .marks(q.getMarks())
                        .orderIndex(q.getOrderIndex())
                        .options(q.getOptions() != null ? q.getOptions().stream().map(o -> QuizOptionDto.builder()
                                .id(o.getId())
                                .optionText(o.getOptionText())
                                .isCorrect(showCorrectAnswers ? o.getIsCorrect() : null)
                                .build()).collect(Collectors.toList()) : List.of())
                        .build()).collect(Collectors.toList())
                : List.of();

        return QuizDto.builder()
                .id(quiz.getId())
                .courseId(quiz.getCourse().getId())
                .courseTitle(quiz.getCourse().getTitle())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .passingScorePercentage(quiz.getPassingScorePercentage())
                .timeLimitMinutes(quiz.getTimeLimitMinutes())
                .questions(questions)
                .build();
    }

    public QuizAttemptDto mapToAttemptDto(QuizAttempt attempt) {
        return QuizAttemptDto.builder()
                .id(attempt.getId())
                .quizId(attempt.getQuiz().getId())
                .quizTitle(attempt.getQuiz().getTitle())
                .courseId(attempt.getQuiz().getCourse().getId())
                .courseTitle(attempt.getQuiz().getCourse().getTitle())
                .studentId(attempt.getStudent().getId())
                .studentName(attempt.getStudent().getUser().getFullName())
                .score(attempt.getScore())
                .totalPossibleScore(attempt.getTotalPossibleScore())
                .percentage(attempt.getPercentage())
                .passed(attempt.getPassed())
                .attemptedAt(attempt.getAttemptedAt())
                .build();
    }
}
