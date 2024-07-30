package imade.specscore.controller;

import imade.specscore.domain.*;
import imade.specscore.dto.CourseDetailResponse;
import imade.specscore.dto.CourseRequest;
import imade.specscore.dto.CourseResponse;
import imade.specscore.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    /* 강의 생성 */
    @PostMapping("/course/create")
    public ResponseEntity<Long> createCourse(CourseRequest request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(courseService.createCourse(user, request));
    }

    /* 모든 Course 조회 */
    @GetMapping("/list")
    public ResponseEntity<List<CourseResponse>> getCourses() {
        return ResponseEntity.ok(courseService.findAllCourses());
    }

    /* Course 상세 */
    @GetMapping("/{courseId}/detail")
    public ResponseEntity<CourseDetailResponse> getCourseDetails(@PathVariable Long courseId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(courseService.getCourseDetail(courseId, user));
    }
}
