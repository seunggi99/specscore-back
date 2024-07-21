package imade.specscore.repository;

import imade.specscore.domain.Enrollment;
import imade.specscore.domain.Lecture;
import imade.specscore.domain.LectureProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureProgressRepository extends JpaRepository<LectureProgress, Long> {
    Optional<LectureProgress> findByEnrollmentAndLecture(Enrollment enrollment, Lecture lecture);
    //SELECT l FROM LectureProgress l WHERE l.enrollment = :enrollment AND l.lecture = :lecture
    List<LectureProgress> findByEnrollment(Enrollment enrollment);
}
