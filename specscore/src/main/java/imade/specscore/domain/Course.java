package imade.specscore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_category_id")
    private CourseCategory courseCategory;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "approval_id")
    private Approval approval;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lecture> lectures = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseNotice> courseNotices = new ArrayList<>();

    private String title;
    private String description;
    private String goal;
    private String expected_effects;
    private LocalDate created_date;
    private LocalDate modified_date;
    private boolean status;
    private int price;
    private String img;
    private double ratingAvg;
    private int readCount;
    private int studentCount;
    private int likeCount;
    private int sales;

    /* 연관관계 편의 메서드 */
    public void addLecture(Lecture lecture) {
        lectures.add(lecture);
        lecture.setCourse(this);
    }
    public void setUser(User user) {
        this.user = user;
        user.getCourses().add(this);
    }

    /* 강의 생성 메서드 */
    /*
    public static Course createCourse(User user, Lecture... lectures) {
        Course course = new Course();
        course.setUser(user);
        for(Lecture lecture : lectures) {
            course.addLecture(lecture);
        }
        return course;
    }
    */
}
