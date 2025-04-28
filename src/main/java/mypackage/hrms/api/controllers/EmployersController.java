package mypackage.hrms.api.controllers;

import mypackage.hrms.business.abstracts.EmployersService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.EmployerContacts;
import mypackage.hrms.entities.concretes.Employers;
import mypackage.hrms.entities.concretes.JobPostings;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/employers")
public class EmployersController implements EmployersControllerDocs {

	private final EmployersService employersService;

	public EmployersController(EmployersService employersService) {
		this.employersService = employersService;
	}

	@GetMapping("/getall")
	public ResponseEntity<DataNotification<List<Employers>>> getAll() {
		return ResponseEntity.ok(employersService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<Notification> add(@RequestBody Employers employer) {
		Notification notification = employersService.add(employer);
		return handleNotificationResponse(notification, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Notification> update(@RequestBody Employers employer) {
		Notification notification = employersService.update(employer);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Notification> delete(@PathVariable int id) {
		Notification notification = employersService.delete(id);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@PostMapping(value = "/verify-kyc-employers/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Notification> verifyKYCEmployers(@PathVariable("id") int id, @RequestPart("file") MultipartFile file) {
		Notification notification = employersService.verifyKYCEmployers(id, file);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@PostMapping("/verify-email-employers")
	public ResponseEntity<Notification> verifyEmailEmployers(@RequestParam String email, @RequestParam String code) {
		Notification notification = employersService.verifyEmailEmployers(email, code);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@GetMapping("/find-by-tax-number/{taxNumber}")
	public ResponseEntity<DataNotification<Employers>> findByTaxNumber(@PathVariable String taxNumber) {
		DataNotification<Employers> notification = employersService.findByTaxNumber(taxNumber);
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("not found")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notification);
			}
			return ResponseEntity.badRequest().body(notification);
		}
		return ResponseEntity.ok(notification);
	}

	@GetMapping("/{employerId}/contacts")
	public ResponseEntity<DataNotification<List<EmployerContacts>>> getContactsByEmployerId(@PathVariable int employerId) {
		return ResponseEntity.ok(employersService.getContactsByEmployerId(employerId));
	}

	@PostMapping("/contacts/add")
	public ResponseEntity<Notification> addContact(@RequestBody EmployerContacts contact) {
		Notification notification = employersService.addContact(contact);
		return handleNotificationResponse(notification, HttpStatus.CREATED);
	}

	@PutMapping("/contacts/update")
	public ResponseEntity<Notification> updateContact(@RequestBody EmployerContacts contact) {
		Notification notification = employersService.updateContact(contact);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@DeleteMapping("/contacts/delete/{contactId}")
	public ResponseEntity<Notification> deleteContact(@PathVariable long contactId) {
		Notification notification = employersService.deleteContact(contactId);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@GetMapping("/{employerId}/job-postings")
	public ResponseEntity<DataNotification<List<JobPostings>>> getJobPostingsByEmployerId(@PathVariable int employerId) {
		return ResponseEntity.ok(employersService.getJobPostingsByEmployerId(employerId));
	}

	@PostMapping("/job-postings/add")
	public ResponseEntity<Notification> addJobPosting(@RequestBody JobPostings jobPosting) {
		Notification notification = employersService.addJobPosting(jobPosting);
		return handleNotificationResponse(notification, HttpStatus.CREATED);
	}

	@PutMapping("/job-postings/update")
	public ResponseEntity<Notification> updateJobPosting(@RequestBody JobPostings jobPosting) {
		Notification notification = employersService.updateJobPosting(jobPosting);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@DeleteMapping("/job-postings/delete/{jobPostingId}")
	public ResponseEntity<Notification> deleteJobPosting(@PathVariable long jobPostingId) {
		Notification notification = employersService.deleteJobPosting(jobPostingId);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	private ResponseEntity<Notification> handleNotificationResponse(Notification notification, HttpStatus successStatus) {
		if (!notification.isSucceed()) {
			if (notification.getMessage().contains("not found")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notification);
			}
			return ResponseEntity.badRequest().body(notification);
		}
		return ResponseEntity.status(successStatus).body(notification);
	}
}