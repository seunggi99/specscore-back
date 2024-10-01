package imade.specscore.controller;

import imade.specscore.domain.*;
import imade.specscore.dto.CourseDetailResponse;
import imade.specscore.dto.CourseRequest;
import imade.specscore.dto.CourseResponse;
import imade.specscore.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    /* 강의 생성 */
    @PostMapping("/instructor/course/create")
    @Operation(summary = "강의 생성", description = "강의를 생성함")
    public ResponseEntity<Long> createCourse(@RequestBody CourseRequest request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(courseService.createCourse(user, request));
    }

    /* 모든 Course 조회 */
    @GetMapping("/public/course/list")
    @Operation(summary = "모든 강의 조회", description = "모든 강의에 대한 정보를 받아옴")
    public ResponseEntity<List<CourseResponse>> getCourses() {
        return ResponseEntity.ok(courseService.findAllCourses());
    }

    /* Course 상세 */
    @GetMapping("/user/course/{courseId}/detail")
    @Operation(summary = "강의 상세", description = "강의 상세에 대한 정보를 받아옴")
    public ResponseEntity<CourseDetailResponse> getCourseDetails(@PathVariable("courseId") Long courseId, @AuthenticationPrincipal User user) {
        courseService.readUpdate(courseId);
        return ResponseEntity.ok(courseService.getCourseDetail(courseId, user));
    }
}
