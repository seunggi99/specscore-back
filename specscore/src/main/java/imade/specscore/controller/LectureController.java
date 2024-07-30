package imade.specscore.controller;

import imade.specscore.domain.Lecture;
import imade.specscore.domain.User;
import imade.specscore.dto.LectureRequest;
import imade.specscore.dto.LectureResponse;
import imade.specscore.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;

    /* 강의 목차 생성 */
    @PostMapping("/{courseId}/lecture/create")
    public ResponseEntity<Long> createLecture(@PathVariable Long courseId, @RequestBody LectureRequest request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(lectureService.createLecture(courseId, request, user));
    }

    /* 강의 목차 상세  */
    @GetMapping("/{courseId}/detail/lecture/list")
    public ResponseEntity<List<LectureResponse>> getLecturesByCourse(@PathVariable Long courseId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(lectureService.findLecturesByCourse(courseId ,user));
    }
}
