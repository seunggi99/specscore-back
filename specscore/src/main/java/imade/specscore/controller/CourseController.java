package imade.specscore.controller;

import imade.specscore.domain.*;
import imade.specscore.dto.CourseDetailResponse;
import imade.specscore.dto.CourseResponse;
import imade.specscore.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    /** 전체 강의 목록 조회 */
    @GetMapping("/list")
    public ResponseEntity<List<CourseResponse>> getAllCourse() {
        List<Course> courses = courseService.findAllCourses();
        List<CourseResponse> courseResponses = courses.stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseResponses);
    }

    /** 강의 상세 */
    @GetMapping("/detail/{courseId}")
    public ResponseEntity<CourseDetailResponse> getCourseDetails(@PathVariable Long courseId) {
        Course course = courseService.findCourseById(courseId);
        CourseDetailResponse courseDetail = new CourseDetailResponse(course);
        return ResponseEntity.ok(courseDetail);
    }
}
