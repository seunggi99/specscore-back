package imade.specscore.dto;

import imade.specscore.domain.Course;
import imade.specscore.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
//강의 목록
public class CourseResponse {
    private Long courseId;
    private String name; //강사
    private String title; //제목
    private int price; //강의 가격
    private String img;  //강의 대표 이미지
    private double ratingAvg; //강의 평점 평균
    private int readCount; //조회수
    private int likeCount; //좋아요 수

    public CourseResponse(Course course) {
        this.courseId = course.getId();
        this.name = course.getUser().getUsername();
        this.title = course.getTitle();
        this.price = course.getPrice();
        this.img = course.getImg();
        this.ratingAvg = course.getRatingAvg();
        this.readCount = course.getReadCount();
        this.likeCount = course.getLikeCount();
    }
}