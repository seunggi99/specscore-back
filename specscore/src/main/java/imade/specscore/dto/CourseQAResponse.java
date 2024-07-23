package imade.specscore.dto;

import imade.specscore.domain.CourseAnswer;
import imade.specscore.domain.CourseQuestion;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CourseQAResponse {
    private Long questionId;
    private String questionTitle;
    private String questionContent;
    private List<AnswerResponse> answers;
    public CourseQAResponse(CourseQuestion question) {
        this.questionId = question.getId();
        this.questionTitle = question.getTitle();
        this.questionContent = question.getContent();
        this.answers = question.getCourseAnswers().stream()
                .map(AnswerResponse::new)
                .collect(Collectors.toList());
    }

    @Data
    public static class AnswerResponse {
        private Long answerId;
        private String answerTitle;
        private String answerContent;
        public AnswerResponse(CourseAnswer answer) {
            this.answerId = answer.getId();
            this.answerTitle = answer.getTitle();
            this.answerContent = answer.getContent();
        }
    }
}

