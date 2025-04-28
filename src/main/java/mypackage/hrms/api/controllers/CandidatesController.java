package mypackage.hrms.api.controllers;

import mypackage.hrms.business.abstracts.CandidatesService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidatesController implements CandidatesControllerDocs {

	private final CandidatesService candidatesService;

	public CandidatesController(CandidatesService candidatesService) {
		this.candidatesService = candidatesService;
	}

	@GetMapping("/getall")
	public ResponseEntity<DataNotification<List<Candidates>>> getAll() {
		return ResponseEntity.ok(candidatesService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<Notification> add(@RequestBody Candidates candidate) {
		Notification notification = candidatesService.add(candidate);
		return handleNotificationResponse(notification, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Notification> update(@RequestBody Candidates candidate) {
		Notification notification = candidatesService.update(candidate);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Notification> delete(@PathVariable int id) {
		Notification notification = candidatesService.delete(id);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@PostMapping(value = "/verify-kyc-candidates/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Notification> verifyKYCCandidate(@PathVariable("id") int id, @RequestPart("file") MultipartFile file) {
		Notification notification = candidatesService.verifyKYCCandidate(id, file);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@PostMapping("/verify-email-candidates")
	public ResponseEntity<Notification> verifyEmailCandidate(@RequestParam String email, @RequestParam String code) {
		Notification notification = candidatesService.verifyEmailCandidate(email, code);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@GetMapping("/{candidateId}/educations")
	public ResponseEntity<DataNotification<List<CandidateEducations>>> getEducationsByCandidateId(@PathVariable int candidateId) {
		return ResponseEntity.ok(candidatesService.getEducationsByCandidateId(candidateId));
	}

	@PostMapping("/educations/add")
	public ResponseEntity<Notification> addEducation(@RequestBody CandidateEducations education) {
		Notification notification = candidatesService.addEducation(education);
		return handleNotificationResponse(notification, HttpStatus.CREATED);
	}

	@PutMapping("/educations/update")
	public ResponseEntity<Notification> updateEducation(@RequestBody CandidateEducations education) {
		Notification notification = candidatesService.updateEducation(education);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@DeleteMapping("/educations/delete/{educationId}")
	public ResponseEntity<Notification> deleteEducation(@PathVariable long educationId) {
		Notification notification = candidatesService.deleteEducation(educationId);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@GetMapping("/{candidateId}/experiences")
	public ResponseEntity<DataNotification<List<CandidateExperiences>>> getExperiencesByCandidateId(@PathVariable int candidateId) {
		return ResponseEntity.ok(candidatesService.getExperiencesByCandidateId(candidateId));
	}

	@PostMapping("/experiences/add")
	public ResponseEntity<Notification> addExperience(@RequestBody CandidateExperiences experience) {
		Notification notification = candidatesService.addExperience(experience);
		return handleNotificationResponse(notification, HttpStatus.CREATED);
	}

	@PutMapping("/experiences/update")
	public ResponseEntity<Notification> updateExperience(@RequestBody CandidateExperiences experience) {
		Notification notification = candidatesService.updateExperience(experience);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@DeleteMapping("/experiences/delete/{experienceId}")
	public ResponseEntity<Notification> deleteExperience(@PathVariable long experienceId) {
		Notification notification = candidatesService.deleteExperience(experienceId);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@GetMapping("/{candidateId}/skills")
	public ResponseEntity<DataNotification<List<CandidateSkills>>> getSkillsByCandidateId(@PathVariable int candidateId) {
		return ResponseEntity.ok(candidatesService.getSkillsByCandidateId(candidateId));
	}

	@PostMapping("/skills/add")
	public ResponseEntity<Notification> addSkill(@RequestBody CandidateSkills skill) {
		Notification notification = candidatesService.addSkill(skill);
		return handleNotificationResponse(notification, HttpStatus.CREATED);
	}

	@PutMapping("/skills/update")
	public ResponseEntity<Notification> updateSkill(@RequestBody CandidateSkills skill) {
		Notification notification = candidatesService.updateSkill(skill);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@DeleteMapping("/skills/delete/{skillId}")
	public ResponseEntity<Notification> deleteSkill(@PathVariable long skillId) {
		Notification notification = candidatesService.deleteSkill(skillId);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@GetMapping("/{candidateId}/languages")
	public ResponseEntity<DataNotification<List<CandidateLanguages>>> getLanguagesByCandidateId(@PathVariable int candidateId) {
		return ResponseEntity.ok(candidatesService.getLanguagesByCandidateId(candidateId));
	}

	@PostMapping("/languages/add")
	public ResponseEntity<Notification> addLanguage(@RequestBody CandidateLanguages language) {
		Notification notification = candidatesService.addLanguage(language);
		return handleNotificationResponse(notification, HttpStatus.CREATED);
	}

	@PutMapping("/languages/update")
	public ResponseEntity<Notification> updateLanguage(@RequestBody CandidateLanguages language) {
		Notification notification = candidatesService.updateLanguage(language);
		return handleNotificationResponse(notification, HttpStatus.OK);
	}

	@DeleteMapping("/languages/delete/{languageId}")
	public ResponseEntity<Notification> deleteLanguage(@PathVariable long languageId) {
		Notification notification = candidatesService.deleteLanguage(languageId);
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