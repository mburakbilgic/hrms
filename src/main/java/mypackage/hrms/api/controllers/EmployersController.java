package mypackage.hrms.api.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mypackage.hrms.business.abstracts.EmployersService;
import mypackage.hrms.core.utilities.notifications.*;
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

	@Operation(
			summary = "Get All Employers",
			description = "Retrieves a list of all employers in the system.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Employers retrieved successfully.",
							content = @Content(
									schema = @Schema(implementation = DataNotification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "Employers retrieved successfully.",
                            "data": [
                                {
                                    "id": 1,
                                    "email" : "info@company1.com",
                                    "password" : "password1",
                                    "companyName": "Company 1",
                                    "webAdress": "www.company1.com",
                                    "phoneNumber": "5554441234",
                                },
                                {
                                    "id": 2,
                                    "email" : "info@company2.com",
                                    "password" : "password2",
                                    "companyName": "Company 2",
                                    "webAdress": "www.company2.com",
                                    "phoneNumber": "5665554321",
                                }
                            ]
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "200",
							description = "No employers found.",
							content = @Content(
									schema = @Schema(implementation = DataNotification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "No employers found.",
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
	public ResponseEntity<DataNotification<List<Employers>>> getAll() {
		return ResponseEntity.ok(employersService.getAll());
	}

	@Operation(
			summary = "Add a New Employer",
			description = "Creates a new employer and associated user in the system.",
			requestBody = @RequestBody(
					description = "Employer data including user information.",
					required = true,
					content = @Content(
							schema = @Schema(implementation = Employers.class),
							examples = @ExampleObject(
									value = """
                    {
                        "id": 1,
                    	"email" : "info@company1.com",
                    	"password" : "password1",
                    	"companyName": "Company 1",
                    	"webAdress": "www.company1.com",
                    	"phoneNumber": "5554441234"
                    }
                    """
							)
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Employer added successfully.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "Employer added successfully."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Email already exists or invalid data.",
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
	public ResponseEntity<Notification> add(@RequestBody Employers employer) {
		Notification notification = employersService.add(employer);
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("Email or password cannot be null.")) {
				return ResponseEntity.badRequest().body(notification);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notification);
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(notification);
	}

	@Operation(
			summary = "Update Employer Information",
			description = "Updates the information of an existing employer and associated user.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Updated employer data.",
					required = true,
					content = @Content(
							schema = @Schema(implementation = Employers.class),
							examples = @ExampleObject(
									value = """  
                    {
                        "id": 1,
                    	"email" : "info@company1.com",
                    	"password" : "password1",
                    	"companyName": "Company 1",
                    	"webAdress": "www.company1.com",
                    	"phoneNumber": "5554441234"
                    }
                    """
							)
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Employer updated successfully.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "Employer updated successfully."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Invalid data or employer not found.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "Invalid data or employer not found."
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
	public ResponseEntity<Notification> update(@RequestBody Employers employer) {
		Notification notification = employersService.update(employer);
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("Employer not found.")) {
				return ResponseEntity.badRequest().body(notification);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notification);
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(notification);
	}

	@Operation(
			summary = "Delete an Employer",
			description = "Deletes an employer and associated user by ID.",
			parameters = {
					@Parameter(
							name = "id",
							description = "ID of the employer to delete",
							required = true,
							in = ParameterIn.PATH,
							schema = @Schema(type = "integer", format = "int64", example = "1")
					)
			},
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Employer deleted successfully.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "Employer deleted successfully."
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "404",
							description = "Employer not found.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "Employer not found."
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
		Notification notification = employersService.delete(id);
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("Employer not found.")) {
				return ResponseEntity.badRequest().body(notification);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notification);
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(notification);
	}

	@Operation(
			summary = "Verify KYC for a Employer",
			description = "Uploads a document to verify KYC status for a employer.",
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
							description = "Employer not found.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "Employer not found."
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
	@PostMapping(value="/verify-kyc-employers/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Notification> verifyKYCEmployers(
			@PathVariable("id") @Parameter(description = "Employer ID") int id,
			@RequestPart("file") @Parameter(description = "Document file for KYC verification") MultipartFile file) {
		Notification notification = employersService.verifyKYCEmployers(id, file);
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("Employer not found.")) {
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
			summary = "Verify Employer with Email",
			description = "Verifies a employer's email using a verification code, updating their status.",
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
					@ApiResponse(
							responseCode = "200",
							description = "Employer verified successfully.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": true,
                            "message": "Verification successfully."
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
                            "message": "Invalid verification code or email!"
                        }
                        """
									)
							)
					),
					@ApiResponse(
							responseCode = "404",
							description = "Employer not found.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                        {
                            "success": false,
                            "message": "Employer not found."
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
	@PostMapping(value="/verify-email-employers")
	public ResponseEntity<Notification> verifyEmailEmployers(@RequestParam String email, @RequestParam String code) {
		Notification notification = employersService.verifyEmailEmployers(email, code);
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("Employer not found.")) {
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