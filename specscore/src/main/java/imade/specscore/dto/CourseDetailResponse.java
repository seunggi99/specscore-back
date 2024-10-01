package imade.specscore.dto;

import imade.specscore.domain.*;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
//강의 상세
public class CourseDetailResponse {
    private Long id;
    private String title;       //강의 제목
    private String description; //강의 설명
    private String goal;        //강의 목표
    private String expectedEffects; //기대 효과
    private String img;         //강의 대표 이미지
    private double ratingAvg;   //전체 평점 평균

    private EnrollmentResponse enrollmentProgress;  //유저의 수강률
    private List<ReviewResponse> reviews;   //수강 후기 (작성자,별점,내용)
    private List<LectureResponse> lectures;
    private List<CourseQuestionResponse.CourseQuestionWithAnswerResponse> questions; //학생 질문 (작성자,제목,내용) + 강사답변 (답변)

    public CourseDetailResponse(Course course, Enrollment enrollment) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.goal = course.getGoal();
        this.expectedEffects = course.getExpected_effects();
        this.img = course.getImg();
        this.ratingAvg = course.getRatingAvg();

        this.enrollmentProgress = new EnrollmentResponse(enrollment);

        this.reviews = course.getReviews().stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());;

        this.lectures = course.getLectures().stream()
                .map(lecture -> new LectureResponse(lecture, enrollment.getUser()))
                .toList();;

        this.questions = course.getLectures().stream()
                .flatMap(lecture -> lecture.getCourseQuestions().stream())
                .map(CourseQuestionResponse.CourseQuestionWithAnswerResponse::new)
                .collect(Collectors.toList());
    }
}
