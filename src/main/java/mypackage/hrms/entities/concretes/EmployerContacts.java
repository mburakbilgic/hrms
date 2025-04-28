package mypackage.hrms.entities.concretes;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employer_contacts")
public class EmployerContacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private Employers employer;

    @Column(name = "contact_type", nullable = false)
    private String contactType;

    @Column(name = "contact_value", nullable = false)
    private String contactValue;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (contactType == null || !contactType.matches("PHONE|EMAIL|ADDRESS|OTHER")) {
            throw new IllegalArgumentException("Contact type must be PHONE, EMAIL, ADDRESS, or OTHER.");
        }
        if (contactValue == null || contactValue.isBlank()) {
            throw new IllegalArgumentException("Contact value cannot be empty.");
        }
    }
}