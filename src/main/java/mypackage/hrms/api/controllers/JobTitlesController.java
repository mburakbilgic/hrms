package mypackage.hrms.api.controllers;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import mypackage.hrms.business.abstracts.JobTitlesService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.JobTitles;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobTitlesController {
	private final JobTitlesService jobTitlesService;

	@Autowired
	public JobTitlesController(JobTitlesService jobTitlesService) {
		this.jobTitlesService = jobTitlesService;
	}

	@Operation(
			summary = "Get All Job Titles",
			description = "Retrieves a list of all job titles in the system.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Job titles retrieved successfully.",
							content = @Content(
									schema = @Schema(implementation = DataNotification.class),
									examples = @ExampleObject(
											value = """
                                            {
                                                "success": true,
                                                "message": "Job titles retrieved successfully.",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "title": "Software Engineer"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "title": "Data Analyst"
                                                    }
                                                ]
                                            }
                                            """
									)
							)
					),
					@ApiResponse(
							responseCode = "200",
							description = "No job titles found.",
							content = @Content(
									schema = @Schema(implementation = DataNotification.class),
									examples = @ExampleObject(
											value = """
                                            {
                                                "success": true,
                                                "message": "No job titles found.",
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
	public ResponseEntity<DataNotification<List<JobTitles>>> getAll() {
		return ResponseEntity.ok(jobTitlesService.getAll());
	}

	@Operation(
			summary = "Add a New Job Title",
			description = "Creates a new job title in the system.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Job title data.",
					required = true,
					content = @Content(
							schema = @Schema(implementation = JobTitles.class),
							examples = @ExampleObject(
									value = """
                                    {
                                        "title": "Software Engineer"
                                    }
                                    """
							)
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Job title added successfully.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                                            {
                                                "success": true,
                                                "message": "Job title added successfully."
                                            }
                                            """
									)
							)
					),
					@ApiResponse(
							responseCode = "400",
							description = "Invalid job title data (null or empty title).",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                                            {
                                                "success": false,
                                                "message": "Job title cannot be null or empty."
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
	public ResponseEntity<Notification> add(@RequestBody JobTitles jobTitles) {
		return handleNotificationResponse(jobTitlesService.add(jobTitles), HttpStatus.CREATED);
	}

	@Operation(
			summary = "Update Job Title Information",
			description = "Updates the information of an existing job title.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Updated job title data.",
					required = true,
					content = @Content(
							schema = @Schema(implementation = JobTitles.class),
							examples = @ExampleObject(
									value = """
                                    {
                                        "id": 1,
                                        "title": "Senior Software Engineer"
                                    }
                                    """
							)
					)
			),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Job title updated successfully.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                                            {
                                                "success": true,
                                                "message": "Job title updated successfully."
                                            }
                                            """
									)
							)
					),
					@ApiResponse(
							responseCode = "404",
							description = "Job title not found.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                                            {
                                                "success": false,
                                                "message": "Job title not found."
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
	public ResponseEntity<Notification> update(@RequestBody JobTitles jobTitles) {
		return handleNotificationResponseForUpdate(jobTitlesService.update(jobTitles));
	}

	@Operation(
			summary = "Delete Job Title",
			description = "Deletes a job title by its ID.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Job title deleted successfully.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                                            {
                                                "success": true,
                                                "message": "Job title deleted successfully."
                                            }
                                            """
									)
							)
					),
					@ApiResponse(
							responseCode = "404",
							description = "Job title not found.",
							content = @Content(
									schema = @Schema(implementation = Notification.class),
									examples = @ExampleObject(
											value = """
                                            {
                                                "success": false,
                                                "message": "Job title not found."
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
		return handleNotificationResponseForDelete(jobTitlesService.delete(id));
	}

	private ResponseEntity<Notification> handleNotificationResponse(Notification notification, HttpStatus successStatus) {
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("Job title already exists or invalid data.")) {
				return ResponseEntity.badRequest().body(notification);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notification);
		}
		return ResponseEntity.status(successStatus).body(notification);
	}

	private ResponseEntity<Notification> handleNotificationResponseForUpdate(Notification notification) {
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("not found")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notification);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notification);
		}
		return ResponseEntity.ok(notification);
	}

	private ResponseEntity<Notification> handleNotificationResponseForDelete(Notification notification) {
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("not found")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notification);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notification);
		}
		return ResponseEntity.ok(notification);
	}
}