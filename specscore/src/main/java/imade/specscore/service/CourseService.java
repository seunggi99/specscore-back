package imade.specscore.service;

import imade.specscore.domain.*;
import imade.specscore.dto.CourseDetailResponse;
import imade.specscore.dto.CourseRequest;
import imade.specscore.dto.CourseResponse;
import imade.specscore.dto.LectureRequest;
import imade.specscore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;
    private final EnrollmentRepository enrollmentRepository;

    /* 모든 강의 조회 */
    public List<CourseResponse> findAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    /* 단일 강의 조회 */
    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 강의가 존재하지 않습니다."));
    }

    /* 강의 상세 */
    public CourseDetailResponse getCourseDetail(Long courseId, User user) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Enrollment enrollment = enrollmentRepository.findByCourseIdAndUserId(courseId, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
        return new CourseDetailResponse(course, enrollment);
    }

    /* 강의 생성 */
    @Transactional
    public Long createCourse(User user, CourseRequest courseRequest) {
        Course course = Course.createCourse(courseRequest, user);
        courseRepository.save(course);
        for (LectureRequest lectureRequest : courseRequest.getLectures()) {
            Lecture lecture = Lecture.createLecture(course, lectureRequest);
            lectureRepository.save(lecture);
        }
        return course.getId();
    }

    /* 조회수 증가 */
    @Transactional
    public void readUpdate(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        // 조회수 증가
        course.updateReadCount();
    }
}
