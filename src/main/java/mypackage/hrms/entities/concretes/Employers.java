package mypackage.hrms.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

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

}