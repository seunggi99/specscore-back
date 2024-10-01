package imade.specscore.service;

import imade.specscore.domain.*;
import imade.specscore.dto.LectureRequest;
import imade.specscore.dto.LectureResponse;
import imade.specscore.repository.CourseRepository;
import imade.specscore.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {
    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;

    /* 강의 목차 생성 */
    @Transactional
    public Long createLecture(Long courseId, LectureRequest lectureRequest, User user) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        if (!user.getRole().equals(Role.ROLE_INSTRUCTOR)) {
            throw new RuntimeException("Only instructors can create lectures");
        }
        Lecture lecture = Lecture.createLecture(course, lectureRequest);
        lectureRepository.save(lecture);
        return lecture.getId();
    }

    /* 강의 목차 상세 */
    public List<LectureResponse> findLecturesByCourse(Long courseId, User user) {
        List<Lecture> lectures = lectureRepository.findByCourseId(courseId);
        return lectures.stream()
                .map(lecture -> new LectureResponse(lecture, user))
                .toList();
    }
}
