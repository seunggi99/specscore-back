package imade.specscore.domain;

import imade.specscore.dto.CourseAnswerRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class CourseAnswer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_answer_id")
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_question_id")
    private CourseQuestion courseQuestion;

    private String username;  //username 추가 -> 후기에 작성자 표시

    private String title;

    private String content;

    private LocalDate createdDate;

    //==생성 메서드==//
    public static CourseAnswer createCourseAnswer(CourseQuestion courseQuestion, String username, CourseAnswerRequest courseAnswerRequest) {
        CourseAnswer courseAnswer = new CourseAnswer();
        courseAnswer.setTitle(courseAnswerRequest.getTitle());
        courseAnswer.setContent(courseAnswerRequest.getContent());

        courseAnswer.setCreatedDate(LocalDate.now());

        courseAnswer.setCourseQuestion(courseQuestion);
        courseAnswer.setUsername(username);
        return courseAnswer;
    }

}
