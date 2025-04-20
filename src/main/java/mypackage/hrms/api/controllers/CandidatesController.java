package mypackage.hrms.api.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import mypackage.hrms.business.abstracts.CandidatesService;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.entities.concretes.Candidates;

@RestController
@RequestMapping("/api/candidates")

public class CandidatesController {

	private CandidatesService candidatesService;

	@Autowired
	public CandidatesController(CandidatesService candidatesService) {
		super();
		this.candidatesService = candidatesService;
	}

	@Operation(
			summary = "Get All Candidates",
			description = "Retrieves a list of all candidates in the system.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Candidates retrieved successfully.",
							content = @Content(
									schema = @Schema(implementation = DataNotification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "Candidates retrieved successfully.",
                            "data": [
                                {
                                    "id": 1,
                                    "email" : "john.doe@email.com",
                                    "password" : "password1",
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "nationalID": 12345678901,
                                    "dateOfBirth" : "1996-04-07"
                                },
                                {
                                    "id": 2,
                                    "email" : "jane.smith@email.com",
                                    "password" : "password2",
                                    "firstName": "Jane",
                                    "lastName": "Smith",
                                    "nationalID": 98765432109,
                                    "dateOfBirth" : "1996-04-07"
                                }
                            ]
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "200",
							description = "No candidates found.",
							content = @Content(
									schema = @Schema(implementation = DataNotification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "No candidates found.",
                            "data": []
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "500",
							description = "Internal server error.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "An unexpected error occurred."
                        }
                        """
									)
							)
					)
			}
	)
	@GetMapping("/getall")
	public ResponseEntity<DataNotification<List<Candidates>>> getAll() {
		return ResponseEntity.ok(candidatesService.getAll());
	}

	@Operation(
			summary = "Add a New Candidate",
			description = "Creates a new candidate and associated user in the system.",
			requestBody = @RequestBody(
					description = "Candidate data including user information.",
					required = true,
					content = @Content(
							schema = @Schema(implementation = Candidates.class),
							examples = @ExampleObject(
									value = """
                    {
                    	"email" : "john.doe@email.com",
                    	"password" : "password1",
                    	"firstName": "John",
                    	"lastName": "Doe",
                    	"nationalID": 12345678901,
                    	"dateOfBirth" : "1996-04-07"
                    }
                    """
							)
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Candidate added successfully.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "Candidate added successfully."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Invalid candidate data or email already exists.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "Email already exists or invalid data."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "500",
							description = "Internal server error.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "An unexpected error occurred."
                        }
                        """
									)
							)
					)
			}
	)
	@PostMapping("/add")
	public ResponseEntity<Notification> add(@RequestBody Candidates candidate) {
		Notification notification = candidatesService.add(candidate);
		if (!notification.isSucceed()) {
			if (notification.getMessage().equals("Email already exists or invalid data.")) {
				return ResponseEntity.badRequest().body(notification);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notification);
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(notification);
	}

	@Operation(
			summary = "Update Candidate Information",
			description = "Updates the information of an existing candidate and associated user.",
			requestBody = @RequestBody(
					description = "Updated candidate data.",
					required = true,
					content = @Content(
							schema = @Schema(implementation = Candidates.class),
							examples = @ExampleObject(
									value = """  
                    {
                        "id": 1,
                    	"email" : "john.doe@email.com",
                    	"password" : "password1",
                    	"firstName": "John",
                    	"lastName": "Doe",
                    	"nationalID": 12345678901,
                    	"dateOfBirth" : "1996-04-07"
                    }
                    """
							)
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Candidate updated successfully.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "Candidate updated successfully."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Invalid data or candidate not found.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "Invalid candidate data."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "500",
							description = "Internal server error.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "An unexpected error occurred."
                        }
                        """
									)
							)
					)
			}
	)
	@PutMapping("/update")
	public ResponseEntity<Notification> update(@RequestBody Candidates candidate) {
		Notification notification = candidatesService.update(candidate);
		if (!notification.isSucceed()) {
			if (notification.getMessage().equals("Invalid data or candidate not found.")) {
				return ResponseEntity.badRequest().body(notification);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notification);
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(notification);
	}

	@Operation(
			summary = "Delete a Candidate",
			description = "Deletes a candidate and associated user by ID.",
			parameters = {
					@Parameter(
							name = "id",
							description = "ID of the candidate to delete",
							required = true,
							in = ParameterIn.PATH,
							schema = @Schema(type = "integer", format = "int64", example = "1")
					)
			},
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Candidate deleted successfully.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "Candidate deleted successfully."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "404",
							description = "Candidate not found.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "Candidate not found."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "500",
							description = "Internal server error.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "An unexpected error occurred."
                        }
                        """
									)
							)
					)
			}
	)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Notification> delete(@PathVariable int id) {
		Notification notification = candidatesService.delete(id);
		if (!notification.isSucceed()) {
			if (notification.getMessage().equals("Candidate not found.")) {
				return ResponseEntity.badRequest().body(notification);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notification);
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(notification);
	}

	@Operation(
			summary = "Verify KYC for a Candidate",
			description = "Uploads a document to verify KYC status for a candidate.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "KYC verification data including a document file and user ID.",
					required = true,
					content = @Content(
							mediaType = "multipart/form-data",
							examples = @ExampleObject(
									value = """
                    {
                        "userId": 1,
                        "file": "document.pdf"
                    }
                    """
							)
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "KYC verification successful.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "KYC verification successful."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Invalid request or missing file.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "Invalid request or missing file."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "404",
							description = "Candidate not found.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "Candidate not found."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "500",
							description = "Internal server error.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "An unexpected error occurred."
                        }
                        """
									)
							)
					)
			}
	)
	@PostMapping(value="/verify-kyc-candidates/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Notification> verifyKYCCandidate(
			@PathVariable("id") @Parameter(description = "Candidate ID") int id,
			@RequestPart("file") @Parameter(description = "Document file for KYC verification") MultipartFile file) {
		Notification notification = candidatesService.verifyKYCCandidate(id, file);
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("Candidate not found.")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notification);
			} else if (notification.getMessage().contains("Invalid request or missing file.")) {
				return ResponseEntity.badRequest().body(notification);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notification);
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(notification);
	}

	@Operation(
			summary = "Verify Candidate with Email",
			description = "Verifies a candidate's email using a verification code, updating their status.",
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
					@ApiResponse(
							responseCode = "200",
							description = "Candidate verified successfully.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "Candidate verified successfully."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Invalid verification code or email.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "Invalid verification code or email."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "404",
							description = "Candidate not found.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "Candidate not found."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "500",
							description = "Internal server error.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "An unexpected error occurred."
                        }
                        """
									)
							)
					)
			}
	)
	@PostMapping("/verify-email-candidates")
	public ResponseEntity<Notification> verifyEmailCandidate(@RequestParam String email, @RequestParam String code) {
		Notification notification = candidatesService.verifyEmailCandidate(email, code);
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("Candidate not found.")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notification);
			} else if (notification.getMessage().contains("Invalid verification code or email.")) {
				return ResponseEntity.badRequest().body(notification);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notification);
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(notification);
	}

}