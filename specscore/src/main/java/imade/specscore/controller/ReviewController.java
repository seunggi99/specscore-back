package imade.specscore.controller;

import imade.specscore.domain.*;
import imade.specscore.dto.ReviewRequest;
import imade.specscore.dto.ReviewResponse;
import imade.specscore.service.EnrollmentService;
import imade.specscore.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final EnrollmentService enrollmentService;

    /* 강의에 대한 리뷰 생성 */
    @PostMapping("/user/course/{courseId}/detail/review/create")
    @Operation(summary = "리뷰 생성", description = "강의에 대한 리뷰를 생성함")
    public ResponseEntity<Long> createReview(@PathVariable("courseId") Long courseId, @AuthenticationPrincipal User user, @RequestBody ReviewRequest reviewRequest) {
        Enrollment enrollment = enrollmentService.findByCourseIdAndUser(courseId, user);
        return ResponseEntity.ok(reviewService.createReview(courseId, enrollment.getId(), user, reviewRequest));
    }

    /* 강의 리뷰 전체 조회 */
    @GetMapping("/user/course/{courseId}/detail/review/list")
    @Operation(summary = "리뷰 전체 조회", description = "강의에 대한 전체 리뷰를 조회함")
    public ResponseEntity<List<ReviewResponse>> getReviewsByCourseId(@PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(reviewService.findReviewsByCourseId(courseId));
    }
}
