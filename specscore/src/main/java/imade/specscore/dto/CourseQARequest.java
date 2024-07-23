package imade.specscore.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseQARequest {
    private String title;
    private String content;
    private LocalDate createdDate;
}
