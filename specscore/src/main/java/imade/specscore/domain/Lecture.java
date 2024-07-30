package imade.specscore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import imade.specscore.dto.LectureRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Lecture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @JsonIgnore
    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<LectureProgress> lectureProgresses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<CourseQuestion> courseQuestions = new ArrayList<>();

    private String title;

    private String content;

    private String courseFileUrl;

    private String videoUrl;

    private int orders;

    //== 생성 메서드 ==//
    public static Lecture createLecture(Course course, LectureRequest lectureRequest) {
        Lecture lecture = new Lecture();
        lecture.setTitle(lecture.getTitle());
        lecture.setContent(lecture.getContent());
        lecture.setCourseFileUrl(lecture.getCourseFileUrl());
        lecture.setVideoUrl(lecture.getVideoUrl());
        lecture.setOrders(lecture.getOrders());

        lecture.setCourse(course);
        return lecture;
    }
}
