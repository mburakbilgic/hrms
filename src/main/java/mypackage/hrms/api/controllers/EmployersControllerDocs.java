package mypackage.hrms.api.controllers;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.Employers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployersControllerDocs {

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
                                                        "email": "info@company1.com",
                                                        "password": "hashed_password",
                                                        "companyName": "Company 1",
                                                        "webAddress": "https://www.company1.com",
                                                        "phoneNumber": "+905554441234",
                                                        "taxNumber": "1234567890",
                                                        "industry": "Technology",
                                                        "companySize": "51-200",
                                                        "address": "123 Business St, Istanbul",
                                                        "description": "Leading tech company.",
                                                        "logoPath": "/uploads/logos/company1.png",
                                                        "linkedinPage": "https://linkedin.com/company/company1",
                                                        "isVerified": false
                                                    }
                                                ]
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No employers found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
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
    ResponseEntity<DataNotification<List<Employers>>> getAll();

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
                                        "email": "info@company1.com",
                                        "password": "password123",
                                        "companyName": "Company 1",
                                        "webAddress": "https://www.company1.com",
                                        "phoneNumber": "+905554441234",
                                        "taxNumber": "1234567890",
                                        "industry": "Technology",
                                        "companySize": "51-200",
                                        "address": "123 Business St, Istanbul",
                                        "description": "Leading tech company.",
                                        "logoPath": "/uploads/logos/company1.png",
                                        "linkedinPage": "https://linkedin.com/company/company1",
                                        "isVerified": false
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
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
                            description = "Invalid employer data or email/tax number already exists.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid data or email/tax number already exists."
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
    ResponseEntity<Notification> add(Employers employer);

    @Operation(
            summary = "Update Employer Information",
            description = "Updates the information of an existing employer and associated user.",
            requestBody = @RequestBody(
                    description = "Updated employer data.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Employers.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "id": 1,
                                        "email": "info@company1.com",
                                        "password": "newpassword123",
                                        "companyName": "Company 1",
                                        "webAddress": "https://www.company1.com",
                                        "phoneNumber": "+905554441234",
                                        "taxNumber": "1234567890",
                                        "industry": "Technology",
                                        "companySize": "51-200",
                                        "address": "123 Business St, Istanbul",
                                        "description": "Leading tech company.",
                                        "logoPath": "/uploads/logos/company1.png",
                                        "linkedinPage": "https://linkedin.com/company/company1",
                                        "isVerified": true
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
    ResponseEntity<Notification> update(Employers employer);

    @Operation(
            summary = "Delete an Employer",
            description = "Deletes an employer and associated user by ID.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the employer to delete",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
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
    ResponseEntity<Notification> delete(int id);

    @Operation(
            summary = "Verify KYC for an Employer",
            description = "Uploads a document to verify KYC status for an employer.",
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
                            description = "Employer ID",
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
                                                "message": "KYC verification successfully."
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
    ResponseEntity<Notification> verifyKYCEmployers(int id, MultipartFile file);

    @Operation(
            summary = "Verify Employer with Email",
            description = "Verifies an employer's email using a verification code, updating their status.",
            parameters = {
                    @Parameter(
                            name = "email",
                            description = "Employer's email address",
                            required = true,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", example = "info@company1.com")
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
                                                "message": "Invalid verification code or email."
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
    ResponseEntity<Notification> verifyEmailEmployers(String email, String code);
}