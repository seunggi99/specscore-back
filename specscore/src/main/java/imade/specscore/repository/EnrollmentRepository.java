package imade.specscore.repository;

import imade.specscore.domain.Course;
import imade.specscore.domain.Enrollment;
import imade.specscore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    /* userId로 전체 enrollment 찾기 */
    List<Enrollment> findByUserId(Long userId);

    /* courseId, userId로 enrollment 찾기 */
    Optional<Enrollment> findByCourseIdAndUserId(Long courseId, Long userId);

}
