package imade.specscore.dto;

import imade.specscore.domain.Enrollment;
import lombok.Data;

@Data
public class EnrollmentResponse {
    private Long enrollmentId;
    private int progress;
    private boolean isCompleted;

    public EnrollmentResponse(Enrollment enrollment) {
        this.enrollmentId = enrollment.getId();
        this.progress = enrollment.getProgress();
        this.isCompleted = enrollment.isCompleted();
    }
}
