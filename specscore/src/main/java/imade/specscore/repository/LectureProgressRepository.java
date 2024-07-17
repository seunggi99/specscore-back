package imade.specscore.repository;

import imade.specscore.domain.LectureProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureProgressRepository extends JpaRepository<LectureProgress,Long> {

}
