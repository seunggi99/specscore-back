package imade.specscore.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import imade.specscore.domain.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String phone;
    private LocalDate birthDate;
    private String interestedJob;
    private String reason;

    // 강사 입력 내용
    private String profile_img;
    private String certificate; // 자격증
    private String education; // 학력
    private String experience; // 경력
    private String content; // 자기 소개 내용
    private Role role;
}