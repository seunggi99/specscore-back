package imade.specscore.controller;

import imade.specscore.domain.Role;
import imade.specscore.domain.User;
import imade.specscore.dto.CourseAnswerRequest;
import imade.specscore.service.CourseAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CourseAnswerController {
    private final CourseAnswerService courseAnswerService;

    /* 질문에 대한 답변 생성 */
    @PostMapping("/instructor/course/detail/question/{questionId}/answer/create")
    @Operation(summary = "답변 생성", description = "질문에 대한 답변 생성함")
    public ResponseEntity<Long> createAnswer(@PathVariable("questionId") Long questionId, @AuthenticationPrincipal User user, @RequestBody CourseAnswerRequest request) {
       return ResponseEntity.ok(courseAnswerService.createAnswer(questionId, user, request));
    }
}
