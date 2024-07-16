package imade.specscore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class EmploymentNotice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employment_notice_id")
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "approval_id")
    private Approval approval;

    private String title;

    private String content;

    private LocalDate createdDate;

    private int readCount;
}
