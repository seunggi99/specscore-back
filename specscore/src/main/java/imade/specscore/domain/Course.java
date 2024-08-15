package imade.specscore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import imade.specscore.dto.CourseRequest;
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

    //== 생성 메서드 ==//
    public static Course createCourse(CourseRequest courseRequest, User user) {
        Course course = new Course();
        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());
        course.setGoal(courseRequest.getGoal());
        course.setExpected_effects(courseRequest.getExpectedEffects());

        course.setCreated_date(LocalDate.now());
        course.setModified_date(LocalDate.now());

        course.setStatus(courseRequest.isStatus());
        course.setPrice(courseRequest.getPrice());
        course.setImg(courseRequest.getImg());
        course.setRatingAvg(0.0);
        course.setReadCount(0);
        course.setStudentCount(0);
        course.setLikeCount(0);
        course.setSales(0);

        course.setUser(user);
        return course;
    }

    //== 비지니스 로직 ==//
    /* 평균 평점 업데이트 */
    public void updateRatingAvg() {
        this.ratingAvg = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    /* 학생 수 업데이트 */
    public void updateStudentCount() {
        this.studentCount++;
    }

    /* 좋아요 수 업데이트 */
    public void updateLikedCount() {
        this.likeCount++;
    }

    /* 조회수 업데이트 */
    public void updateReadCount() {
        this.readCount++;
    }
}
