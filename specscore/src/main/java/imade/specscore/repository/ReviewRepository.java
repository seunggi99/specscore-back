package imade.specscore.repository;

import imade.specscore.domain.Course;
import imade.specscore.domain.Enrollment;
import imade.specscore.domain.Review;
import imade.specscore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCourseId(Long courseId);
}
