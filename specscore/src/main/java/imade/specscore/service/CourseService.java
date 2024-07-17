package imade.specscore.service;

import imade.specscore.domain.Course;
import imade.specscore.domain.Lecture;
import imade.specscore.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 강의가 존재하지 않습니다."));
    }

}
