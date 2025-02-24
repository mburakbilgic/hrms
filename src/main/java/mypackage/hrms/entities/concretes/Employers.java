package mypackage.hrms.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
@Table(name = "employers")

public class Employers extends Users {

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "web_address")
	private String webAdress;

	@Column(name = "phone_number")
	private String phoneNumber;

	@JsonIgnore
	@Column(name = "activate_status")
	private Boolean activateStatus;

}