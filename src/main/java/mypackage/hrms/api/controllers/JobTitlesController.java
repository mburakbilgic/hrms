package mypackage.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mypackage.hrms.business.abstracts.JobTitlesService;
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
	public List<JobTitles> getAll() {
		return this.jobTitlesService.getAll();
	}

}
