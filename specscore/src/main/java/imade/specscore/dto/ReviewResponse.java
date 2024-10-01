package imade.specscore.dto;

import imade.specscore.domain.Review;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewResponse {
    private Long reviewId;
    private String username;  //username 추가 -> 후기에 작성자 표시
    private String title;
    private String content;
    private int rating;
    private LocalDate createDate;
    public ReviewResponse(Review review) {
        this.reviewId = review.getId();
        this.username = review.getUsername();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.createDate = review.getCreateDate();
    }
}
