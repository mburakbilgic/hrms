package mypackage.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mypackage.hrms.business.abstracts.EmployersService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.Employers;

@RestController
@RequestMapping("/api/employers")

public class EmployersController {

	private EmployersService employersService;

	@Autowired
	public EmployersController(EmployersService employersService) {
		super();
		this.employersService = employersService;
	}

	@GetMapping("/getall")
	public ResponseEntity<DataNotification<List<Employers>>> getAll() {
		return ResponseEntity.ok(employersService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<Notification> add(@RequestBody Employers employer) {
		return ResponseEntity.ok(employersService.add(employer));
	}

	@PutMapping("/update")
	public ResponseEntity<Notification> update(@RequestBody Employers employers) {
		return ResponseEntity.ok(employersService.update(employers));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Notification> delete(@PathVariable int id) {
		return ResponseEntity.ok(employersService.delete(id));
	}

	@PostMapping("/verify/{id}")
	public ResponseEntity<Notification> verifyEmployers(@PathVariable int id) {
		return ResponseEntity.ok(employersService.verifyEmployers(id));
	}

}