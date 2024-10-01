package imade.specscore.service;

import imade.specscore.domain.*;
import imade.specscore.dto.CourseAnswerRequest;
import imade.specscore.repository.CourseAnswerRepository;
import imade.specscore.repository.CourseQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseAnswerService {
    private final CourseAnswerRepository courseAnswerRepository;
    private final CourseQuestionRepository courseQuestionRepository;

    /* 답변 생성 */
    @Transactional
    public Long createAnswer(Long courseQuestionId, User user, CourseAnswerRequest courseAnswerRequest) {
        CourseQuestion courseQuestion = courseQuestionRepository.findById(courseQuestionId).orElseThrow(()->
                new IllegalArgumentException("질문을 찾을 수 없습니다."));
        CourseAnswer courseAnswer = CourseAnswer.createCourseAnswer(
                courseQuestion, user.getUsername(), courseAnswerRequest);
        courseAnswerRepository.save(courseAnswer);
        return courseAnswer.getId();
    }
}
