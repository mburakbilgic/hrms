package mypackage.hrms.entities.concretes;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
@Table(name = "employers")
public class Employers extends Users {

	@Column(name = "company_name", nullable = false)
	private String companyName;

	@Column(name = "web_address", nullable = false)
	private String webAddress;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "tax_number", nullable = false, unique = true)
	private String taxNumber;

	@Column(name = "industry")
	private String industry;

	@Column(name = "company_size")
	private String companySize;

	@Column(name = "address")
	private String address;

	@Column(name = "description")
	private String description;

	@Column(name = "logo_path")
	private String logoPath;

	@Column(name = "linkedin_page")
	private String linkedinPage;

	@Column(name = "is_verified", nullable = false)
	private boolean isVerified;

	@PrePersist
	@PreUpdate
	private void validate() {
		if (webAddress != null && !webAddress.matches("^https?://[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			throw new IllegalArgumentException("Invalid web address format.");
		}
	}
}