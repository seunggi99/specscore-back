package imade.specscore.dto;

import imade.specscore.domain.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder @Data
public class CourseDetailResponse {
    private Long id;
    private String title;       //강의 제목
    private String description; //강의 설명
    private String goal;        //강의 목표
    private String expectedEffects; //기대 효과
    private String img;         //강의 대표 이미지
    private double ratingAvg;   //전체 평점 평균

    private List<Lecture> lectures; //강의 목차 (강의 제목)
    private List<Review> reviews;   //수강 후기 (작성자,별점,내용)
    private List<CourseQuestionResponse> questions; //학생질문 (작성자,제목,내용)
    //private List<CourseAnswer> answers;     //강사답변 (답변)

    public CourseDetailResponse(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.goal = course.getGoal();
        this.expectedEffects = course.getExpected_effects();
        this.img = course.getImg();
        this.ratingAvg = course.getRatingAvg();

        this.lectures = course.getLectures();
        this.reviews = course.getReviews();

        // Lecture에서 CourseQuestion을 끌어오는 로직
        this.questions = course.getLectures().stream()
                .flatMap(lecture -> lecture.getCourseQuestions().stream())
                .map(CourseQuestionResponse::new)
                .collect(Collectors.toList());
    }

    @Data
    public static class CourseQuestionResponse {
        private Long id;
        private String title;
        private String content;
        private List<CourseAnswerResponse> answers;

        public CourseQuestionResponse(CourseQuestion question) {
            this.id = question.getId();
            this.title = question.getTitle();
            this.content = question.getContent();
            this.answers = question.getCourseAnswers().stream()
                    .map(CourseAnswerResponse::new)
                    .collect(Collectors.toList());
        }
    }

    @Data
    public static class CourseAnswerResponse {
        private Long id;
        private String content;

        public CourseAnswerResponse(CourseAnswer answer) {
            this.id = answer.getId();
            this.content = answer.getContent();
        }
    }
}
