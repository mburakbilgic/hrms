package mypackage.hrms.entities.concretes;

import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "activate_status_email", nullable = false)
	private boolean activateStatusEmail;

	@Column(name = "activate_status_kyc", nullable = false)
	private boolean activateStatusKyc;

	@Column(name = "activate_status_sms", nullable = false)
	private boolean activateStatusSms;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "created_at", nullable = false)
	private ZonedDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private ZonedDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Istanbul"));
		this.createdAt = now;
		this.updatedAt = now;
		if (status == null) {
			status = "Pending";
		}
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = ZonedDateTime.now();
	}
}