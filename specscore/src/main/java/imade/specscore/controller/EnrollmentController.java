package imade.specscore.controller;

import imade.specscore.domain.Course;
import imade.specscore.domain.Enrollment;
import imade.specscore.domain.User;
import imade.specscore.dto.CourseResponse;
import imade.specscore.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    /* 수강 등록 생성 */
    @PostMapping("/user/course/{courseId}/detail/enrollment")
    @Operation(summary = "수강 등록 생성", description = "수강 등록을 진행함")
    public ResponseEntity<Long> createEnrollment(@PathVariable("courseId") Long courseId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(enrollmentService.createEnrollment(courseId, user));
    }

    /* 수강 목록 조회 */
    @GetMapping("/user/myPage/myCourse")
    @Operation(summary = "수강 목록 조회", description = "수강 등록한 수강 과목들을 조회함")
    public ResponseEntity<List<CourseResponse>> getCoursesByUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(enrollmentService.findCoursesByUser(user));
    }
}
