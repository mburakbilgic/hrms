package mypackage.hrms.api.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mypackage.hrms.business.abstracts.EmployersService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.Employers;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employers")

public class EmployersController {

	private EmployersService employersService;

	@Autowired
	public EmployersController(EmployersService employersService) {
		super();
		this.employersService = employersService;
	}

	@GetMapping("/getall")
	public ResponseEntity<DataNotification<List<Employers>>> getAll() {
		return ResponseEntity.ok(employersService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<Notification> add(@RequestBody Employers employer) {
		return ResponseEntity.ok(employersService.add(employer));
	}

	@PutMapping("/update")
	public ResponseEntity<Notification> update(@RequestBody Employers employers) {
		return ResponseEntity.ok(employersService.update(employers));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Notification> delete(@PathVariable int id) {
		return ResponseEntity.ok(employersService.delete(id));
	}

	@PostMapping(value="/verify-kyc-employers/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Verify KYC for an employer", description = "Uploads a document to verify KYC status for an employer")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Verification successful"),
			@ApiResponse(responseCode = "400", description = "Invalid request or missing file"),
			@ApiResponse(responseCode = "404", description = "Employer not found")
	})
	public ResponseEntity<Notification> verifyKYCEmployers(
			@PathVariable("id") @Parameter(description = "Employer ID") int id,
			@RequestPart("file") @Parameter(description = "Document file for KYC verification") MultipartFile file) {
		return ResponseEntity.ok(employersService.verifyKYCEmployers(id, file));
	}

	@PostMapping(value="/verify-email-employers")
	@Operation(
			summary = "Verify Employers with Email",
			description = "Verifies an employer's email using a verification code, updating their status.",
			parameters = {
					@Parameter(
							name = "email",
							description = "Employer's email address",
							required = true,
							in = ParameterIn.QUERY,
							schema = @Schema(type = "string", example = "employer@example.com")
					),
					@Parameter(
							name = "code",
							description = "Verification code sent to the employer's email",
							required = true,
							in = ParameterIn.QUERY,
							schema = @Schema(type = "string", example = "123456")
					)
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Employer verified successfully!"),
					@ApiResponse(responseCode = "400", description = "Invalid verification code or email!"),
					@ApiResponse(responseCode = "404", description = "Employer not found.")
			}
	)
	public ResponseEntity<Notification> verifyEmailEmployers(@RequestParam String email, @RequestParam String code) {
		return ResponseEntity.ok(employersService.verifyEmailEmployers(email, code));
	}

}