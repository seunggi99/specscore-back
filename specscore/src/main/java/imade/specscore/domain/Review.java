package imade.specscore.domain;

import imade.specscore.dto.ReviewRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    private String username;  //username 추가 -> 후기에 작성자 표시
    
    private String title;

    private String content;

    private int rating;

    private LocalDate createDate;

    private boolean liked;

    //==생성 메서드==//
    public static Review createReview(Course course, Enrollment enrollment, String username, ReviewRequest reviewRequest) {
        Review review = new Review();
        review.setTitle(reviewRequest.getTitle());
        review.setContent(reviewRequest.getContent());
        review.setRating(review.getRating());

        review.setCreateDate(LocalDate.now());
        review.setLiked(false);

        review.setUsername(username);
        review.setCourse(course);
        review.setEnrollment(enrollment);
        return review;
    }
}
