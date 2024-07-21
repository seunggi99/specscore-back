package imade.specscore.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data @Builder
public class ReviewRequest {
    private String title;
    private String content;
    private int rating;
    private LocalDate createDate;
    private boolean like;
}
