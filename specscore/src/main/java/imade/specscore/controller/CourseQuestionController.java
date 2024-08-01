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
@RequiredArgsConstructor
public class CourseQuestionController {
    private final CourseQuestionService courseQuestionService;

    /* 전체 질문 조회 (강의)*/
    @GetMapping("/user/course/{courseId}/detail/question/list")
    public ResponseEntity<List<CourseQuestionResponse>> getQuestionsByCourse(@PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(courseQuestionService.findQuestionsByCourseId(courseId));
    }

    /* 질문 조회 (강의 목차) */
    @GetMapping("/user/course/detail/{lectureId}/question/list")
    public ResponseEntity<List<CourseQuestionResponse>> getQuestionsByLecture(@PathVariable("lectureId") Long lectureId) {
        return ResponseEntity.ok(courseQuestionService.findQuestionsByLectureId(lectureId));
    }

    /* 질문 및 답변 (질문 상세) */
    @GetMapping("/user/course/detail/question/{questionId}/questionDetail")
    public ResponseEntity<CourseQuestionResponse.CourseQuestionWithAnswerResponse> getQuestionsWithAnswer(@PathVariable("questionId") Long questionId) {
        return ResponseEntity.ok(courseQuestionService.findQuestionWithAnswerByQuestionId(questionId));
    }

    /* 강의 목차에 대한 질문 생성 */
    @PostMapping("/user/course/detail/{lectureId}/question/create")
    public ResponseEntity<Long> createQuestion(@PathVariable("lectureId") Long lectureId, @AuthenticationPrincipal User user, @RequestBody CourseQuestionRequest request) {
        return ResponseEntity.ok(courseQuestionService.createCourseQuestion(lectureId, user, request));
    }
}
