package imade.specscore.service;

import imade.specscore.domain.Lecture;
import imade.specscore.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    public List<Lecture> getAllLecturesByCourseId(Long courseId) {
        return lectureRepository.findByCourseId(courseId);
    }

    public Lecture getLectureById(Long id) {
        return lectureRepository.findById(id).orElseThrow(() -> new RuntimeException("Lecture not found"));
    }

    public Lecture saveLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    public void deleteLecture(Long id) {
        lectureRepository.deleteById(id);
    }
}
