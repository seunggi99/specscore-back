package imade.specscore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
public class CourseRequest {
    private String title;
    private String description;
    private String goal;
    private String expectedEffects;
    private boolean status;
    private int price;
    private String img;

    private List<LectureRequest> lectures;
}
