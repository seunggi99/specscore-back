package imade.specscore.repository;

import imade.specscore.domain.CourseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseQuestionRepository extends JpaRepository<CourseQuestion, Long> {
}
