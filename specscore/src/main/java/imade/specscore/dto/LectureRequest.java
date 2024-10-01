package imade.specscore.dto;

import lombok.Data;

@Data
public class LectureRequest {
    private String title;
    private String content;
    private String courseFileUrl;
    private String videoUrl;
    private int orders;
}
