package imade.specscore.service;

import imade.specscore.domain.*;
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

    /** 등록 강의 전체 조회 */
    public List<Course> findCoursesByUser(User user) {
        List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
        return enrollments.stream()
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());
    }

    /** 수강 등록 */
    @Transactional
    public Enrollment enrollment(Long courseId, User user) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        Enrollment existingEnrollment = enrollmentRepository.findByCourseAndUser(course, user).orElse(null);
        if (existingEnrollment != null) {
            throw new IllegalStateException("User is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setUser(user);

        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setProgress(0);       //전체 수강률
        enrollment.setCompleted(false);  //완강 여부
        return enrollmentRepository.save(enrollment);
    }

    /** 각 과목별 강의 진행률 업데이트 */
    @Transactional
    public LectureProgress updateLectureProgress(Long enrollmentId, Long lectureId, int progress) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found"));
        LectureProgress lectureProgress = lectureProgressRepository.findByEnrollmentAndLecture(enrollment, lecture)
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

    /** 각 과목별 강의 진행률 100시 전체 강의 진행률 업데이트 */
    @Transactional
    private void updateCourseProgress(Enrollment enrollment) {
        List<LectureProgress> progresses = lectureProgressRepository.findByEnrollment(enrollment);
        double averageProgress = progresses.stream()
                .mapToInt(LectureProgress::getProgress)
                .average()
                .orElse(0.0);

        enrollment.setProgress((int) averageProgress);
    }
}
