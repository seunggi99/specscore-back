package imade.specscore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class LectureProgress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_progress_id")
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    private boolean isCompleted;

    private int progress;

    private LocalDate lastAccessed;

    //==생성 메서드==//
    public static LectureProgress createLectureProgress(Lecture lecture, Enrollment enrollment) {
        LectureProgress lectureProgress = new LectureProgress();
        lectureProgress.setCompleted(false);
        lectureProgress.setProgress(0);
        lectureProgress.setLastAccessed(LocalDate.now());

        lectureProgress.setLecture(lecture);
        lectureProgress.setEnrollment(enrollment);
        return lectureProgress;
    }
}
