package imade.specscore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private String createdDate;

    @LastModifiedDate
    private String lastModifiedDate;

    @PrePersist
    public void prePersist() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.createdDate = now;
        this.lastModifiedDate = now;
    }
    @PreUpdate
    public void preUpdate() {
        this.lastModifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}
