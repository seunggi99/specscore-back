package imade.specscore.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data @Builder
public class CourseQARequest {
    private String title;
    private String content;
    private LocalDate createdDate;
}
