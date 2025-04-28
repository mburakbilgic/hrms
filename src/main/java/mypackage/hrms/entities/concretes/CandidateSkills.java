package mypackage.hrms.entities.concretes;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "candidate_skills")
public class CandidateSkills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidates candidate;

    @Column(name = "skill_name", nullable = false)
    private String skillName;

    @Column(name = "proficiency_level")
    private String proficiencyLevel;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (skillName == null || skillName.isBlank()) {
            throw new IllegalArgumentException("Skill name cannot be empty.");
        }
    }
}