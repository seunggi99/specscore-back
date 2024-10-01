package imade.specscore.repository;

import imade.specscore.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByCourseId(Long courseId);
}
