package mypackage.hrms.entities.concretes;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
@Table(name = "candidates")
public class Candidates extends Users {

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "national_identity", nullable = false, unique = true)
	private String nationalIdentity;

	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "address")
	private String address;

	@Column(name = "gender")
	private String gender;

	@Column(name = "cv_file_path")
	private String cvFilePath;

	@Column(name = "linkedin_profile")
	private String linkedinProfile;

	@Column(name = "github_profile")
	private String githubProfile;

	@PrePersist
	@PreUpdate
	private void validate() {
		if (nationalIdentity != null && !nationalIdentity.matches("\\d{11}")) {
			throw new IllegalArgumentException("National ID must be 11 digits.");
		}
		if (gender != null && !gender.matches("Male|Female|Other")) {
			throw new IllegalArgumentException("Gender must be Male, Female, or Other.");
		}
	}
}