package imade.specscore.repository;

import imade.specscore.domain.Course;
import imade.specscore.domain.Enrollment;
import imade.specscore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByCourseAndUser(Course course, User user);
    //SELECT e FROM Enrollment e WHERE e.course = :course AND e.user = :user

}
