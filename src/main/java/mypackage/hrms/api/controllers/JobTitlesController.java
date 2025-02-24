package mypackage.hrms.api.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mypackage.hrms.business.abstracts.JobTitlesService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.JobTitles;

@RestController
@RequestMapping("/api/jobs")
public class JobTitlesController {
	private JobTitlesService jobTitlesService;

	@Autowired
	public JobTitlesController(JobTitlesService jobTitlesService) {
		super();
		this.jobTitlesService = jobTitlesService;
	}

	@GetMapping("/getall")
	public ResponseEntity<DataNotification<List<JobTitles>>> getAll() {
		return ResponseEntity.ok(jobTitlesService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<Notification>  add(@RequestBody JobTitles jobTitles) {
		return ResponseEntity.ok(jobTitlesService.add(jobTitles));
	}

	@PutMapping("/update")
	public ResponseEntity<Notification> update(@RequestBody JobTitles jobTitles) {
		return ResponseEntity.ok(jobTitlesService.update(jobTitles));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Notification> delete(@PathVariable int id) {
		return ResponseEntity.ok(jobTitlesService.delete(id));
	}

}