
package imade.specscore.service;

import imade.specscore.domain.*;
import imade.specscore.dto.CourseQuestionRequest;
import imade.specscore.dto.CourseQuestionResponse;
import imade.specscore.repository.CourseQuestionRepository;
import imade.specscore.repository.EnrollmentRepository;
import imade.specscore.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseQuestionService {
    private final LectureRepository lectureRepository;
    private final CourseQuestionRepository courseQuestionRepository;
    private final EnrollmentRepository enrollmentRepository;

    /* 강의 목차에 대한 질문 생성 */
    @Transactional
    public Long createCourseQuestion(Long lectureId, User user, CourseQuestionRequest courseQuestionRequest) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found"));
        Enrollment enrollment = enrollmentRepository.findByCourseIdAndUserId(lecture.getCourse().getId(), user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Lecture not found"));
        CourseQuestion courseQuestion = CourseQuestion.createCourseQuestion(
                lecture, enrollment, user.getUsername(), courseQuestionRequest);
        courseQuestionRepository.save(courseQuestion);
        return courseQuestion.getId();
    }

    /* 전체 질문 조회 (강의) */
    public List<CourseQuestionResponse> findQuestionsByCourseId(Long courseId) {
        List<Lecture> lectures = lectureRepository.findByCourseId(courseId);
        List<CourseQuestion> courseQuestionList = new ArrayList<>();
        for (Lecture lecture : lectures) {
            List<CourseQuestion> questionsForLecture = courseQuestionRepository.findByLectureId(lecture.getId());
            courseQuestionList.addAll(questionsForLecture);
        }
        return courseQuestionList.stream()
                .map(CourseQuestionResponse::new)
                .collect(Collectors.toList());
    }

    /* 전체 질문 조회 (강의 목차) */
    public List<CourseQuestionResponse> findQuestionsByLectureId(Long lectureId) {
        List<CourseQuestion> courseQuestions = courseQuestionRepository.findByLectureId(lectureId);
        return courseQuestions.stream()
                .map(CourseQuestionResponse::new)
                .collect(Collectors.toList());
    }

    /* 질문 및 답변 (질문 상세) */
    public CourseQuestionResponse.CourseQuestionWithAnswerResponse findQuestionWithAnswerByQuestionId(Long questionId) {
        CourseQuestion courseQuestion = courseQuestionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        return new CourseQuestionResponse.CourseQuestionWithAnswerResponse(courseQuestion);
    }

}
