package imade.specscore.service;

import imade.specscore.domain.Course;
import imade.specscore.domain.Enrollment;
import imade.specscore.domain.Review;
import imade.specscore.domain.User;
import imade.specscore.dto.ReviewRequest;
import imade.specscore.dto.ReviewResponse;
import imade.specscore.repository.CourseRepository;
import imade.specscore.repository.EnrollmentRepository;
import imade.specscore.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    /* 강의에 대한 리뷰 생성 */
    @Transactional
    public Long createReview(Long courseId, Long enrollmentId, User user, ReviewRequest reviewRequest) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
        Review review = Review.createReview(course, enrollment, user.getUsername(), reviewRequest);
        reviewRepository.save(review);

        course.updateRatingAvg(); /* 강의 평점 업데이트 */
        course.updateLikedCount(); /* 좋아요 수 업데이트 */
        return review.getId();
    }

    /* 강의 리뷰 전체 조회 */
    public List<ReviewResponse> findReviewsByCourseId(Long courseId) {
        List<Review> reviews = reviewRepository.findByCourseId(courseId);
        return reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }
}
