package imade.specscore.controller;

import imade.specscore.domain.CourseAnswer;
import imade.specscore.domain.Role;
import imade.specscore.domain.User;
import imade.specscore.dto.CourseQARequest;
import imade.specscore.service.CourseAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("course/detail/lecture")
@RequiredArgsConstructor
public class CourseAnswerController {
    private final CourseAnswerService courseAnswerService;
    /** 답변 생성 */
    @PostMapping("/{questionId}/answer/create")
    public ResponseEntity<CourseAnswer> createAnswer(@PathVariable Long questionId, @AuthenticationPrincipal User user, @RequestBody CourseQARequest request) {
        if (!user.getRole().equals(Role.ROLE_INSTRUCTOR)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        CourseAnswer answer = courseAnswerService.createAnswer(questionId, user, request);
        return ResponseEntity.ok(answer);
    }
}
