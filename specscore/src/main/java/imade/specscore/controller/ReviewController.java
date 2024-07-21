package imade.specscore.controller;

import imade.specscore.domain.Course;
import imade.specscore.domain.Review;
import imade.specscore.domain.Role;
import imade.specscore.domain.User;
import imade.specscore.dto.ReviewRequest;
import imade.specscore.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("course/detail")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    /** 특정 강의 리뷰 전체 조회 */
    @GetMapping("/{courseId}/review/list")
    public ResponseEntity<List<Review>> getAllCourseReview(@PathVariable Long courseId) {
        List<Review> responses = reviewService.findAllReview(courseId);
        return ResponseEntity.ok(responses);
    }

    /** 리뷰 작성 */
    @PostMapping("/{courseId}/review/create")
    public ResponseEntity<Review> createReview(@PathVariable Long courseId, @AuthenticationPrincipal User user, @RequestBody ReviewRequest reviewRequest) {
        if (!user.getRole().equals(Role.ROLE_USER)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Review review = reviewService.createReview(courseId, user, reviewRequest);
        return ResponseEntity.ok(review);
    }
}
