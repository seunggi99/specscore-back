package imade.specscore.service;

import imade.specscore.domain.Lecture;
import imade.specscore.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {
    private final LectureRepository lectureRepository;

    public List<Lecture> findAllLecturesByCourse(Long courseId) {
        return lectureRepository.findByCourseId(courseId);
    }
}
