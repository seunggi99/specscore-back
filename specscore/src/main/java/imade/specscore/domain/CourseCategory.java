package imade.specscore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class CourseCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_category_id")
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "courseCategory")
    private List<Course> courses = new ArrayList<>();
}
