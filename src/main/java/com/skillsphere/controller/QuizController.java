package com.skillsphere.controller;

import com.skillsphere.dto.QuizAttemptDto;
import com.skillsphere.dto.QuizDto;
import com.skillsphere.dto.QuizRequest;
import com.skillsphere.dto.QuizSubmissionRequest;
import com.skillsphere.security.CustomUserDetails;
import com.skillsphere.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<QuizDto>> getQuizzesByCourseId(
            @PathVariable Long courseId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean showCorrect = userDetails != null && (
                userDetails.getUser().getRole().name().equals("INSTRUCTOR") ||
                userDetails.getUser().getRole().name().equals("ADMIN")
        );
        return ResponseEntity.ok(quizService.getQuizzesByCourseId(courseId, showCorrect));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getQuizById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean showCorrect = userDetails != null && (
                userDetails.getUser().getRole().name().equals("INSTRUCTOR") ||
                userDetails.getUser().getRole().name().equals("ADMIN")
        );
        return ResponseEntity.ok(quizService.getQuizById(id, showCorrect));
    }

    @PostMapping("/course/{courseId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<QuizDto> createQuiz(
            @PathVariable Long courseId,
            @Valid @RequestBody QuizRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isAdmin = userDetails.getUser().getRole().name().equals("ADMIN");
        QuizDto created = quizService.createQuiz(courseId, request, userDetails.getInstructorId(), isAdmin);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<QuizDto> updateQuiz(
            @PathVariable Long id,
            @Valid @RequestBody QuizRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isAdmin = userDetails.getUser().getRole().name().equals("ADMIN");
        QuizDto updated = quizService.updateQuiz(id, request, userDetails.getInstructorId(), isAdmin);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteQuiz(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        boolean isAdmin = userDetails.getUser().getRole().name().equals("ADMIN");
        quizService.deleteQuiz(id, userDetails.getInstructorId(), isAdmin);
        return ResponseEntity.noContent().build();
    }

    @PostMapping({"/{quizId}/submit", "/submit"})
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<QuizAttemptDto> submitQuiz(
            @PathVariable(required = false) Long quizId,
            @Valid @RequestBody QuizSubmissionRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        if (quizId != null) {
            request.setQuizId(quizId);
        }
        QuizAttemptDto attempt = quizService.submitQuiz(request, userDetails.getStudentId());
        return ResponseEntity.ok(attempt);
    }
}
