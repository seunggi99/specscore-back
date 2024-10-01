package imade.specscore.repository;

import imade.specscore.domain.CourseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseQuestionRepository extends JpaRepository<CourseQuestion, Long> {
    List<CourseQuestion> findByLectureId(Long lectureId);
}
