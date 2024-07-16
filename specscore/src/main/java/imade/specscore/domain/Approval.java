package imade.specscore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Approval {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private Long Id;

    @JsonIgnore
    @OneToMany(mappedBy = "approval", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "approval", cascade = CascadeType.ALL)
    private List<EmploymentNotice> employmentNotices = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ApprovalType approvalType;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createDate;

    private LocalDate modifiedDate;
}
