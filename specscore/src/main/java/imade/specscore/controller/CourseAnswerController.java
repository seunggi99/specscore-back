package imade.specscore.controller;

import imade.specscore.domain.Role;
import imade.specscore.domain.User;
import imade.specscore.dto.CourseAnswerRequest;
import imade.specscore.service.CourseAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseAnswerController {
    private final CourseAnswerService courseAnswerService;

    /* 질문에 대한 답변 생성 */
    @PostMapping("/detail/question/{questionId}/questionDetail/answer/create")
    public ResponseEntity<Long> createAnswer(@PathVariable Long questionId, @AuthenticationPrincipal User user, @RequestBody CourseAnswerRequest request) {
        if (!user.getRole().equals(Role.ROLE_INSTRUCTOR)) {return ResponseEntity.status(HttpStatus.FORBIDDEN).build();}
        return ResponseEntity.ok(courseAnswerService.createAnswer(questionId, user, request));
    }
}
