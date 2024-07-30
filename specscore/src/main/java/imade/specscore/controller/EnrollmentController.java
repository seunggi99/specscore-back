package imade.specscore.controller;

import imade.specscore.domain.Course;
import imade.specscore.domain.Enrollment;
import imade.specscore.domain.User;
import imade.specscore.dto.CourseResponse;
import imade.specscore.service.EnrollmentService;
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
    @PostMapping("/course/{courseId}/detail/enrollment")
    public ResponseEntity<Long> createEnrollment(@PathVariable Long courseId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(enrollmentService.createEnrollment(courseId, user));
    }

    /* 수강 목록 조회 */
    @GetMapping("/myPage/myCourse")
    public ResponseEntity<List<CourseResponse>> getCoursesByUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(enrollmentService.findCoursesByUser(user));
    }
}
