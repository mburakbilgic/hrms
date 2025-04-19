package mypackage.hrms.api.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import mypackage.hrms.business.abstracts.CandidatesService;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.entities.concretes.Candidates;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/candidates")

public class CandidatesController {

	private CandidatesService candidatesService;

	@Autowired
	public CandidatesController(CandidatesService candidatesService) {
		super();
		this.candidatesService = candidatesService;
	}

	@GetMapping("/getall")
	public ResponseEntity<DataNotification<List<Candidates>>> getAll() {
		return ResponseEntity.ok(candidatesService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<Notification> add(@RequestBody Candidates candidate) {
		return ResponseEntity.ok(candidatesService.add(candidate));
	}

	@PutMapping("/update")
	public ResponseEntity<Notification> update(@RequestBody Candidates candidate) {
		return ResponseEntity.ok(candidatesService.update(candidate));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Notification> delete(@PathVariable int id) {
		return ResponseEntity.ok(candidatesService.delete(id));
	}

	@PostMapping(value="/verify-kyc-candidates/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Verify KYC for a candidate", description = "Uploads a document to verify KYC status for an candidate")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Verification successful"),
			@ApiResponse(responseCode = "400", description = "Invalid request or missing file"),
			@ApiResponse(responseCode = "404", description = "Candidate not found")
	})
	public ResponseEntity<Notification> verifyKYCCandidate(
			@PathVariable("id") @Parameter(description = "Candidate ID") int id,
			@RequestPart("file") @Parameter(description = "Document file for KYC verification") MultipartFile file) {
		return ResponseEntity.ok(candidatesService.verifyKYCCandidate(id, file));
	}

	@PostMapping("/verify-email-candidates")
	@Operation(
			summary = "Verify Candidate with Email",
			description = "Verifies an candidate's email using a verification code, updating their status.",
			parameters = {
					@Parameter(
							name = "email",
							description = "Candidate's email address",
							required = true,
							in = ParameterIn.QUERY,
							schema = @Schema(type = "string", example = "candidate@example.com")
					),
					@Parameter(
							name = "code",
							description = "Verification code sent to the candidate's email",
							required = true,
							in = ParameterIn.QUERY,
							schema = @Schema(type = "string", example = "123456")
					)
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Candidate verified successfully!"),
					@ApiResponse(responseCode = "400", description = "Invalid verification code or email!"),
					@ApiResponse(responseCode = "404", description = "Candidate not found.")
			}
	)
	public ResponseEntity<Notification> verifyEmailCandidate(@RequestParam String email, @RequestParam String code) {
		return ResponseEntity.ok(candidatesService.verifyEmailCandidate(email, code));
	}

}