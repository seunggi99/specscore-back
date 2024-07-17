package imade.specscore.service;

import imade.specscore.domain.*;
import imade.specscore.repository.CourseRepository;
import imade.specscore.repository.LectureProgressRepository;
import imade.specscore.repository.LectureRepository;
import imade.specscore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final LectureProgressRepository lectureProgressRepository;

    /** 전체 조회 */
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    /** 단건 조회 */
    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 강의가 존재하지 않습니다."));
    }

}
