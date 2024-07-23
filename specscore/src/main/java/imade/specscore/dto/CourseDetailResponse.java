package imade.specscore.dto;

import imade.specscore.domain.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
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
    private List<Review> reviews;   //수강 후기 (작성자,별점,내용)

    private List<Lecture> lectures;
    private UserProgress userProgress;  //유저의 수강률

    private List<CourseQuestionResponse> questions; //학생질문 (작성자,제목,내용) + 강사답변 (답변)
    //private List<CourseAnswer> answers;

    public CourseDetailResponse(Course course, User user) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.goal = course.getGoal();
        this.expectedEffects = course.getExpected_effects();
        this.img = course.getImg();
        this.ratingAvg = course.getRatingAvg();
        this.reviews = course.getReviews();

        this.lectures = course.getLectures();
        this.userProgress = new UserProgress(user, course);

        // Lecture에서 CourseQuestion을 끌어오는 로직
        this.questions = course.getLectures().stream()
                .flatMap(lecture -> lecture.getCourseQuestions().stream())
                .map(CourseQuestionResponse::new)
                .collect(Collectors.toList());
    }

    @Data
    public static class UserProgress {
        private Long userId;
        private double courseProgress;

        public UserProgress(User user, Course course) {
            this.userId = user.getId();
            Optional<Enrollment> enrollmentOpt = course.getEnrollments().stream()
                    .filter(e -> e.getUser().equals(user))
                    .findFirst();
            if (enrollmentOpt.isPresent()) {
                this.courseProgress = enrollmentOpt.get().getProgress();
            } else {
                this.courseProgress = 0.0;  // 등록되지 않은 경우 수강률을 0으로 설정
            }
        }
    }

    @Data
    public static class CourseQuestionResponse {
        private Long id;
        private String title;
        private String content;
        private List<CourseAnswerResponse> answers;

        /* CourseQuestion에서 CourseAnswer을 끌어오는 로작 */
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
