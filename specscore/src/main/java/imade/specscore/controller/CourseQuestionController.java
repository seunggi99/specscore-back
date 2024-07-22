package imade.specscore.controller;

import imade.specscore.domain.CourseQuestion;
import imade.specscore.domain.Role;
import imade.specscore.domain.User;
import imade.specscore.dto.CourseQARequest;
import imade.specscore.dto.CourseQAResponse;
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

    /** Course에 대한 전체 질문 조회 (특정 강의 전체 질문) */ // -> 나중에 동적 조회로 구현 필요
    @GetMapping("/{courseId}/detail/question/list")
    public ResponseEntity<List<CourseQuestion>> getAllQuestionsByCourse(@PathVariable Long courseId) {
        List<CourseQuestion> questions = courseQuestionService.findAllQuestionsByCourse(courseId);
        return ResponseEntity.ok(questions);
    }

    /** Lecture에 대한 전체 질문 조회 (특정 강의 특정 목차에 대한 전체 질문) */
    @GetMapping("/detail/question/{lectureId}/list")
    public ResponseEntity<List<CourseQuestion>> getAllQuestionsByLecture(@PathVariable Long lectureId) {
        List<CourseQuestion> questions = courseQuestionService.findAllQuestionsByLecture(lectureId);
        return ResponseEntity.ok(questions);
    }

    /** 특정 질문 조회 (답변까지) */
    @GetMapping("/detail/question/{questionId}/questionDetail")
    public ResponseEntity<CourseQAResponse> getQuestionsWithAnswer(@PathVariable Long questionId) {
        CourseQuestion courseQuestion = courseQuestionService.findQuestionWithAnswer(questionId);
        CourseQAResponse questionsWithAnswers = new CourseQAResponse(courseQuestion);
        return ResponseEntity.ok(questionsWithAnswers);
    }

    /** Lecture에 대한 질문 생성 */
    @PostMapping("/detail/question/{lectureId}/create")
    public ResponseEntity<CourseQuestion> createQuestion(@PathVariable Long lectureId, @AuthenticationPrincipal User user, @RequestBody CourseQARequest request) {
        if (!user.getRole().equals(Role.ROLE_USER)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        CourseQuestion question = courseQuestionService.createQuestion(lectureId, user, request);
        return ResponseEntity.ok(question);
    }

}
