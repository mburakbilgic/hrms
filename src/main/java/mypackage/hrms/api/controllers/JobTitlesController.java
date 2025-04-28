package mypackage.hrms.api.controllers;

import mypackage.hrms.business.abstracts.JobTitlesService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.JobTitles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-titles")
public class JobTitlesController implements JobTitlesControllerDocs {

	private final JobTitlesService jobTitlesService;

	public JobTitlesController(JobTitlesService jobTitlesService) {
		this.jobTitlesService = jobTitlesService;
	}

	@GetMapping("/getall")
	public ResponseEntity<DataNotification<List<JobTitles>>> getAll() {
		return ResponseEntity.ok(jobTitlesService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<Notification> add(@RequestBody JobTitles jobTitle) {
		Notification notification = jobTitlesService.add(jobTitle);
		return handleNotificationResponse(notification, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Notification> update(@RequestBody JobTitles jobTitle) {
		Notification notification = jobTitlesService.update(jobTitle);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Notification> delete(@PathVariable Long id) {
		Notification notification = jobTitlesService.delete(id);
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