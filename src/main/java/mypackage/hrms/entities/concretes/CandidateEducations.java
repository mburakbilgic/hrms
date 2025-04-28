package mypackage.hrms.entities.concretes;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "candidate_educations")
public class CandidateEducations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidates candidate;

    @Column(name = "education_level", nullable = false)
    private String educationLevel;

    @Column(name = "university")
    private String university;

    @Column(name = "department")
    private String department;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (educationLevel == null || educationLevel.isBlank()) {
            throw new IllegalArgumentException("Education level cannot be empty.");
        }
    }
}