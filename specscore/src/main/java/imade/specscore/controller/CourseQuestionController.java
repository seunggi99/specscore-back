package imade.specscore.controller;

import imade.specscore.domain.Role;
import imade.specscore.domain.User;
import imade.specscore.dto.CourseQuestionRequest;
import imade.specscore.dto.CourseQuestionResponse;
import imade.specscore.service.CourseQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseQuestionController {
    private final CourseQuestionService courseQuestionService;

    /* 전체 질문 조회 (강의)*/
    @GetMapping("/{courseId}/detail/question/list")
    public ResponseEntity<List<CourseQuestionResponse>> getQuestionsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseQuestionService.findQuestionsByCourseId(courseId));
    }

    /* 전체 질문 조회 (강의 목차) */
    @GetMapping("/detail/question/{lectureId}/list")
    public ResponseEntity<List<CourseQuestionResponse>> getQuestionsByLecture(@PathVariable Long lectureId) {
        return ResponseEntity.ok(courseQuestionService.findQuestionsByLectureId(lectureId));
    }

    /* 질문 및 답변 (질문 상세)*/
    @GetMapping("/detail/question/{questionId}/questionDetail")
    public ResponseEntity<CourseQuestionResponse.CourseQuestionWithAnswerResponse> getQuestionsWithAnswer(@PathVariable Long questionId) {
        return ResponseEntity.ok(courseQuestionService.findQuestionWithAnswerByQuestionId(questionId));
    }

    /* 강의 목차에 대한 질문 생성 */
    @PostMapping("/detail/question/{lectureId}/create")
    public ResponseEntity<Long> createQuestion(@PathVariable Long lectureId, @AuthenticationPrincipal User user, @RequestBody CourseQuestionRequest request) {
        if (!user.getRole().equals(Role.ROLE_USER)) {return ResponseEntity.status(HttpStatus.FORBIDDEN).build();}
        return ResponseEntity.ok(courseQuestionService.createCourseQuestion(lectureId, user, request));
    }
}
