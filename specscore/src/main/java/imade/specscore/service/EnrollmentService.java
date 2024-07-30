package imade.specscore.service;

import imade.specscore.domain.*;
import imade.specscore.dto.CourseResponse;
import imade.specscore.repository.CourseRepository;
import imade.specscore.repository.EnrollmentRepository;
import imade.specscore.repository.LectureProgressRepository;
import imade.specscore.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;
    private final LectureProgressRepository lectureProgressRepository;

    /* 수강 등록 생성 */
    @Transactional
    public Long createEnrollment(Long courseId, User user) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Enrollment enrollment = Enrollment.createEnrollment(course, user);
        enrollmentRepository.save(enrollment);
        List<Lecture> lectures = lectureRepository.findByCourseId(courseId);
        for (Lecture lecture : lectures) {
            LectureProgress lectureProgress = LectureProgress.createLectureProgress(lecture, enrollment);
            lectureProgressRepository.save(lectureProgress);
        }
        return enrollment.getId();
    }

    /* userId, courseId로 Enrollment 찾기 */
    public Enrollment findByCourseIdAndUser(Long courseId, User user) {
        return enrollmentRepository.findByCourseIdAndUserId(courseId, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
    }

    /* userId로 Course 찾기 */
    public List<CourseResponse> findCoursesByUser(User user) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(user.getId());
        List<Course> courses = enrollments.stream()
                .map(Enrollment::getCourse)
                .toList();
        return courses.stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }

    /* 강의 진행률 업데이트 */
    @Transactional
    public LectureProgress updateLectureProgress(Long enrollmentId, Long lectureId, int progress) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found"));
        LectureProgress lectureProgress = lectureProgressRepository.findByEnrollmentIdAndLectureId(enrollmentId, lectureId)
                .orElse(new LectureProgress());  // 새 진행 상태 생성
        lectureProgress.setEnrollment(enrollment);
        lectureProgress.setLecture(lecture);
        lectureProgress.setProgress(progress);
        lectureProgress.setLastAccessed(LocalDate.now());
        if(lectureProgress.getProgress() == 100) {
            lectureProgress.setCompleted(true);
        }
        updateCourseProgress(enrollment);
        return lectureProgressRepository.save(lectureProgress);
    }

    /* 전체 강의 진행률 업데이트 */
    @Transactional
    private void updateCourseProgress(Enrollment enrollment) {
        List<LectureProgress> progresses = lectureProgressRepository.findByEnrollmentId(enrollment.getId());
        double averageProgress = progresses.stream()
                .mapToInt(LectureProgress::getProgress)
                .average()
                .orElse(0.0);
        enrollment.setProgress((int) averageProgress);
    }
}
