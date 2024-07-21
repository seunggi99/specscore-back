package imade.specscore.service;

import imade.specscore.domain.Course;
import imade.specscore.domain.Enrollment;
import imade.specscore.domain.Review;
import imade.specscore.domain.User;
import imade.specscore.dto.ReviewRequest;
import imade.specscore.repository.CourseRepository;
import imade.specscore.repository.EnrollmentRepository;
import imade.specscore.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;
    private final EnrollmentRepository enrollmentRepository;

    /** 특정 강의 리뷰 전체 조회 */
    public List<Review> findAllReview(Long courseId) {
        return reviewRepository.findByCourseId(courseId);
    }

    /** 리뷰 작성 */
    @Transactional
    public Review createReview(Long courseId, User user, ReviewRequest reviewRequest) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // 사용자가 강의에 등록되어 있는지 확인
        Enrollment enrollment = enrollmentRepository.findByCourseAndUser(course, user)
                .orElseThrow(() -> new RuntimeException("User is not enrolled in this course"));

        Review review = new Review();
        review.setTitle(reviewRequest.getTitle());
        review.setContent(reviewRequest.getContent());
        review.setRating(reviewRequest.getRating());    //별점
        review.setLike(reviewRequest.isLike());         //좋아요
        review.setCreateDate(reviewRequest.getCreateDate());
        review.setUsername(user.getUsername());  //작성자

        review.setCourse(course);
        review.setEnrollment(enrollment);

        // 좋아요 + 1
        if(reviewRequest.isLike()) {
            int currentLike = course.getLikeCount();
            course.setLikeCount(currentLike + 1);
        }

        return reviewRepository.save(review);
    }
}
