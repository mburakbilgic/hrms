package mypackage.hrms.api.controllers;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.*;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidatesControllerDocs {

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
                                                        "email": "john.doe@example.com",
                                                        "password": "hashed_password",
                                                        "firstName": "John",
                                                        "lastName": "Doe",
                                                        "nationalIdentity": "12345678901",
                                                        "dateOfBirth": "1990-01-01",
                                                        "phoneNumber": "+905551234567",
                                                        "address": "123 Main St, Istanbul",
                                                        "gender": "Male",
                                                        "cvFilePath": "/uploads/cvs/john_doe_cv.pdf",
                                                        "linkedinProfile": "https://linkedin.com/in/johndoe",
                                                        "githubProfile": "https://github.com/johndoe"
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
    ResponseEntity<DataNotification<List<Candidates>>> getAll();

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
                                        "email": "john.doe@example.com",
                                        "password": "password123",
                                        "firstName": "John",
                                        "lastName": "Doe",
                                        "nationalIdentity": "12345678901",
                                        "dateOfBirth": "1990-01-01",
                                        "phoneNumber": "+905551234567",
                                        "address": "123 Main St, Istanbul",
                                        "gender": "Male",
                                        "cvFilePath": "/uploads/cvs/john_doe_cv.pdf",
                                        "linkedinProfile": "https://linkedin.com/in/johndoe",
                                        "githubProfile": "https://github.com/johndoe"
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
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
                            description = "Invalid candidate data or email/national ID already exists.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid data or email/national ID already exists."
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
    ResponseEntity<Notification> add(Candidates candidate);

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
                                        "email": "john.doe@example.com",
                                        "password": "newpassword123",
                                        "firstName": "John",
                                        "lastName": "Doe",
                                        "nationalIdentity": "12345678901",
                                        "dateOfBirth": "1990-01-01",
                                        "phoneNumber": "+905551234567",
                                        "address": "123 Main St, Istanbul",
                                        "gender": "Male",
                                        "cvFilePath": "/uploads/cvs/john_doe_cv.pdf",
                                        "linkedinProfile": "https://linkedin.com/in/johndoe",
                                        "githubProfile": "https://github.com/johndoe"
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
                                                "message": "Invalid data or candidate not found."
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
    ResponseEntity<Notification> update(Candidates candidate);

    @Operation(
            summary = "Delete a Candidate",
            description = "Deletes a candidate and associated user by ID.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the candidate to delete",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
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
    ResponseEntity<Notification> delete(int id);

    @Operation(
            summary = "Verify KYC for a Candidate",
            description = "Uploads a document to verify KYC status for a candidate.",
            requestBody = @RequestBody(
                    description = "KYC verification data including a document file.",
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
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Candidate ID",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
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
                                                "message": "KYC verified successfully."
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
    ResponseEntity<Notification> verifyKYCCandidate(int id, MultipartFile file);

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
    ResponseEntity<Notification> verifyEmailCandidate(String email, String code);

    @Operation(
            summary = "Get Educations by Candidate ID",
            description = "Retrieves all educations for a specific candidate.",
            parameters = {
                    @Parameter(
                            name = "candidateId",
                            description = "ID of the candidate",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Educations retrieved successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Educations retrieved successfully.",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "candidateId": 1,
                                                        "educationLevel": "Bachelor's Degree",
                                                        "university": "Istanbul University",
                                                        "department": "Computer Engineering",
                                                        "graduationYear": 2015
                                                    }
                                                ]
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "No educations found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "No educations found.",
                                                "data": []
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Candidate not found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Candidate not found.",
                                                "data": null
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
    ResponseEntity<DataNotification<List<CandidateEducations>>> getEducationsByCandidateId(int candidateId);

    @Operation(
            summary = "Add Education for a Candidate",
            description = "Adds a new education record for a candidate.",
            requestBody = @RequestBody(
                    description = "Education data for the candidate.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CandidateEducations.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "candidate": {"id": 1},
                                        "educationLevel": "Bachelor's Degree",
                                        "university": "Istanbul University",
                                        "department": "Computer Engineering",
                                        "graduationYear": 2015
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Education added successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Education added successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid education data.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid education data."
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
    ResponseEntity<Notification> addEducation(CandidateEducations education);

    @Operation(
            summary = "Update Education for a Candidate",
            description = "Updates an existing education record for a candidate.",
            requestBody = @RequestBody(
                    description = "Updated education data.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CandidateEducations.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "id": 1,
                                        "candidate": {"id": 1},
                                        "educationLevel": "Master's Degree",
                                        "university": "Bogazici University",
                                        "department": "Software Engineering",
                                        "graduationYear": 2018
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Education updated successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Education updated successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid education data or education not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid education data or education not found."
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
    ResponseEntity<Notification> updateEducation(CandidateEducations education);

    @Operation(
            summary = "Delete Education for a Candidate",
            description = "Deletes an education record for a candidate.",
            parameters = {
                    @Parameter(
                            name = "educationId",
                            description = "ID of the education record to delete",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Education deleted successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Education deleted successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Education not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Education not found."
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
    ResponseEntity<Notification> deleteEducation(long educationId);

    @Operation(
            summary = "Get Experiences by Candidate ID",
            description = "Retrieves all experiences for a specific candidate.",
            parameters = {
                    @Parameter(
                            name = "candidateId",
                            description = "ID of the candidate",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Experiences retrieved successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Experiences retrieved successfully.",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "candidateId": 1,
                                                        "jobTitle": {"id": 1, "title": "Software Engineer"},
                                                        "companyName": "Tech Corp",
                                                        "startDate": "2020-01-01",
                                                        "endDate": null,
                                                        "description": "Developed web applications."
                                                    }
                                                ]
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "No experiences found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "No experiences found.",
                                                "data": []
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Candidate not found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Candidate not found.",
                                                "data": null
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
    ResponseEntity<DataNotification<List<CandidateExperiences>>> getExperiencesByCandidateId(int candidateId);

    @Operation(
            summary = "Add Experience for a Candidate",
            description = "Adds a new experience record for a candidate.",
            requestBody = @RequestBody(
                    description = "Experience data for the candidate.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CandidateExperiences.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "candidate": {"id": 1},
                                        "jobTitle": {"id": 1},
                                        "companyName": "Tech Corp",
                                        "startDate": "2020-01-01",
                                        "endDate": null,
                                        "description": "Developed web applications."
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Experience added successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Experience added successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid experience data.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid experience data."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Candidate or job title not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Candidate or job title not found."
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
    ResponseEntity<Notification> addExperience(CandidateExperiences experience);

    @Operation(
            summary = "Update Experience for a Candidate",
            description = "Updates an existing experience record for a candidate.",
            requestBody = @RequestBody(
                    description = "Updated experience data.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CandidateExperiences.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "id": 1,
                                        "candidate": {"id": 1},
                                        "jobTitle": {"id": 1},
                                        "companyName": "Tech Corp",
                                        "startDate": "2020-01-01",
                                        "endDate": "2023-01-01",
                                        "description": "Developed and maintained web applications."
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Experience updated successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Experience updated successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid experience data or experience not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid experience data or experience not found."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Candidate or job title not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Candidate or job title not found."
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
    ResponseEntity<Notification> updateExperience(CandidateExperiences experience);

    @Operation(
            summary = "Delete Experience for a Candidate",
            description = "Deletes an experience record for a candidate.",
            parameters = {
                    @Parameter(
                            name = "experienceId",
                            description = "ID of the experience record to delete",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Experience deleted successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Experience deleted successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Experience not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Experience not found."
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
    ResponseEntity<Notification> deleteExperience(long experienceId);

    @Operation(
            summary = "Get Skills by Candidate ID",
            description = "Retrieves all skills for a specific candidate.",
            parameters = {
                    @Parameter(
                            name = "candidateId",
                            description = "ID of the candidate",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Skills retrieved successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Skills retrieved successfully.",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "candidateId": 1,
                                                        "skillName": "Java",
                                                        "proficiencyLevel": "Advanced"
                                                    }
                                                ]
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "No skills found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "No skills found.",
                                                "data": []
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Candidate not found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Candidate not found.",
                                                "data": null
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
    ResponseEntity<DataNotification<List<CandidateSkills>>> getSkillsByCandidateId(int candidateId);

    @Operation(
            summary = "Add Skill for a Candidate",
            description = "Adds a new skill record for a candidate.",
            requestBody = @RequestBody(
                    description = "Skill data for the candidate.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CandidateSkills.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "candidate": {"id": 1},
                                        "skillName": "Java",
                                        "proficiencyLevel": "Advanced"
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Skill added successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Skill added successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid skill data.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid skill data."
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
    ResponseEntity<Notification> addSkill(CandidateSkills skill);

    @Operation(
            summary = "Update Skill for a Candidate",
            description = "Updates an existing skill record for a candidate.",
            requestBody = @RequestBody(
                    description = "Updated skill data.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CandidateSkills.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "id": 1,
                                        "candidate": {"id": 1},
                                        "skillName": "Java",
                                        "proficiencyLevel": "Expert"
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Skill updated successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Skill updated successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid skill data or skill not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid skill data or skill not found."
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
    ResponseEntity<Notification> updateSkill(CandidateSkills skill);

    @Operation(
            summary = "Delete Skill for a Candidate",
            description = "Deletes a skill record for a candidate.",
            parameters = {
                    @Parameter(
                            name = "skillId",
                            description = "ID of the skill record to delete",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Skill deleted successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Skill deleted successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Skill not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Skill not found."
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
    ResponseEntity<Notification> deleteSkill(long skillId);

    @Operation(
            summary = "Get Languages by Candidate ID",
            description = "Retrieves all languages for a specific candidate.",
            parameters = {
                    @Parameter(
                            name = "candidateId",
                            description = "ID of the candidate",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Languages retrieved successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Languages retrieved successfully.",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "candidateId": 1,
                                                        "language": "English",
                                                        "proficiencyLevel": "C1"
                                                    }
                                                ]
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "No languages found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "No languages found.",
                                                "data": []
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Candidate not found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Candidate not found.",
                                                "data": null
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
    ResponseEntity<DataNotification<List<CandidateLanguages>>> getLanguagesByCandidateId(int candidateId);

    @Operation(
            summary = "Add Language for a Candidate",
            description = "Adds a new language record for a candidate.",
            requestBody = @RequestBody(
                    description = "Language data for the candidate.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CandidateLanguages.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "candidate": {"id": 1},
                                        "language": "English",
                                        "proficiencyLevel": "C1"
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Language added successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Language added successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid language data.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid language data."
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
    ResponseEntity<Notification> addLanguage(CandidateLanguages language);

    @Operation(
            summary = "Update Language for a Candidate",
            description = "Updates an existing language record for a candidate.",
            requestBody = @RequestBody(
                    description = "Updated language data.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CandidateLanguages.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "id": 1,
                                        "candidate": {"id": 1},
                                        "language": "English",
                                        "proficiencyLevel": "C2"
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Language updated successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Language updated successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid language data or language not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid language data or language not found."
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
    ResponseEntity<Notification> updateLanguage(CandidateLanguages language);

    @Operation(
            summary = "Delete Language for a Candidate",
            description = "Deletes a language record for a candidate.",
            parameters = {
                    @Parameter(
                            name = "languageId",
                            description = "ID of the language record to delete",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Language deleted successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Language deleted successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Language not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Language not found."
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
    ResponseEntity<Notification> deleteLanguage(long languageId);
}