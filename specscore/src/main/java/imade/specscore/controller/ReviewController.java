package imade.specscore.controller;

import imade.specscore.domain.*;
import imade.specscore.dto.ReviewRequest;
import imade.specscore.dto.ReviewResponse;
import imade.specscore.service.EnrollmentService;
import imade.specscore.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final EnrollmentService enrollmentService;

    /* 강의에 대한 리뷰 생성 */
    @PostMapping("/{courseId}/detail/review/create")
    public ResponseEntity<Long> createReview(@PathVariable Long courseId, @AuthenticationPrincipal User user, @RequestBody ReviewRequest reviewRequest) {
        if (!user.getRole().equals(Role.ROLE_USER)) {return ResponseEntity.status(HttpStatus.FORBIDDEN).build();}
        Enrollment enrollment = enrollmentService.findByCourseIdAndUser(courseId, user);
        return ResponseEntity.ok(reviewService.createReview(courseId, enrollment.getId(), user, reviewRequest));
    }

    /* 강의 리뷰 전체 조회 */
    @GetMapping("/{courseId}/detail/review/list")
    public ResponseEntity<List<ReviewResponse>> getReviewsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(reviewService.findReviewsByCourseId(courseId));
    }
}
