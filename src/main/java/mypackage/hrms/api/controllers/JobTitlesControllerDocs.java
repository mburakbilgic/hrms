package mypackage.hrms.api.controllers;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.JobTitles;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobTitlesControllerDocs {

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
                                                        "title": "Project Manager"
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
    ResponseEntity<DataNotification<List<JobTitles>>> getAll();

    @Operation(
            summary = "Add a New Job Title",
            description = "Creates a new job title in the system.",
            requestBody = @RequestBody(
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
                            description = "Invalid data or title already exists.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid data or title already exists."
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
    ResponseEntity<Notification> add(JobTitles jobTitle);

    @Operation(
            summary = "Update a Job Title",
            description = "Updates an existing job title in the system.",
            requestBody = @RequestBody(
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
                            responseCode = "400",
                            description = "Invalid data or job title not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid data or job title not found."
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
    ResponseEntity<Notification> update(JobTitles jobTitle);

    @Operation(
            summary = "Delete a Job Title",
            description = "Deletes a job title by ID.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the job title to delete",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", format = "int64", example = "1")
                    )
            },
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
    ResponseEntity<Notification> delete(Long id);
}