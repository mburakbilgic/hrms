package mypackage.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mypackage.hrms.business.abstracts.CandidatesService;
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
	public List<Candidates> getAll() {
		return this.candidatesService.getAll();
	}
	
}
