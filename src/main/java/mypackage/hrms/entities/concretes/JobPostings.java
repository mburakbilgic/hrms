package mypackage.hrms.entities.concretes;

import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_postings")
public class JobPostings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private Employers employer;

    @ManyToOne
    @JoinColumn(name = "job_title_id", nullable = false)
    private JobTitles jobTitle;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Istanbul"));
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = "Open";
        }
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = ZonedDateTime.now(ZoneId.of("Europe/Istanbul"));
    }
}