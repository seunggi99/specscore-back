package imade.specscore;

import imade.specscore.domain.*;
import imade.specscore.dto.userDto.SignupRequest;
import imade.specscore.repository.*;
import imade.specscore.service.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;
    private final LectureProgressRepository lectureProgressRepository;
    private final CourseQuestionRepository courseQuestionRepository;
    private final CourseAnswerRepository courseAnswerRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        initData();
    }

    public void initData() {
        // 초기 데이터 삽입 로직
        User instructor = createUser("instructor", "password", Role.ROLE_INSTRUCTOR);
        User student = createUser("student", "password", Role.ROLE_USER);

        Course course = createCourse("Java Basics", instructor);
        Enrollment enrollment = createEnrollment(course, student);
        Lecture lecture1 = createLecture(course, "Introduction to Java", "Java overview", "fileUrl", "videoUrl", 1);
        Lecture lecture2 = createLecture(course, "Java Syntax", "Java basic syntax", "fileUrl", "videoUrl", 2);

        createLectureProgress(lecture1, enrollment);
        createLectureProgress(lecture2, enrollment);

        CourseQuestion question = createCourseQuestion(lecture1, student, enrollment, "What is Java?", "Can someone explain what Java is?");
        createCourseAnswer(question, instructor, "Java is a programming language.");

        createReview(course, student, enrollment, "Great course!", "I learned a lot.", 5);
    }

    @Transactional
    public User createUser(String username, String password, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setEmail(username + "@example.com");
        user.setNickname(username);
        user.setPhone("010-1234-5678");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setRegistrationDate(LocalDateTime.now());
        user.setActive(true);
        return userRepository.save(user);
    }

    @Transactional
    public Course createCourse(String title, User instructor) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription("This is a course about Java Basics.");
        course.setGoal("Learn Java from scratch.");
        course.setExpected_effects("Become proficient in Java.");
        course.setCreated_date(LocalDate.now());
        course.setModified_date(LocalDate.now());
        course.setStatus(true);
        course.setPrice(100);
        course.setImg("imgUrl");
        course.setRatingAvg(0);
        course.setReadCount(0);
        course.setStudentCount(0);
        course.setLikeCount(0);
        course.setSales(0);
        course.setUser(instructor);
        return courseRepository.save(course);
    }

    @Transactional
    public Enrollment createEnrollment(Course course, User student) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setUser(student);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setProgress(0);
        enrollment.setCompleted(false);
        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    public Lecture createLecture(Course course, String title, String content, String courseFileUrl, String videoUrl, int orders) {
        Lecture lecture = new Lecture();
        lecture.setTitle(title);
        lecture.setContent(content);
        lecture.setCourseFileUrl(courseFileUrl);
        lecture.setVideoUrl(videoUrl);
        lecture.setOrders(orders);
        lecture.setCourse(course);
        return lectureRepository.save(lecture);
    }

    @Transactional
    public LectureProgress createLectureProgress(Lecture lecture, Enrollment enrollment) {
        LectureProgress lectureProgress = new LectureProgress();
        lectureProgress.setLecture(lecture);
        lectureProgress.setEnrollment(enrollment);
        lectureProgress.setCompleted(false);
        lectureProgress.setProgress(0);
        lectureProgress.setLastAccessed(LocalDate.now());
        return lectureProgressRepository.save(lectureProgress);
    }

    @Transactional
    public CourseQuestion createCourseQuestion(Lecture lecture, User student, Enrollment enrollment, String title, String content) {
        CourseQuestion courseQuestion = new CourseQuestion();
        courseQuestion.setLecture(lecture);
        courseQuestion.setEnrollment(enrollment);
        courseQuestion.setTitle(title);
        courseQuestion.setContent(content);
        courseQuestion.setCreatedDate(LocalDate.now());
        courseQuestion.setUsername(student.getUsername());
        return courseQuestionRepository.save(courseQuestion);
    }

    @Transactional
    public CourseAnswer createCourseAnswer(CourseQuestion courseQuestion, User instructor, String content) {
        CourseAnswer courseAnswer = new CourseAnswer();
        courseAnswer.setCourseQuestion(courseQuestion);
        courseAnswer.setUsername(instructor.getUsername());
        courseAnswer.setTitle("Answer");
        courseAnswer.setContent(content);
        courseAnswer.setCreatedDate(LocalDate.now());
        return courseAnswerRepository.save(courseAnswer);
    }

    @Transactional
    public Review createReview(Course course, User student, Enrollment enrollment, String title, String content, int rating) {
        Review review = new Review();
        review.setCourse(course);
        review.setEnrollment(enrollment);
        review.setUsername(student.getUsername());
        review.setTitle(title);
        review.setContent(content);
        review.setRating(rating);
        review.setCreateDate(LocalDate.now());
        review.setLiked(false);
        return reviewRepository.save(review);
    }
}
