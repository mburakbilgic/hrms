package mypackage.hrms.business.abstracts;

import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.entities.concretes.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidatesService {
	DataNotification<List<Candidates>> getAll();
	Notification add(Candidates candidate);
	Notification update(Candidates candidate);
	Notification delete(int id);
	Notification verifyKYCCandidate(int id, MultipartFile document);
	Notification verifyEmailCandidate(String email, String code);

	// Education operations
	DataNotification<List<CandidateEducations>> getEducationsByCandidateId(int candidateId);
	Notification addEducation(CandidateEducations education);
	Notification updateEducation(CandidateEducations education);
	Notification deleteEducation(long educationId);

	// Experience operations
	DataNotification<List<CandidateExperiences>> getExperiencesByCandidateId(int candidateId);
	Notification addExperience(CandidateExperiences experience);
	Notification updateExperience(CandidateExperiences experience);
	Notification deleteExperience(long experienceId);

	// Skill operations
	DataNotification<List<CandidateSkills>> getSkillsByCandidateId(int candidateId);
	Notification addSkill(CandidateSkills skill);
	Notification updateSkill(CandidateSkills skill);
	Notification deleteSkill(long skillId);

	// Language operations
	DataNotification<List<CandidateLanguages>> getLanguagesByCandidateId(int candidateId);
	Notification addLanguage(CandidateLanguages language);
	Notification updateLanguage(CandidateLanguages language);
	Notification deleteLanguage(long languageId);
}