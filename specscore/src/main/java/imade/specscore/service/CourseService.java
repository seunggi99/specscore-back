package imade.specscore.service;

import imade.specscore.domain.*;
import imade.specscore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;

    /** 모든 Course 조회 */
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    /** 특정 Course 조회 */
    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 강의가 존재하지 않습니다."));
    }

    /** 강의 생성 */
    /*
    @Transactional
    public Course createCourse(User user, CourseRequest courseRequest) {
        Course course = new Course();
        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());
        course.setUser(user);

        courseRequest.getLectures().forEach(lectureRequest -> {
            Lecture lecture = new Lecture();
            lecture.setTitle(lectureRequest.getTitle());
            lecture.setVideoUrl(lectureRequest.getVideoUrl());
            course.addLecture(lecture);
        });
        return courseRepository.save(course);
    }
    */

}
