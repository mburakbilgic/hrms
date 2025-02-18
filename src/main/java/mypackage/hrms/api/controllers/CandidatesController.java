package mypackage.hrms.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/getall")
	public ResponseEntity<DataNotification<List<Candidates>>> getAll() {
		return ResponseEntity.ok(candidatesService.getAll());
	}

	@PutMapping("/update")
	public ResponseEntity<Notification> update(@RequestBody Candidates candidate) {
		return ResponseEntity.ok(candidatesService.update(candidate));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Notification> delete(@PathVariable int id) {
		return ResponseEntity.ok(candidatesService.delete(id));
	}

	@PostMapping("/verify/{id}")
	public ResponseEntity<Notification> verifyCandidate(@PathVariable int id) {
		return ResponseEntity.ok(candidatesService.verifyCandidate(id));
	}

}