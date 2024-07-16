package imade.specscore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter @Setter
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long Id;

    private String username;

    private String password;

    private String email;

    private String nickname;

    private String phone;

    private LocalDate birthDate;

    private LocalDateTime registrationDate;

    private LocalDateTime lastLoginDate;

    private boolean is_active;

    // 학생 입력 내용

    private String interestedJob; // 관심 직무

    private String reason; // 가입 이유

    // 강사 입력 내용
    private String profile_img;

    private String certificate; // 자격증

    private String education; // 학력

    private String experience; // 경력

    private String content; // 자기 소개 내용


    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return is_active;
    }

}
