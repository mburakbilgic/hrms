package mypackage.hrms.business.concretes;

import mypackage.hrms.business.abstracts.CandidatesService;
import mypackage.hrms.core.VerificationService.VerificateKYC.VerificateKYC;
import mypackage.hrms.core.VerificationService.VerificateMail.MailVerificateService;
import mypackage.hrms.core.utilities.notifications.DataNotification;
import mypackage.hrms.core.utilities.notifications.Notification;
import mypackage.hrms.dataAccess.abstracts.*;
import mypackage.hrms.entities.concretes.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CandidatesManager implements CandidatesService {

	private final CandidatesDao candidatesDao;
	private final CandidateEducationsDao educationsDao;
	private final CandidateExperiencesDao experiencesDao;
	private final CandidateSkillsDao skillsDao;
	private final CandidateLanguagesDao languagesDao;
	private final VerificateKYC kycVerification;
	private final MailVerificateService mailVerificateService;

	public CandidatesManager(CandidatesDao candidatesDao,
							 CandidateEducationsDao educationsDao,
							 CandidateExperiencesDao experiencesDao,
							 CandidateSkillsDao skillsDao,
							 CandidateLanguagesDao languagesDao,
							 VerificateKYC kycVerification,
							 MailVerificateService mailVerificateService) {
		this.candidatesDao = candidatesDao;
		this.educationsDao = educationsDao;
		this.experiencesDao = experiencesDao;
		this.skillsDao = skillsDao;
		this.languagesDao = languagesDao;
		this.kycVerification = kycVerification;
		this.mailVerificateService = mailVerificateService;
	}

	@Override
	public DataNotification<List<Candidates>> getAll() {
		List<Candidates> candidates = candidatesDao.findAll();
		String message = candidates.isEmpty() ? "No candidates found." : "Candidates retrieved successfully.";
		return new DataNotification<>(candidates, true, message);
	}

	@Override
	public Notification add(Candidates candidate) {
		if (!isValidCandidate(candidate)) {
			return createErrorNotification("Invalid data or email/national ID already exists.");
		}

		candidate.setActivateStatusEmail(false);
		candidate.setActivateStatusKyc(false);
		candidate.setActivateStatusSms(false);
		candidate.setStatus("Pending");

		try {
			candidatesDao.save(candidate);
			String verificationCode = String.valueOf(new Random().nextInt(900000) + 100000);
			mailVerificateService.sendVerificationEmail(candidate.getEmail(), verificationCode);
			return createSuccessNotification("Candidate added successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to add candidate: " + e.getMessage());
		}
	}

	@Override
	public Notification update(Candidates candidate) {
		if (!candidatesDao.existsById(candidate.getId()) || !isValidCandidate(candidate)) {
			return createErrorNotification("Invalid data or candidate not found.");
		}

		try {
			candidatesDao.save(candidate);
			return createSuccessNotification("Candidate updated successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to update candidate: " + e.getMessage());
		}
	}

	@Override
	public Notification delete(int id) {
		if (!candidatesDao.existsById(id)) {
			return createErrorNotification("Candidate not found.");
		}

		try {
			candidatesDao.deleteById(id);
			return createSuccessNotification("Candidate deleted successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to delete candidate: " + e.getMessage());
		}
	}

	@Override
	public Notification verifyEmailCandidate(String email, String code) {
		Optional<Candidates> candidateOpt = candidatesDao.findByEmail(email);
		if (candidateOpt.isEmpty()) {
			return createErrorNotification("Candidate not found.");
		}

		Candidates candidate = candidateOpt.get();
		boolean isVerified = mailVerificateService.verifyEmail(email, code);
		if (isVerified) {
			candidate.setActivateStatusEmail(true);
			updateCandidateStatus(candidate);
			candidatesDao.save(candidate);
			return createSuccessNotification("Email verified successfully.");
		}
		return createErrorNotification("Invalid verification code or email.");
	}

	@Override
	public Notification verifyKYCCandidate(int id, MultipartFile document) {
		Optional<Candidates> candidateOpt = candidatesDao.findById(id);
		if (candidateOpt.isEmpty()) {
			return createErrorNotification("Candidate not found.");
		}

		Candidates candidate = candidateOpt.get();
		boolean kycResult = kycVerification.verifyCandidates(candidate);
		if (kycResult) {
			candidate.setActivateStatusKyc(true);
			updateCandidateStatus(candidate);
			candidatesDao.save(candidate);
			return createSuccessNotification("KYC verified successfully.");
		}
		return createErrorNotification("Invalid request or missing file.");
	}

	// Education operations
	@Override
	public DataNotification<List<CandidateEducations>> getEducationsByCandidateId(int candidateId) {
		if (!candidatesDao.existsById(candidateId)) {
			return new DataNotification<>(null, false, "Candidate not found.");
		}
		List<CandidateEducations> educations = educationsDao.findByCandidateId(candidateId);
		String message = educations.isEmpty() ? "No educations found." : "Educations retrieved successfully.";
		return new DataNotification<>(educations, true, message);
	}

	@Override
	public Notification addEducation(CandidateEducations education) {
		if (!isValidEducation(education)) {
			return createErrorNotification("Invalid education data.");
		}

		try {
			educationsDao.save(education);
			return createSuccessNotification("Education added successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to add education: " + e.getMessage());
		}
	}

	@Override
	public Notification updateEducation(CandidateEducations education) {
		if (!educationsDao.existsById(education.getId()) || !isValidEducation(education)) {
			return createErrorNotification("Invalid education data or education not found.");
		}

		try {
			educationsDao.save(education);
			return createSuccessNotification("Education updated successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to update education: " + e.getMessage());
		}
	}

	@Override
	public Notification deleteEducation(long educationId) {
		if (!educationsDao.existsById(educationId)) {
			return createErrorNotification("Education not found.");
		}

		try {
			educationsDao.deleteById(educationId);
			return createSuccessNotification("Education deleted successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to delete education: " + e.getMessage());
		}
	}

	// Experience operations
	@Override
	public DataNotification<List<CandidateExperiences>> getExperiencesByCandidateId(int candidateId) {
		if (!candidatesDao.existsById(candidateId)) {
			return new DataNotification<>(null, false, "Candidate not found.");
		}
		List<CandidateExperiences> experiences = experiencesDao.findByCandidateId(candidateId);
		String message = experiences.isEmpty() ? "No experiences found." : "Experiences retrieved successfully.";
		return new DataNotification<>(experiences, true, message);
	}

	@Override
	public Notification addExperience(CandidateExperiences experience) {
		if (!isValidExperience(experience)) {
			return createErrorNotification("Invalid experience data.");
		}

		try {
			experiencesDao.save(experience);
			return createSuccessNotification("Experience added successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to add experience: " + e.getMessage());
		}
	}

	@Override
	public Notification updateExperience(CandidateExperiences experience) {
		if (!experiencesDao.existsById(experience.getId()) || !isValidExperience(experience)) {
			return createErrorNotification("Invalid experience data or experience not found.");
		}

		try {
			experiencesDao.save(experience);
			return createSuccessNotification("Experience updated successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to update experience: " + e.getMessage());
		}
	}

	@Override
	public Notification deleteExperience(long experienceId) {
		if (!experiencesDao.existsById(experienceId)) {
			return createErrorNotification("Experience not found.");
		}

		try {
			experiencesDao.deleteById(experienceId);
			return createSuccessNotification("Experience deleted successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to delete experience: " + e.getMessage());
		}
	}

	// Skill operations
	@Override
	public DataNotification<List<CandidateSkills>> getSkillsByCandidateId(int candidateId) {
		if (!candidatesDao.existsById(candidateId)) {
			return new DataNotification<>(null, false, "Candidate not found.");
		}
		List<CandidateSkills> skills = skillsDao.findByCandidateId(candidateId);
		String message = skills.isEmpty() ? "No skills found." : "Skills retrieved successfully.";
		return new DataNotification<>(skills, true, message);
	}

	@Override
	public Notification addSkill(CandidateSkills skill) {
		if (!isValidSkill(skill)) {
			return createErrorNotification("Invalid skill data.");
		}

		try {
			skillsDao.save(skill);
			return createSuccessNotification("Skill added successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to add skill: " + e.getMessage());
		}
	}

	@Override
	public Notification updateSkill(CandidateSkills skill) {
		if (!skillsDao.existsById(skill.getId()) || !isValidSkill(skill)) {
			return createErrorNotification("Invalid skill data or skill not found.");
		}

		try {
			skillsDao.save(skill);
			return createSuccessNotification("Skill updated successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to update skill: " + e.getMessage());
		}
	}

	@Override
	public Notification deleteSkill(long skillId) {
		if (!skillsDao.existsById(skillId)) {
			return createErrorNotification("Skill not found.");
		}

		try {
			skillsDao.deleteById(skillId);
			return createSuccessNotification("Skill deleted successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to delete skill: " + e.getMessage());
		}
	}

	// Language operations
	@Override
	public DataNotification<List<CandidateLanguages>> getLanguagesByCandidateId(int candidateId) {
		if (!candidatesDao.existsById(candidateId)) {
			return new DataNotification<>(null, false, "Candidate not found.");
		}
		List<CandidateLanguages> languages = languagesDao.findByCandidateId(candidateId);
		String message = languages.isEmpty() ? "No languages found." : "Languages retrieved successfully.";
		return new DataNotification<>(languages, true, message);
	}

	@Override
	public Notification addLanguage(CandidateLanguages language) {
		if (!isValidLanguage(language)) {
			return createErrorNotification("Invalid language data.");
		}

		try {
			languagesDao.save(language);
			return createSuccessNotification("Language added successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to add language: " + e.getMessage());
		}
	}

	@Override
	public Notification updateLanguage(CandidateLanguages language) {
		if (!languagesDao.existsById(language.getId()) || !isValidLanguage(language)) {
			return createErrorNotification("Invalid language data or language not found.");
		}

		try {
			languagesDao.save(language);
			return createSuccessNotification("Language updated successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to update language: " + e.getMessage());
		}
	}

	@Override
	public Notification deleteLanguage(long languageId) {
		if (!languagesDao.existsById(languageId)) {
			return createErrorNotification("Language not found.");
		}

		try {
			languagesDao.deleteById(languageId);
			return createSuccessNotification("Language deleted successfully.");
		} catch (Exception e) {
			return createErrorNotification("Failed to delete language: " + e.getMessage());
		}
	}

	private boolean isValidCandidate(Candidates candidate) {
		if (candidate == null ||
				candidate.getEmail() == null ||
				candidate.getPassword() == null ||
				candidate.getFirstName() == null ||
				candidate.getLastName() == null ||
				candidate.getNationalIdentity() == null ||
				candidate.getDateOfBirth() == null) {
			return false;
		}
		if (!candidate.getNationalIdentity().matches("\\d{11}")) {
			return false;
		}
		if (candidate.getGender() != null && !candidate.getGender().matches("Male|Female|Other")) {
			return false;
		}
		return !candidatesDao.findByEmail(candidate.getEmail()).isPresent() &&
				!candidatesDao.existsByNationalIdentity(candidate.getNationalIdentity());
	}

	private boolean isValidEducation(CandidateEducations education) {
		return education != null &&
				education.getCandidate() != null &&
				candidatesDao.existsById(education.getCandidate().getId()) &&
				education.getEducationLevel() != null &&
				!education.getEducationLevel().isBlank();
	}

	private boolean isValidExperience(CandidateExperiences experience) {
		return experience != null &&
				experience.getCandidate() != null &&
				candidatesDao.existsById(experience.getCandidate().getId()) &&
				experience.getJobTitle() != null &&
				experience.getCompanyName() != null &&
				!experience.getCompanyName().isBlank() &&
				experience.getStartDate() != null &&
				(experience.getEndDate() == null || !experience.getEndDate().isBefore(experience.getStartDate()));
	}

	private boolean isValidSkill(CandidateSkills skill) {
		return skill != null &&
				skill.getCandidate() != null &&
				candidatesDao.existsById(skill.getCandidate().getId()) &&
				skill.getSkillName() != null &&
				!skill.getSkillName().isBlank();
	}

	private boolean isValidLanguage(CandidateLanguages language) {
		return language != null &&
				language.getCandidate() != null &&
				candidatesDao.existsById(language.getCandidate().getId()) &&
				language.getLanguage() != null &&
				!language.getLanguage().isBlank() &&
				language.getProficiencyLevel() != null &&
				!language.getProficiencyLevel().isBlank();
	}

	private void updateCandidateStatus(Candidates candidate) {
		if (candidate.isActivateStatusEmail() &&
				candidate.isActivateStatusKyc() &&
				candidate.isActivateStatusSms()) {
			candidate.setStatus("Active");
		}
	}

	private Notification createSuccessNotification(String message) {
		return new Notification(true, message);
	}

	private Notification createErrorNotification(String message) {
		return new Notification(false, message);
	}
}