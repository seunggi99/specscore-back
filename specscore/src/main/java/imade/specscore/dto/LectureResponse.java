package imade.specscore.dto;

import imade.specscore.domain.Lecture;
import imade.specscore.domain.LectureProgress;
import imade.specscore.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LectureResponse {
    private Long id;
    private String title;
    private String content;
    private String courseFileUrl;// 자료 파일 URL
    private String videoUrl;     // 비디오 파일 URL
    private int orders;           // 강의 순서
    private double progress;     // 강의 수강률
    public LectureResponse(Lecture lecture, User user) {
        this.id = lecture.getId();
        this.title = lecture.getTitle();
        this.content = lecture.getContent();
        this.courseFileUrl = lecture.getCourseFileUrl();
        this.videoUrl = lecture.getVideoUrl();
        this.orders = lecture.getOrders();
        this.progress = calculateProgress(lecture, user);
    }
    private double calculateProgress(Lecture lecture, User user) {
        return lecture.getLectureProgresses().stream()
                .filter(lp -> lp.getEnrollment().getUser().equals(user))
                .findFirst()
                .map(LectureProgress::getProgress)
                .orElse(0);
    }
}