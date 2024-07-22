package imade.specscore.controller;

import imade.specscore.domain.Lecture;
import imade.specscore.domain.User;
import imade.specscore.dto.LectureResponse;
import imade.specscore.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;

    @GetMapping("/detail/lecture/list")
    public ResponseEntity<List<LectureResponse>> getLecturesByCourse(@PathVariable Long courseId, @AuthenticationPrincipal User user) {
        List<Lecture> lectures = lectureService.findAllLecturesByCourse(courseId);
        List<LectureResponse> responses = lectures.stream()
                .map(lecture -> new LectureResponse(lecture, user))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
