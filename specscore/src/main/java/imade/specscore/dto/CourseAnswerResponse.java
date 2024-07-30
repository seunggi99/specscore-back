package imade.specscore.dto;

import imade.specscore.domain.CourseAnswer;
import lombok.Data;

@Data
public class CourseAnswerResponse {
    private Long answerId;
    private String answerTitle;
    private String answerContent;

    public CourseAnswerResponse(CourseAnswer courseAnswer) {
        this.answerId = courseAnswer.getId();
        this.answerTitle = courseAnswer.getTitle();
        this.answerContent = courseAnswer.getContent();
    }
}
