package imade.specscore.service;

import imade.specscore.domain.*;
import imade.specscore.dto.CourseQARequest;
import imade.specscore.repository.CourseAnswerRepository;
import imade.specscore.repository.CourseQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseAnswerService {
    private final CourseQuestionRepository courseQuestionRepository;
    private final CourseAnswerRepository courseAnswerRepository;
    /** 답변 생성 */
    @Transactional
    public CourseAnswer createAnswer(Long questionId, User user, CourseQARequest request) {
        CourseQuestion question = courseQuestionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("CourseQuestion not found"));

        CourseAnswer answer = new CourseAnswer();
        answer.setCourseQuestion(question);
        answer.setUsername(user.getUsername());
        answer.setTitle(request.getTitle());
        answer.setContent(request.getContent());
        answer.setCreatedDate(request.getCreatedDate());

        return courseAnswerRepository.save(answer);
    }
}
