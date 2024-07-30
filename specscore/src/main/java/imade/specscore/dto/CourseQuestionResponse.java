package imade.specscore.dto;
import imade.specscore.domain.CourseQuestion;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CourseQuestionResponse {
    private Long questionId;
    private String questionTitle;
    private String questionContent;

    public CourseQuestionResponse(CourseQuestion question) {
        this.questionId = question.getId();
        this.questionTitle = question.getTitle();
        this.questionContent = question.getContent();
    }

    @Data
    public static class CourseQuestionWithAnswerResponse {
        private Long questionId;
        private String questionTitle;
        private String questionContent;
        private List<CourseAnswerResponse> answers;

        public CourseQuestionWithAnswerResponse(CourseQuestion question) {
            this.questionId = question.getId();
            this.questionTitle = question.getTitle();
            this.questionContent = question.getContent();
            this.answers = question.getCourseAnswers().stream()
                    .map(CourseAnswerResponse::new)
                    .collect(Collectors.toList());
        }
    }
}
