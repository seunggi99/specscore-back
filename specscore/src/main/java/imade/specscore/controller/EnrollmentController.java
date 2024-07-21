package imade.specscore.controller;

import imade.specscore.domain.Course;
import imade.specscore.domain.Enrollment;
import imade.specscore.domain.User;
import imade.specscore.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    /** 사용자가 등록한 강의 목록 조회 */
    @GetMapping("myPage/myCourse")
    public ResponseEntity<List<Course>> getMyCourses(@AuthenticationPrincipal User user) {
        List<Course> courses = enrollmentService.findCoursesByUser(user);
        return ResponseEntity.ok(courses);
    }

    /** 강의 수강 등록 */
    @PostMapping("course/detail/{courseId}/enrollment")
    public ResponseEntity<Enrollment> enrollmentCourse(@PathVariable Long courseId, @AuthenticationPrincipal User user) {
        Enrollment enrollment = enrollmentService.enrollment(courseId, user);
        return ResponseEntity.ok(enrollment);
    }
}
