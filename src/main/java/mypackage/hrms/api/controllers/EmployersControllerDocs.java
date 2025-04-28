package mypackage.hrms.api.controllers;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.EmployerContacts;
import mypackage.hrms.entities.concretes.Employers;
import mypackage.hrms.entities.concretes.JobPostings;
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
                                                        "taxNumber": "1234567890",
                                                        "industry": "Technology",
                                                        "companySize": "51-200",
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
                                        "taxNumber": "1234567890",
                                        "industry": "Technology",
                                        "companySize": "51-200",
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
                                        "taxNumber": "1234567890",
                                        "industry": "Technology",
                                        "companySize": "51-200",
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

    @Operation(
            summary = "Find Employer by Tax Number",
            description = "Retrieves an employer by their tax number.",
            parameters = {
                    @Parameter(
                            name = "taxNumber",
                            description = "Tax number of the employer",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", example = "1234567890")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Employer retrieved successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Employer retrieved successfully.",
                                                "data": {
                                                    "id": 1,
                                                    "email": "info@company1.com",
                                                    "companyName": "Company 1",
                                                    "webAddress": "https://www.company1.com",
                                                    "taxNumber": "1234567890",
                                                    "industry": "Technology",
                                                    "companySize": "51-200",
                                                    "description": "Leading tech company.",
                                                    "logoPath": "/uploads/logos/company1.png",
                                                    "linkedinPage": "https://linkedin.com/company/company1",
                                                    "isVerified": false
                                                }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Employer not found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Employer not found.",
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
    ResponseEntity<DataNotification<Employers>> findByTaxNumber(String taxNumber);

    @Operation(
            summary = "Get Contacts by Employer ID",
            description = "Retrieves all contacts for a specific employer.",
            parameters = {
                    @Parameter(
                            name = "employerId",
                            description = "ID of the employer",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contacts retrieved successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Contacts retrieved successfully.",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "employerId": 1,
                                                        "contactType": "PHONE",
                                                        "contactValue": "+905554441234",
                                                        "isPrimary": true
                                                    },
                                                    {
                                                        "id": 2,
                                                        "employerId": 1,
                                                        "contactType": "ADDRESS",
                                                        "contactValue": "123 Business St, Istanbul",
                                                        "isPrimary": true
                                                    }
                                                ]
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "No contacts found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "No contacts found.",
                                                "data": []
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Employer not found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Employer not found.",
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
    ResponseEntity<DataNotification<List<EmployerContacts>>> getContactsByEmployerId(int employerId);

    @Operation(
            summary = "Add Contact for an Employer",
            description = "Adds a new contact record for an employer.",
            requestBody = @RequestBody(
                    description = "Contact data for the employer.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EmployerContacts.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "employer": {"id": 1},
                                        "contactType": "PHONE",
                                        "contactValue": "+905554441234",
                                        "isPrimary": true
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Contact added successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Contact added successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid contact data.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid contact data."
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
    ResponseEntity<Notification> addContact(EmployerContacts contact);

    @Operation(
            summary = "Update Contact for an Employer",
            description = "Updates an existing contact record for an employer.",
            requestBody = @RequestBody(
                    description = "Updated contact data.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EmployerContacts.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "id": 1,
                                        "employer": {"id": 1},
                                        "contactType": "PHONE",
                                        "contactValue": "+905554441235",
                                        "isPrimary": true
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contact updated successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Contact updated successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid contact data or contact not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid contact data or contact not found."
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
    ResponseEntity<Notification> updateContact(EmployerContacts contact);

    @Operation(
            summary = "Delete Contact for an Employer",
            description = "Deletes a contact record for an employer.",
            parameters = {
                    @Parameter(
                            name = "contactId",
                            description = "ID of the contact record to delete",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contact deleted successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Contact deleted successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Contact not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Contact not found."
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
    ResponseEntity<Notification> deleteContact(long contactId);

    @Operation(
            summary = "Get Job Postings by Employer ID",
            description = "Retrieves all job postings for a specific employer.",
            parameters = {
                    @Parameter(
                            name = "employerId",
                            description = "ID of the employer",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Job postings retrieved successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Job postings retrieved successfully.",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "employerId": 1,
                                                        "jobTitle": {"id": 1, "title": "Software Engineer"},
                                                        "description": "Develop and maintain web applications.",
                                                        "status": "Open",
                                                        "createdAt": "2025-04-28T10:00:00Z",
                                                        "updatedAt": "2025-04-28T10:00:00Z"
                                                    }
                                                ]
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "No job postings found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "No job postings found.",
                                                "data": []
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Employer not found.",
                            content = @Content(
                                    schema = @Schema(implementation = DataNotification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Employer not found.",
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
    ResponseEntity<DataNotification<List<JobPostings>>> getJobPostingsByEmployerId(int employerId);

    @Operation(
            summary = "Add Job Posting for an Employer",
            description = "Adds a new job posting record for an employer.",
            requestBody = @RequestBody(
                    description = "Job posting data for the employer.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = JobPostings.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "employer": {"id": 1},
                                        "jobTitle": {"id": 1},
                                        "description": "Develop and maintain web applications.",
                                        "status": "Open"
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Job posting added successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Job posting added successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid job posting data.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid job posting data."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Employer or job title not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Employer or job title not found."
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
    ResponseEntity<Notification> addJobPosting(JobPostings jobPosting);

    @Operation(
            summary = "Update Job Posting for an Employer",
            description = "Updates an existing job posting record for an employer.",
            requestBody = @RequestBody(
                    description = "Updated job posting data.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = JobPostings.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "id": 1,
                                        "employer": {"id": 1},
                                        "jobTitle": {"id": 1},
                                        "description": "Develop and maintain web applications.",
                                        "status": "Closed"
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Job posting updated successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Job posting updated successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid job posting data or job posting not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Invalid job posting data or job posting not found."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Employer or job title not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Employer or job title not found."
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
    ResponseEntity<Notification> updateJobPosting(JobPostings jobPosting);

    @Operation(
            summary = "Delete Job Posting for an Employer",
            description = "Deletes a job posting record for an employer.",
            parameters = {
                    @Parameter(
                            name = "jobPostingId",
                            description = "ID of the job posting record to delete",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Job posting deleted successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": true,
                                                "message": "Job posting deleted successfully."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Job posting not found.",
                            content = @Content(
                                    schema = @Schema(implementation = Notification.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                                "success": false,
                                                "message": "Job posting not found."
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
    ResponseEntity<Notification> deleteJobPosting(long jobPostingId);
}