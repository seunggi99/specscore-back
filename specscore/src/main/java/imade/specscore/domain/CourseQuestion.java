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
@Getter
@Setter
public class CourseQuestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_question_id")
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @JsonIgnore
    @OneToMany(mappedBy = "courseQuestion", cascade = CascadeType.ALL)
    private List<CourseAnswer> courseAnswers = new ArrayList<>();

    private String title;

    private String content;

    private LocalDate createdDate;

}
