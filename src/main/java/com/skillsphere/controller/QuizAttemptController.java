package com.skillsphere.controller;

import com.skillsphere.dto.QuizAttemptDto;
import com.skillsphere.security.CustomUserDetails;
import com.skillsphere.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-attempts")
public class QuizAttemptController {

    private final QuizService quizService;

    public QuizAttemptController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping({"/my", "/my-attempts"})
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<QuizAttemptDto>> getMyAttempts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(quizService.getStudentQuizAttempts(userDetails.getStudentId()));
    }

    @GetMapping("/instructor")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<List<QuizAttemptDto>> getInstructorAttempts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(quizService.getInstructorQuizAttempts(userDetails.getInstructorId()));
    }
}
