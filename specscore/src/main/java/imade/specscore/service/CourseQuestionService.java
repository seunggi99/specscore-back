
package imade.specscore.service;

import imade.specscore.domain.*;
import imade.specscore.dto.CourseQARequest;
import imade.specscore.dto.CourseQAResponse;
import imade.specscore.repository.CourseQuestionRepository;
import imade.specscore.repository.CourseRepository;
import imade.specscore.repository.EnrollmentRepository;
import imade.specscore.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseQuestionService {
    private final LectureRepository lectureRepository;
    private final CourseQuestionRepository courseQuestionRepository;
    private final EnrollmentRepository enrollmentRepository;
//    private final CourseRepository courseRepository;

    /** Course에 대한 전체 질문 조회 */
    public List<CourseQuestion> findAllQuestionsByCourse(Long courseId) {
        return courseQuestionRepository.findByCourseId(courseId);
    }

    /** Lecture에 대한 전체 질문 조회 */
    public List<CourseQuestion> findAllQuestionsByLecture(Long lectureId) {
        return courseQuestionRepository.findByLectureId(lectureId);
    }

    /** 특정 질문 조회 */
    public CourseQuestion findQuestionWithAnswer(Long questionId) {
        return courseQuestionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    /** Lecture에 대한 질문 생성 */
    @Transactional
    public CourseQuestion createQuestion(Long lectureId, User user, CourseQARequest request) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));
        Course course = lecture.getCourse(); // 강의에서 강의(Course) 가져오기
        //Course course = courseRepository.findByCourseId(courseId);
        // 해당 강의(Course)와 사용자(User)를 기반으로 등록 정보(Enrollment) 찾기
        Enrollment enrollment = enrollmentRepository.findByCourseAndUser(course, user)
                .orElseThrow(() -> new RuntimeException("Enrollment not found for the given user and course"));

        CourseQuestion question = new CourseQuestion();
        question.setLecture(lecture);
        question.setEnrollment(enrollment);
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        question.setCreatedDate(request.getCreatedDate());
        question.setUsername(user.getUsername());  //작성자 추가
        return courseQuestionRepository.save(question);
    }
}
