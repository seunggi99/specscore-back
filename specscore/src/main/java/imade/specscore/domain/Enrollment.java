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
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
    private List<LectureProgress> lectureProgresses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
    private List<CourseQuestion> courseQuestions = new ArrayList<>();

    private LocalDate enrollmentDate;

    private int progress;

    private boolean isCompleted;
}
