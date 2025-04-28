package mypackage.hrms.entities.concretes;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "candidate_languages")
public class CandidateLanguages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidates candidate;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "proficiency_level", nullable = false)
    private String proficiencyLevel;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (language == null || language.isBlank()) {
            throw new IllegalArgumentException("Language cannot be empty.");
        }
        if (proficiencyLevel == null || proficiencyLevel.isBlank()) {
            throw new IllegalArgumentException("Proficiency level cannot be empty.");
        }
    }
}