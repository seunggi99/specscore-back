package imade.specscore.repository;

import imade.specscore.domain.Enrollment;
import imade.specscore.domain.Lecture;
import imade.specscore.domain.LectureProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureProgressRepository extends JpaRepository<LectureProgress, Long> {
    /* enrollmentId, 와 lectureId로 LectureProgress 찾기 */
    Optional<LectureProgress> findByEnrollmentIdAndLectureId(Long enrollmentId, Long lectureId);

    /* enrollmentId로 전체 LectureProgress 찾기 */
    List<LectureProgress> findByEnrollmentId(Long enrollmentId);
}
