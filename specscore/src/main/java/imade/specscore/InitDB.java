package imade.specscore;

import imade.specscore.domain.*;
import imade.specscore.dto.userDto.SignupRequest;
import imade.specscore.repository.*;
import imade.specscore.service.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("test")
public class InitDB {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;
    private final LectureProgressRepository lectureProgressRepository;
    private final ReviewRepository reviewRepository;
    private final CourseQuestionRepository courseQuestionRepository;
    private final CourseAnswerRepository courseAnswerRepository;
    private final AuthService authService;

    @PostConstruct
    public void init() {
        initData();
    }

    private void initData() {

        //강사 역할 사용자 생성
        SignupRequest instructorSignupRequest = new SignupRequest();
        instructorSignupRequest.setUsername("instructor");
        instructorSignupRequest.setPassword("password");
        instructorSignupRequest.setEmail("instructor@example.com");
        instructorSignupRequest.setNickname("Instructor");
        instructorSignupRequest.setPhone("010-1234-5678");
        instructorSignupRequest.setBirthDate(LocalDate.of(1980, 1, 1));
        instructorSignupRequest.setProfile_img("profile_img");
        instructorSignupRequest.setCertificate("certificate");
        instructorSignupRequest.setEducation("education");
        instructorSignupRequest.setExperience("experience");
        instructorSignupRequest.setContent("content");
        instructorSignupRequest.setRole(Role.ROLE_INSTRUCTOR);
        authService.signUp(instructorSignupRequest);
        //유저 역할 사용자 생성
        SignupRequest userSignupRequest = new SignupRequest();
        userSignupRequest.setUsername("user");
        userSignupRequest.setPassword("password");
        userSignupRequest.setEmail("user@example.com");
        userSignupRequest.setNickname("User");
        userSignupRequest.setPhone("010-5678-1234");
        userSignupRequest.setBirthDate(LocalDate.of(1990, 1, 1));
        userSignupRequest.setInterestedJob("interestedJob");
        userSignupRequest.setReason("reason");
        userSignupRequest.setRole(Role.ROLE_USER);
        authService.signUp(userSignupRequest);

        User instructor = userRepository.findByUsername("instructor").orElseThrow();
        User user = userRepository.findByUsername("user").orElseThrow();

        // 강의 생성
        Course course = new Course();
        course.setTitle("Sample Course");
        course.setDescription("This is a sample course.");
        course.setGoal("Learn something new.");
        course.setExpected_effects("You will gain knowledge.");
        course.setCreated_date(LocalDate.now());
        course.setModified_date(LocalDate.now());
        course.setStatus(true);
        course.setPrice(1000);
        course.setImg("course_img");
        course.setRatingAvg(0);
        course.setReadCount(0);
        course.setStudentCount(0);
        course.setLikeCount(0);
        course.setSales(0);
        course.setUser(instructor);
        courseRepository.save(course);

        // 강의 목차 생성
        Lecture lecture = new Lecture();
        lecture.setTitle("Sample Lecture");
        lecture.setContent("This is a sample lecture.");
        lecture.setCourseFileUrl("course_file_url");
        lecture.setVideoUrl("video_url");
        lecture.setOrders(1);
        lecture.setCourse(course);
        lectureRepository.save(lecture);

        // 수강 등록 생성
        //Enrollment enrollment = Enrollment.createEnrollment(List.of(), List.of(), List.of(), course, user);
        //enrollmentRepository.save(enrollment);

        // 수강 상태 생성
        //LectureProgress lectureProgress = LectureProgress.createLectureProgress(lecture, enrollment);
        //lectureProgressRepository.save(lectureProgress);

        // 리뷰 생성
        //Review review = Review.createReview(course, user.getUsername(), enrollment, "Sample Review", "This is a sample review.", 5);
        //reviewRepository.save(review);

        // 질문 생성
        //CourseQuestion courseQuestion = CourseQuestion.createCourseQuestion(List.of(), lecture, user.getUsername(), enrollment, "Sample Question", "This is a sample question.");
        //courseQuestionRepository.save(courseQuestion);

        // 답변 생성
        //CourseAnswer courseAnswer = CourseAnswer.createCourseAnswer(courseQuestion, instructor.getUsername(), "Sample Answer", "This is a sample answer.");
        //courseAnswerRepository.save(courseAnswer);
    }
}
