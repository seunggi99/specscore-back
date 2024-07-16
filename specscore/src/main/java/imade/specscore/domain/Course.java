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
    @OneToMany(mappedBy = "course")
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Lecture> lectures = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course")
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

}
