package mypackage.hrms.entities.concretes;

import java.time.LocalDate;

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
@Table(name = "hrms_personels")

public class HrmsPersonels extends Users {

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "national_identity")
	private long nationalID;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

}