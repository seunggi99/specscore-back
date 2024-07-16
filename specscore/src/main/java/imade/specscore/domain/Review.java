package imade.specscore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    private String title;

    private String content;

    private int rating;

    private LocalDate createDate;

    private boolean like;
}
