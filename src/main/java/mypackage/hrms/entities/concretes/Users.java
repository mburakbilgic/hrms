package mypackage.hrms.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)

public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@JsonIgnore
	@Column(name = "activate_status_email", nullable = false)
	private Boolean activateStatusEmail = false;

	@JsonIgnore
	@Column(name = "activate_status_sms", nullable = false)
	private Boolean activateStatusSMS = false;

	@JsonIgnore
	@Column(name = "activate_status_kyc", nullable = false)
	private Boolean activateStatusKYC = false;
}