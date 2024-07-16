package imade.specscore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class CourseNotice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_notice_id")
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private String title;

    private String content;

    private LocalDate createdDate;

    private int readCount;
}
